package com.readdit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.readdit.dto.request.BookReviewRequest;
import com.readdit.dto.response.BookReviewResponse;
import com.readdit.model.Book;
import com.readdit.model.BookReview;
import com.readdit.model.User;
import com.readdit.repository.BookRepository;
import com.readdit.repository.BookReviewRepository;
import com.readdit.repository.UserRepository;

@Service
public class BookReviewService {

    @Autowired
    public BookReviewRepository bkRvwRepo;

    @Autowired
    public BookRepository bookRepo;

    @Autowired
    public UserRepository usrRepo;

    @Autowired
    public BookRepository bkRepo;

    public BookReview createReview(String slug, BookReviewRequest bkRvwReq) {
        int bookId = bookRepo.getBySlug(slug).getId();
        BookReview review = bkRvwReq.toBookReview();
        review.setBookId(bookId);
        return bkRvwRepo.save(review);
    }

    // PATCH - only overwrites non-null/non-empty fields
    public BookReviewResponse update(int bkRvwId, BookReviewRequest bkRvwReq) {
        BookReview existing = bkRvwRepo.findById(bkRvwId).orElseThrow();
        if (bkRvwReq.getContent() != null && !bkRvwReq.getContent().isEmpty())
            existing.setContent(bkRvwReq.getContent());
        if (bkRvwReq.getRating() != 0)
            existing.setRating(bkRvwReq.getRating());
        existing.setModifiedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        bkRvwRepo.save(existing);
        return getById(bkRvwId);
    }

    // PUT - overwrites all fields
    public BookReviewResponse replace(int bkRvwId, BookReviewRequest bkRvwReq) {
        BookReview existing = bkRvwRepo.findById(bkRvwId).orElseThrow();
        existing.setContent(bkRvwReq.getContent());
        existing.setRating(bkRvwReq.getRating());
        existing.setModifiedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        bkRvwRepo.save(existing);
        return getById(bkRvwId);
    }

    public void deleteById(int reviewId) {
        bkRvwRepo.deleteById(reviewId);
    }

    public BookReviewResponse getById(int reviewId) {
        BookReview bkRvw  = bkRvwRepo.findById(reviewId).orElse(new BookReview());
        Book bk = bkRepo.getById(bkRvw.getBookId());
        User usr = usrRepo.getById(bkRvw.getUserId());
        return BookReviewResponse.fromBookReview(bkRvw, bk, usr);
    }

    public List<BookReviewResponse> getAll(String slug) {
        Book book = bookRepo.getBySlug(slug);
        return bkRvwRepo.findByBookId(book.getId())
                .stream()
                .map(review -> {
                    User user = usrRepo.getById(review.getUserId());
                    return BookReviewResponse.fromBookReview(review, book, user);
                })
                .toList();
    }


}
