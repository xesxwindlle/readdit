package com.readdit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.BookGenreRequest;
import com.readdit.dto.request.BookRequest;
import com.readdit.dto.response.BookResponse;
import com.readdit.model.Book;
import com.readdit.repository.BookAuthorRepository;
import com.readdit.repository.BookGenreRepository;
import com.readdit.repository.BookRepository;
import com.readdit.util.RegexHelper;

@Service
public class BookService {

    @Autowired
    BookAuthorRepository bookAuthorRepository;

    @Autowired
    BookGenreRepository bookGenreRepository;

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse insert(BookRequest bookRequest) {
        Book savedBook = bookRepository.insert(bookRequest.toBook());

        bookRequest.getAuthorIds().forEach(authorId ->
            bookAuthorRepository.save(savedBook.getId(), authorId)
        );

        bookRequest.getGenreIds().forEach(genreId ->
            bookGenreRepository.save(savedBook.getId(), genreId)
        );

        return getById(savedBook.getId());
    }

    // PATCH - partial update, only overwrites non-null/non-empty fields
    public BookResponse patch(int bookId, BookRequest bookRequest) {
        Book book = bookRequest.toBook();
        Book existing = bookRepository.getById(bookId);

        if (book.getIsbn() != null && !book.getIsbn().isEmpty())
            existing.setIsbn(book.getIsbn());
        if (book.getTitle() != null && !book.getTitle().isEmpty())
            existing.setTitle(book.getTitle());
        if (book.getSlug() != null && !book.getSlug().isEmpty())
            existing.setSlug(book.getSlug());
        if (book.getPublisherId() != null && !book.getPublisherId().isEmpty())
            existing.setPublisherId(book.getPublisherId());
        if (book.getReleaseDate() != null)
            existing.setReleaseDate(book.getReleaseDate());
        if (book.getPrice() != 0)
            existing.setPrice(book.getPrice());
        if (book.getCoverImage() != null)
            existing.setCoverImage(book.getCoverImage());

        bookRepository.update(bookId, existing);

        // Only add new links, don't remove existing ones
        for (int authorId : bookRequest.getAuthorIds()) {
            if (bookAuthorRepository.exists(bookId, authorId)) continue;
            bookAuthorRepository.save(bookId, authorId);
        }

        for (int genreId : bookRequest.getGenreIds()) {
            if (bookGenreRepository.exists(bookId, genreId)) continue;
            bookGenreRepository.save(bookId, genreId);
        }

        return getById(bookId);
    }

    // PUT - full replace, overwrites all fields and replaces all links
    public BookResponse update(int bookId, BookRequest bookRequest) {
        Book book = bookRequest.toBook();
        book.setId(bookId);
        bookRepository.update(bookId, book);

        // Clear old links and re-insert
        bookAuthorRepository.deleteByBookId(bookId);
        bookGenreRepository.deleteByBookId(bookId);

        bookRequest.getAuthorIds().forEach(authorId ->
            bookAuthorRepository.save(bookId, authorId)
        );

        bookRequest.getGenreIds().forEach(genreId ->
            bookGenreRepository.save(bookId, genreId)
        );

        return getById(bookId);
    }

    public int deleteById(int id) {
        bookAuthorRepository.deleteByBookId(id);
        bookGenreRepository.deleteByBookId(id);
        return bookRepository.deleteById(id);
    }

    public BookResponse getBySlug(String slug) {
        Book book = bookRepository.getBySlug(slug);
        if (book == null) return null;
        return getById(book.getId());
    }

    public BookResponse patchBySlug(String slug, BookRequest bookRequest) {
        return patch(bookRepository.getBySlug(slug).getId(), bookRequest);
    }

    public BookResponse updateBySlug(String slug, BookRequest bookRequest) {
        return update(bookRepository.getBySlug(slug).getId(), bookRequest);
    }

    public void deleteBySlug(String slug) {
        deleteById(bookRepository.getBySlug(slug).getId());
    }

    public BookResponse addGenreBySlug(String slug, BookGenreRequest req) {
        return addGenre(bookRepository.getBySlug(slug).getId(), req);
    }

    public int removeGenreBySlug(String slug, int genreId) {
        return removeGenre(bookRepository.getBySlug(slug).getId(), genreId);
    }

    public BookResponse getById(int id) {
        Book book = bookRepository.getById(id);
        if (book == null) return null;

        List<String> authorNames = bookAuthorRepository.findAuthorsByBookId(id)
                .stream().map(a -> a.getName()).toList();
        List<String> genreNames = bookGenreRepository.findGenresByBookId(id)
                .stream().map(g -> g.getName()).toList();

        return BookResponse.fromBook(book, authorNames, genreNames);
    }

    public List<BookResponse> getAll() {
        return bookRepository.getAll().stream().map(book -> {
            List<String> authorNames = bookAuthorRepository.findAuthorsByBookId(book.getId())
                    .stream().map(a -> a.getName()).toList();
            List<String> genreNames = bookGenreRepository.findGenresByBookId(book.getId())
                    .stream().map(g -> g.getName()).toList();
            return BookResponse.fromBook(book, authorNames, genreNames);
        }).toList();
    }

    public BookResponse addGenre(int bookId, BookGenreRequest req) {
        bookGenreRepository.save(bookId, req.getGenreId());
        return getById(bookId);
    }

    public int removeGenre(int bookId, int genreId) {
        return bookGenreRepository.deleteByIds(bookId, genreId);
    }

    //Advanced Search

    public Book getByTitle(String title) {
        return bookRepository.getByTitle(title);
    }

    public List<Book> listTopNPricedBook(int n) {
        return bookRepository.listTopNPricedBook(n);
    }

}
