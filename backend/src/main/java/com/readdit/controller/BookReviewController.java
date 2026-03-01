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
import com.readdit.dto.response.Response;
import com.readdit.service.BookReviewService;

@RestController
@RequestMapping("/book-reviews")
public class BookReviewController {

    @Autowired
    private BookReviewService bkRvwSrvc;

    @GetMapping("/{reviewId}")
    public ResponseEntity<Response> getById(@PathVariable int reviewId) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(bkRvwSrvc.getById(reviewId)));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Response> patch(@PathVariable int reviewId, @RequestBody BookReviewRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(bkRvwSrvc.update(reviewId, req)));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Response> update(@PathVariable int reviewId, @RequestBody BookReviewRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(bkRvwSrvc.replace(reviewId, req)));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteById(@PathVariable int reviewId) {
        bkRvwSrvc.deleteById(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
