package com.readdit.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.readdit.exception.ResourceAlreadyExistsException;
import com.readdit.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.BookSubmissionRequest;
import com.readdit.dto.request.ReviewRequest;
import com.readdit.dto.response.BookSubmissionResponse;
import com.readdit.enums.ReviewStatus;
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
        if (usrRepo.getById(req.getSubmitterId()) == null) {
            throw new ResourceNotFoundException("User with id " + req.getSubmitterId() + " not found");
        }
        
        if (req.getIsbn() != null && bookRepo.getByIsbn(req.getIsbn()) != null) {
            throw new ResourceAlreadyExistsException("Book with ISBN " + req.getIsbn() + " already exists");
        }

        BookSubmission sub = submissionRepo.save(req.toBookSubmission());
        User submitter = usrRepo.getById(sub.getSubmitterId());
        User reviewer = sub.getReviewerId() != null ? usrRepo.getById(sub.getReviewerId()) : null;
        return BookSubmissionResponse.fromBookSubmission(sub, submitter, reviewer);
    }

    public BookSubmissionResponse review(int submissionId, ReviewRequest req) {
        BookSubmission submission = submissionRepo.findById(submissionId)
            .orElseThrow(() -> new ResourceNotFoundException("Submission with id " + submissionId + " not found"));
        if (usrRepo.getById(req.getReviewerId()) == null) {
            throw new ResourceNotFoundException("User with id " + req.getReviewerId() + " not found");
        }

        submission.setReviewerId(req.getReviewerId());
        submission.setReviewerComment(req.getReviewerComment());
        submission.setReviewStatus(req.getReviewStatus().getValue());
        submission.setReviewedAt(new Timestamp(System.currentTimeMillis()));

        if (ReviewStatus.APPROVED == req.getReviewStatus()) {
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

        submissionRepo.save(submission);
        // return submission;
        User submitter = usrRepo.getById(submission.getSubmitterId());
        User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
        return BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer);
    }

    public BookSubmissionResponse getById(int id) {
        BookSubmission submission = submissionRepo.findById(id).orElse(null);
        if (submission != null){
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            return BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer);
        }
        return null;
    }

    public List<BookSubmissionResponse> getAll() {
        List<BookSubmissionResponse> resp = new ArrayList<>();
        List<BookSubmission> submissions = submissionRepo.findAll();
        for (BookSubmission submission : submissions) {
        User submitter = usrRepo.getById(submission.getSubmitterId());
        User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            resp.add(BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer));
        }
        return resp;
    }

     public List<BookSubmissionResponse> getByReviewStatus(ReviewStatus status) {
        List<BookSubmissionResponse> resp = new ArrayList<>();
        List<BookSubmission> submissions = submissionRepo.findByReviewStatus(status.getValue());
        for (BookSubmission submission : submissions) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            resp.add(BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer));
        }
        return resp;
    }

    public void deleteById(int id) {
        submissionRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Submission with id " + id + " not found"));
        submissionRepo.deleteById(id);
    }
}
