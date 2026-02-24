package com.readdit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readdit.dto.request.AuthorSubmissionRequest;
import com.readdit.dto.request.ReviewRequest;
import com.readdit.dto.response.AuthorSubmissionResponse;
import com.readdit.service.AuthorSubmissionService;

@RestController
@RequestMapping("/author-submissions")
public class AuthorSubmissionController {

    @Autowired
    private AuthorSubmissionService submissionSrvc;

    @PostMapping
    @Transactional
    public ResponseEntity<AuthorSubmissionResponse> submit(@RequestBody AuthorSubmissionRequest req) {
        AuthorSubmissionResponse resp = submissionSrvc.submit(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PatchMapping("/{submissionId}/review")
    @Transactional
    public ResponseEntity<AuthorSubmissionResponse> review(
            @PathVariable int submissionId,
            @RequestBody ReviewRequest req) {
        AuthorSubmissionResponse resp = submissionSrvc.review(submissionId, req);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<AuthorSubmissionResponse> getById(@PathVariable int submissionId) {
        AuthorSubmissionResponse resp = submissionSrvc.getById(submissionId);
        if (resp != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorSubmissionResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(submissionSrvc.getAll());
    }

    @GetMapping("/status/{reviewStatus}")
    public ResponseEntity<List<AuthorSubmissionResponse>> getByReviewStatus(@PathVariable String reviewStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(submissionSrvc.getByReviewStatus(reviewStatus));
    }

    @DeleteMapping("/{submissionId}")
    @Transactional
    public ResponseEntity<Void> deleteById(@PathVariable int submissionId) {
        submissionSrvc.deleteById(submissionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
