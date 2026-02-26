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

import jakarta.validation.Valid;
import com.readdit.dto.request.BookSubmissionRequest;
import com.readdit.dto.request.ReviewRequest;
import com.readdit.dto.response.BookSubmissionResponse;
import com.readdit.dto.response.Response;
import com.readdit.service.BookSubmissionService;

@RestController
@RequestMapping("/book-submissions")
public class BookSubmissionController {

    @Autowired
    private BookSubmissionService submissionSrvc;

    @PostMapping
    @Transactional
    public ResponseEntity<Response> submit(@RequestBody BookSubmissionRequest req) {
        BookSubmissionResponse resp = submissionSrvc.submit(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(resp));
    }

    @PatchMapping("/{submissionId}/review")
    @Transactional
    public ResponseEntity<Response> review(
            @PathVariable int submissionId,
            @Valid @RequestBody ReviewRequest req) {
        BookSubmissionResponse resp = submissionSrvc.review(submissionId, req);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<Response> getById(@PathVariable int submissionId) {
        BookSubmissionResponse resp = submissionSrvc.getById(submissionId);
        if (resp != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("Content not found"));
    }

    @GetMapping
    public ResponseEntity<List<BookSubmissionResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(submissionSrvc.getAll());
    }

    @GetMapping("/status/{reviewStatus}")
    public ResponseEntity<List<BookSubmissionResponse>> getByReviewStatus(@PathVariable String reviewStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(submissionSrvc.getByReviewStatus(reviewStatus));
    }

    @DeleteMapping("/{submissionId}")
    @Transactional
    public ResponseEntity<Void> deleteById(@PathVariable int submissionId) {
        submissionSrvc.deleteById(submissionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
