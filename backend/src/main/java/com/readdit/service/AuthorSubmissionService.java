package com.readdit.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.AuthorSubmissionRequest;
import com.readdit.dto.request.ReviewRequest;
import com.readdit.dto.response.AuthorSubmissionResponse;
import com.readdit.enums.ReviewStatus;
import com.readdit.model.Author;
import com.readdit.model.AuthorSubmission;
import com.readdit.model.User;
import com.readdit.repository.AuthorRepository;
import com.readdit.repository.AuthorSubmissionRepository;
import com.readdit.repository.UserRepository;
import com.readdit.util.RegexHelper;

@Service
public class AuthorSubmissionService {

    @Autowired
    private AuthorSubmissionRepository submissionRepo;

    @Autowired
    private UserRepository usrRepo;

    @Autowired
    private AuthorRepository authorRepo;

    public AuthorSubmissionResponse submit(AuthorSubmissionRequest req) {
        if (usrRepo.getById(req.getSubmitterId()) == null) {
            throw new EmptyResultDataAccessException("User ID " + req.getSubmitterId() + " not found", 1);
        }

        AuthorSubmission sub = submissionRepo.save(req.toAuthorSubmission());
        User submitter = usrRepo.getById(sub.getSubmitterId());
        User reviewer = sub.getReviewerId() != null ? usrRepo.getById(sub.getReviewerId()) : null;
        return AuthorSubmissionResponse.fromAuthorSubmission(sub, submitter, reviewer);
    }

    public AuthorSubmissionResponse review(int submissionId, ReviewRequest req) {
        AuthorSubmission submission = submissionRepo.findById(submissionId)
            .orElseThrow(() -> new EmptyResultDataAccessException("Submission ID " + submissionId + " not found", 1));
        if (usrRepo.getById(req.getReviewerId()) == null) {
            throw new EmptyResultDataAccessException("User ID " + req.getReviewerId() + " not found", 1);
        }

        submission.setReviewerId(req.getReviewerId());
        submission.setReviewerComment(req.getReviewerComment());
        submission.setReviewStatus(req.getReviewStatus().getValue());
        submission.setReviewedAt(new Timestamp(System.currentTimeMillis()));

        if (ReviewStatus.APPROVED == req.getReviewStatus()) {
            if (submission.getAuthorId() == null) {
                // New author — create it and link back
                Author author = new Author();
                author.setName(submission.getAuthorName());
                author.setDateOfBirth(submission.getDateOfBirth());
                author.setDateOfDeath(submission.getDateOfDeath());
                author.setImageUrl(submission.getAuthorImageUrl());
                author.setBiography(submission.getBiography());
                authorRepo.insert(author);
                author.setSlug(RegexHelper.toSlug(author.getName(), author.getId()));
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
                authorRepo.update(existing);
            }
        }

        submissionRepo.save(submission);

        User submitter = usrRepo.getById(submission.getSubmitterId());
        User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
        return AuthorSubmissionResponse.fromAuthorSubmission(submission, submitter, reviewer);
    }

    public AuthorSubmissionResponse getById(int id) {
        AuthorSubmission submission = submissionRepo.findById(id).orElse(null);
        if (submission != null) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            return AuthorSubmissionResponse.fromAuthorSubmission(submission, submitter, reviewer);
        }
        return null;
    }

    public List<AuthorSubmissionResponse> getAll() {
        List<AuthorSubmissionResponse> resp = new ArrayList<>();
        List<AuthorSubmission> submissions = submissionRepo.findAll();
        for (AuthorSubmission submission : submissions) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            resp.add(AuthorSubmissionResponse.fromAuthorSubmission(submission, submitter, reviewer));
        }
        return resp;
    }

    public List<AuthorSubmissionResponse> getByReviewStatus(ReviewStatus status) {
        List<AuthorSubmissionResponse> resp = new ArrayList<>();
        List<AuthorSubmission> submissions = submissionRepo.findByReviewStatus(status.getValue());
        for (AuthorSubmission submission : submissions) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            resp.add(AuthorSubmissionResponse.fromAuthorSubmission(submission, submitter, reviewer));
        }
        return resp;
    }

    public void deleteById(int id) {
        submissionRepo.findById(id)
            .orElseThrow(() -> new EmptyResultDataAccessException("Submission ID " + id + " not found", 1));
        submissionRepo.deleteById(id);
    }
}
