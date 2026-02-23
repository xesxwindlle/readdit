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

import com.readdit.dto.request.BookSubmissionRequest;
import com.readdit.dto.request.ReviewRequest;
import com.readdit.model.BookSubmission;
import com.readdit.service.BookSubmissionService;

@RestController
@RequestMapping("/book-submissions")
public class BookSubmissionController {

    @Autowired
    private BookSubmissionService submissionSrvc;

    @PostMapping
    @Transactional
    public ResponseEntity<BookSubmission> submit(@RequestBody BookSubmissionRequest req) {
        BookSubmission resp = submissionSrvc.submit(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PatchMapping("/{submissionId}/review")
    @Transactional
    public ResponseEntity<BookSubmission> review(
            @PathVariable int submissionId,
            @RequestBody ReviewRequest req) {
        BookSubmission resp = submissionSrvc.review(submissionId, req);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<BookSubmission> getById(@PathVariable int submissionId) {
        BookSubmission resp = submissionSrvc.getById(submissionId);
        if (resp != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BookSubmission>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(submissionSrvc.getAll());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<BookSubmission>> getPending() {
        return ResponseEntity.status(HttpStatus.OK).body(submissionSrvc.getPending());
    }

    @DeleteMapping("/{submissionId}")
    @Transactional
    public ResponseEntity<Void> deleteById(@PathVariable int submissionId) {
        submissionSrvc.deleteById(submissionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
