package com.readdit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.readdit.model.Genre;
import com.readdit.repository.BookGenreRepository;

@Service
public class BookGenreService {

    private final BookGenreRepository bookGenreRepository;

    public BookGenreService(BookGenreRepository bookGenreRepository) {
        this.bookGenreRepository = bookGenreRepository;
    }

    public List<Genre> getGenresByBookId(int bookId) {
        return bookGenreRepository.findGenresByBookId(bookId);
    }

    public void addGenreToBook(int bookId, int genreId) {
        bookGenreRepository.save(bookId, genreId);
    }

    public void removeGenresFromBook(int bookId) {
        bookGenreRepository.deleteByBookId(bookId);
    }
}
