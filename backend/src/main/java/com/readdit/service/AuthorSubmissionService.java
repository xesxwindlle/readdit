package com.readdit.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.AuthorSubmissionRequest;
import com.readdit.dto.request.ReviewRequest;
import com.readdit.dto.response.AuthorSubmissionResponse;
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
        AuthorSubmission sub = submissionRepo.save(req.toAuthorSubmission());
        User submitter = usrRepo.getById(sub.getSubmitterId());
        User reviewer = sub.getReviewerId() != null ? usrRepo.getById(sub.getReviewerId()) : null;
        return AuthorSubmissionResponse.fromAuthorSubmission(sub, submitter, reviewer);
    }

    public AuthorSubmissionResponse review(int submissionId, ReviewRequest req) {
        AuthorSubmission submission = submissionRepo.findById(submissionId).orElse(new AuthorSubmission());

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
        // return submission;

        User submitter = usrRepo.getById(submission.getSubmitterId());
        User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
        return AuthorSubmissionResponse.fromAuthorSubmission(submission, submitter, reviewer);
    }

    public AuthorSubmissionResponse getById(int id) {
        // return submissionRepo.findById(id).orElse(new AuthorSubmission());
        AuthorSubmission submission = submissionRepo.findById(id).orElse(null);
        if (submission != null) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            return AuthorSubmissionResponse.fromAuthorSubmission(submission, submitter, reviewer);   
        }
        return null;
    }

    public List<AuthorSubmissionResponse> getAll() {
        // return submissionRepo.findAll();
        List<AuthorSubmissionResponse> resp = new ArrayList<>();
        List<AuthorSubmission> submissions = submissionRepo.findAll();
        for (AuthorSubmission submission : submissions) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            resp.add(AuthorSubmissionResponse.fromAuthorSubmission(submission, submitter, reviewer));
        }
        return resp;
    }

    public List<AuthorSubmissionResponse> getByReviewStatus(String status) {
        // return submissionRepo.findByReviewStatus(status);
        List<AuthorSubmissionResponse> resp = new ArrayList<>();
        List<AuthorSubmission> submissions = submissionRepo.findByReviewStatus(status);
        for (AuthorSubmission submission : submissions) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            resp.add(AuthorSubmissionResponse.fromAuthorSubmission(submission, submitter, reviewer));
        }
        return resp;
    }

    public void deleteById(int id) {
        submissionRepo.deleteById(id);
    }
}
