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
import com.readdit.dto.response.Response;
import com.readdit.enums.ReviewStatus;
import com.readdit.service.AuthorSubmissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/author-submissions")
public class AuthorSubmissionController {

    @Autowired
    private AuthorSubmissionService submissionSrvc;

    @PostMapping
    @Transactional
    public ResponseEntity<Response> submit(@Valid @RequestBody AuthorSubmissionRequest req) {
        AuthorSubmissionResponse resp = submissionSrvc.submit(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(resp));
    }

    @PatchMapping("/{submissionId}/review")
    @Transactional
    public ResponseEntity<Response> review(
            @PathVariable int submissionId,
            @Valid @RequestBody ReviewRequest req) {
        AuthorSubmissionResponse resp = submissionSrvc.review(submissionId, req);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<Response> getById(@PathVariable int submissionId) {
        AuthorSubmissionResponse resp = submissionSrvc.getById(submissionId);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @GetMapping
    public ResponseEntity<Response> getAll() {
        List<AuthorSubmissionResponse> resp = submissionSrvc.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @GetMapping("/status/{reviewStatus}")
    public ResponseEntity<Response> getByReviewStatus(@PathVariable String reviewStatus) {
        List<AuthorSubmissionResponse> resp = submissionSrvc.getByReviewStatus(ReviewStatus.fromValue(reviewStatus));
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @DeleteMapping("/{submissionId}")
    @Transactional
    public ResponseEntity<Void> deleteById(@PathVariable int submissionId) {
        submissionSrvc.deleteById(submissionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
