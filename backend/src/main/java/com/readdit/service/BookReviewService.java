package com.readdit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.BookReviewRequest;
import com.readdit.dto.response.BookReviewResponse;
import com.readdit.exception.ResourceNotFoundException;
import com.readdit.model.Book;
import com.readdit.model.BookReview;
import com.readdit.model.User;
import com.readdit.repository.BookRepository;
import com.readdit.repository.BookReviewRepository;
import org.springframework.transaction.annotation.Transactional;

import com.readdit.repository.UserRepository;

@Service
public class BookReviewService {

    @Autowired
    private BookReviewRepository bkRvwRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository usrRepo;

    public BookReview createReview(String slug, BookReviewRequest bkRvwReq) {
        Book book = bookRepo.getBySlug(slug);
        if (book == null) {
            throw new ResourceNotFoundException("Book with slug " + slug + " not found");
        }
        BookReview review = bkRvwReq.toBookReview();
        review.setBookId(book.getId());
        return bkRvwRepo.save(review);
    }

    // PATCH - only overwrites non-null/non-empty fields
    @Transactional
    public BookReviewResponse update(int bkRvwId, BookReviewRequest bkRvwReq) {
        BookReview existing = bkRvwRepo.findById(bkRvwId)
            .orElseThrow(() -> new ResourceNotFoundException("Book review with id " + bkRvwId + " not found"));
        if (bkRvwReq.getContent() != null && !bkRvwReq.getContent().isEmpty())
            existing.setContent(bkRvwReq.getContent());
        if (bkRvwReq.getRating() != 0)
            existing.setRating(bkRvwReq.getRating());
        existing.setModifiedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        bkRvwRepo.save(existing);
        return getById(bkRvwId);
    }

    // PUT - overwrites all fields
    @Transactional
    public BookReviewResponse replace(int bkRvwId, BookReviewRequest bkRvwReq) {
        BookReview existing = bkRvwRepo.findById(bkRvwId)
            .orElseThrow(() -> new ResourceNotFoundException("Book review with id " + bkRvwId + " not found"));
        existing.setContent(bkRvwReq.getContent());
        existing.setRating(bkRvwReq.getRating());
        existing.setModifiedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        bkRvwRepo.save(existing);
        return getById(bkRvwId);
    }

    @Transactional
    public void deleteById(int reviewId) {
        bkRvwRepo.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("Book review with id " + reviewId + " not found"));
        bkRvwRepo.deleteById(reviewId);
    }

    public BookReviewResponse getById(int reviewId) {
        BookReview bkRvw = bkRvwRepo.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("Book review with id " + reviewId + " not found"));
        Book bk = bookRepo.getById(bkRvw.getBookId());
        User usr = usrRepo.getById(bkRvw.getUserId());
        return BookReviewResponse.fromBookReview(bkRvw, bk, usr);
    }

    public List<BookReviewResponse> getAll(String slug) {
        Book book = bookRepo.getBySlug(slug);
        if (book == null) {
            throw new ResourceNotFoundException("Book with slug " + slug + " not found");
        }
        return bkRvwRepo.findByBookId(book.getId())
                .stream()
                .map(review -> {
                    User user = usrRepo.getById(review.getUserId());
                    return BookReviewResponse.fromBookReview(review, book, user);
                })
                .toList();
    }
}
