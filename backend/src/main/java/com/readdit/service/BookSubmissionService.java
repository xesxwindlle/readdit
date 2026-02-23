package com.readdit.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.BookSubmissionRequest;
import com.readdit.dto.request.ReviewRequest;
import com.readdit.model.Book;
import com.readdit.model.BookSubmission;
import com.readdit.repository.BookRepository;
import com.readdit.repository.BookSubmissionRepository;
import com.readdit.util.RegexHelper;

@Service
public class BookSubmissionService {

    @Autowired
    private BookSubmissionRepository submissionRepo;

    @Autowired
    private BookRepository bookRepo;

    public BookSubmission submit(BookSubmissionRequest req) {
        BookSubmission submission = new BookSubmission();
        submission.setBookId(req.getBookId());
        submission.setSubmitterId(req.getSubmitterId());
        submission.setSubmitterComment(req.getSubmitterComment());
        submission.setReviewStatus("pending");
        submission.setTitle(req.getTitle());
        submission.setIsbn(req.getIsbn());
        submission.setBookDescription(req.getBookDescription());
        submission.setPublisherId(req.getPublisherId());
        submission.setReleaseDate(req.getReleaseDate());
        submission.setCoverUrl(req.getCoverUrl());
        submission.setCoverImage(req.getCoverImage());
        return submissionRepo.save(submission);
    }

    public BookSubmission review(int submissionId, ReviewRequest req) {
        BookSubmission submission = submissionRepo.findById(submissionId).orElse(new BookSubmission());

        submission.setReviewerId(req.getReviewerId());
        submission.setReviewerComment(req.getReviewerComment());
        submission.setReviewStatus(req.getReviewStatus());
        submission.setReviewedAt(new Timestamp(System.currentTimeMillis()));

        if ("approved".equals(req.getReviewStatus())) {
            if (submission.getBookId() == null) {
                // New book — create it and link back
                Book book = new Book();
                book.setTitle(submission.getTitle());
                book.setIsbn(submission.getIsbn());
                book.setPublisherId(submission.getPublisherId());
                book.setReleaseDate(submission.getReleaseDate());
                book.setCoverImage(submission.getCoverImage());
                book.setSlug(RegexHelper.toSlug(submission.getTitle()));
                bookRepo.insert(book);
                // Update slug to include id suffix for uniqueness
                book.setSlug(RegexHelper.toSlug(submission.getTitle()) + "-" + book.getId());
                bookRepo.update(book.getId(), book);
                submission.setBookId(book.getId());
            } else {
                // Edit — update existing book
                Book existing = bookRepo.getById(submission.getBookId());
                existing.setTitle(submission.getTitle());
                existing.setIsbn(submission.getIsbn());
                existing.setPublisherId(submission.getPublisherId());
                existing.setReleaseDate(submission.getReleaseDate());
                if (submission.getCoverImage() != null)
                    existing.setCoverImage(submission.getCoverImage());
                bookRepo.update(existing.getId(), existing);
            }
        }

        submissionRepo.save(submission);
        return submission;
    }

    public BookSubmission getById(int id) {
        return submissionRepo.findById(id).orElse(new BookSubmission());
    }

    public List<BookSubmission> getAll() {
        return submissionRepo.getAll();
    }

    public List<BookSubmission> getPending() {
        return submissionRepo.getByStatus("pending");
    }

    public void deleteById(int id) {
        submissionRepo.deleteById(id);
    }
}
