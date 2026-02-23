package com.readdit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readdit.dto.request.BookReviewRequest;
import com.readdit.dto.response.BookReviewResponse;
import com.readdit.service.BookReviewService;

@RestController
@RequestMapping("/book-reviews")
public class BookReviewController {

    @Autowired
    public BookReviewService bkRvwSrvc;

    @GetMapping("/{reviewId}")
    public ResponseEntity<BookReviewResponse> getById(@PathVariable int reviewId) {
        BookReviewResponse resp = bkRvwSrvc.getById(reviewId);
        if (resp != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<BookReviewResponse> patch(@PathVariable int reviewId, @RequestBody BookReviewRequest req) {
        BookReviewResponse resp = bkRvwSrvc.update(reviewId, req);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<BookReviewResponse> put(@PathVariable int reviewId, @RequestBody BookReviewRequest req) {
        BookReviewResponse resp = bkRvwSrvc.replace(reviewId, req);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteById(@PathVariable int reviewId) {
        bkRvwSrvc.deleteById(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
