package com.readdit.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.AuthorSubmissionRequest;
import com.readdit.dto.request.ReviewRequest;
import com.readdit.model.Author;
import com.readdit.model.AuthorSubmission;
import com.readdit.repository.AuthorRepository;
import com.readdit.repository.AuthorSubmissionRepository;
import com.readdit.util.RegexHelper;

@Service
public class AuthorSubmissionService {

    @Autowired
    private AuthorSubmissionRepository submissionRepo;

    @Autowired
    private AuthorRepository authorRepo;

    public AuthorSubmission submit(AuthorSubmissionRequest req) {
        AuthorSubmission submission = new AuthorSubmission();
        submission.setAuthorId(req.getAuthorId());
        submission.setSubmitterId(req.getSubmitterId());
        submission.setSubmitterComment(req.getSubmitterComment());
        submission.setReviewStatus("pending");
        submission.setAuthorName(req.getAuthorName());
        submission.setDateOfBirth(req.getDateOfBirth());
        submission.setDateOfDeath(req.getDateOfDeath());
        submission.setAuthorImageUrl(req.getAuthorImageUrl());
        submission.setBiography(req.getBiography());
        return submissionRepo.insert(submission);
    }

    public AuthorSubmission review(int submissionId, ReviewRequest req) {
        AuthorSubmission submission = submissionRepo.getById(submissionId);

        submission.setReviewerId(req.getReviewerId());
        submission.setReviewerComment(req.getReviewerComment());
        submission.setReviewStatus(req.getReviewStatus());
        submission.setReviewedAt(new Timestamp(System.currentTimeMillis()));

        if ("approved".equals(req.getReviewStatus())) {
            if (submission.getAuthorId() == null) {
                // New author — create it and link back
                Author author = new Author();
                author.setName(submission.getAuthorName());
                author.setDateOfBirth(submission.getDateOfBirth());
                author.setDateOfDeath(submission.getDateOfDeath());
                author.setImageUrl(submission.getAuthorImageUrl());
                author.setBiography(submission.getBiography());
                author.setReviewStatus("approved");
                authorRepo.insert(author);
                author.setSlug(RegexHelper.toSlug(author.getName()) + "-" + author.getId());
                authorRepo.updateSlug(author);
                submission.setAuthorId(author.getId());
            } else {
                // Edit — update existing author
                Author existing = authorRepo.getById(submission.getAuthorId());
                existing.setName(submission.getAuthorName());
                existing.setDateOfBirth(submission.getDateOfBirth());
                existing.setDateOfDeath(submission.getDateOfDeath());
                existing.setImageUrl(submission.getAuthorImageUrl());
                existing.setBiography(submission.getBiography());
                existing.setReviewStatus("approved");
                authorRepo.update(existing);
            }
        }

        submissionRepo.update(submission);
        return submission;
    }

    public AuthorSubmission getById(int id) {
        return submissionRepo.getById(id);
    }

    public List<AuthorSubmission> getAll() {
        return submissionRepo.getAll();
    }

    public List<AuthorSubmission> getPending() {
        return submissionRepo.getByStatus("pending");
    }

    public void deleteById(int id) {
        submissionRepo.deleteById(id);
    }
}
