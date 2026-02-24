package com.readdit.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.BookSubmissionRequest;
import com.readdit.dto.request.ReviewRequest;
import com.readdit.dto.response.AuthorSubmissionResponse;
import com.readdit.dto.response.BookSubmissionResponse;
import com.readdit.model.AuthorSubmission;
import com.readdit.model.Book;
import com.readdit.model.BookSubmission;
import com.readdit.model.User;
import com.readdit.repository.BookRepository;
import com.readdit.repository.BookSubmissionRepository;
import com.readdit.repository.UserRepository;
import com.readdit.util.RegexHelper;

@Service
public class BookSubmissionService {

    @Autowired
    private BookSubmissionRepository submissionRepo;

    @Autowired
    private UserRepository usrRepo;

    @Autowired
    private BookRepository bookRepo;

    public BookSubmissionResponse submit(BookSubmissionRequest req) {
        BookSubmission sub = submissionRepo.save(req.toBookSubmission());
        User submitter = usrRepo.getById(sub.getSubmitterId());
        User reviewer = usrRepo.getById(sub.getReviewerId());
        return BookSubmissionResponse.fromBookSubmission(sub, submitter, reviewer);
    }

    public BookSubmissionResponse review(int submissionId, ReviewRequest req) {
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
                book.setCoverUrl(submission.getCoverUrl());
                bookRepo.insert(book);
                // Update slug to include id suffix for uniqueness
                book.setSlug(RegexHelper.toSlug(submission.getTitle(), book.getId()));
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
                if (submission.getCoverUrl() != null && !submission.getCoverUrl().isEmpty()) {
                    existing.setCoverUrl(submission.getCoverUrl());
                }
                bookRepo.update(existing.getId(), existing);
            }
        }
        // submissionRepo.save(submission);
        // return submission;
        User submitter = usrRepo.getById(submission.getSubmitterId());
        User reviewer = usrRepo.getById(submission.getReviewerId());
        return BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer);
    }

    public BookSubmissionResponse getById(int id) {
        // return submissionRepo.findById(id).orElse(new BookSubmission());
        BookSubmission submission = submissionRepo.findById(id).orElse(new BookSubmission());
        User submitter = usrRepo.getById(submission.getSubmitterId());
        User reviewer = usrRepo.getById(submission.getReviewerId());
        return BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer);

    }

    public List<BookSubmissionResponse> getAll() {
        // return submissionRepo.findAll();
        List<BookSubmissionResponse> resp = new ArrayList<>();
        List<BookSubmission> submissions = submissionRepo.findAll();
        for (BookSubmission submission : submissions) {
             User submitter = usrRepo.getById(submission.getSubmitterId());
        User reviewer = usrRepo.getById(submission.getReviewerId());
            resp.add(BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer));
        }
        return resp;
    }

    // public List<BookSubmission> getPending() {
    //     return submissionRepo.findByReviewStatus("pending");
    // }

     public List<BookSubmissionResponse> getByReviewStatus(String status) {
        List<BookSubmissionResponse> resp = new ArrayList<>();
        List<BookSubmission> submissions = submissionRepo.findByReviewStatus(status);
        for (BookSubmission submission : submissions) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = usrRepo.getById(submission.getReviewerId());
            resp.add(BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer));
        }
        return resp;
    }

    public void deleteById(int id) {
        submissionRepo.deleteById(id);
    }
}
