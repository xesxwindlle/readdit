package com.readdit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readdit.dto.request.BookGenreRequest;
import com.readdit.dto.request.BookRequest;
import com.readdit.dto.request.BookReviewRequest;
import com.readdit.dto.response.Response;
import com.readdit.service.BookReviewService;
import com.readdit.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bkSrvc;

    @Autowired
    private BookReviewService bkRvwSrvc;

    // Basic API Endpoints
    @PostMapping
    public ResponseEntity<Response> create(@Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(bkSrvc.create(bookRequest)));
    }

    @PatchMapping("/{slug}")
    public ResponseEntity<Response> patch(@PathVariable String slug, @Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(bkSrvc.patchBySlug(slug, bookRequest)));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<Response> update(@PathVariable String slug, @Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(bkSrvc.updateBySlug(slug, bookRequest)));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteBySlug(@PathVariable String slug) {
        bkSrvc.deleteBySlug(slug);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Response> getBySlug(@PathVariable String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(bkSrvc.getBySlug(slug)));
    }

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(bkSrvc.getAll()));
    }

    @PostMapping("/{slug}/genres")
    public ResponseEntity<Response> addGenre(@PathVariable String slug, @Valid @RequestBody BookGenreRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(bkSrvc.addGenreBySlug(slug, req)));
    }

    @DeleteMapping("/{slug}/genres/{genreId}")
    public ResponseEntity<Void> deleteGenre(@PathVariable String slug, @PathVariable int genreId) {
        bkSrvc.removeGenreBySlug(slug, genreId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{slug}/reviews")
    public ResponseEntity<Response> createReview(@PathVariable String slug, @Valid @RequestBody BookReviewRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(bkRvwSrvc.createReview(slug, req)));
    }

    @GetMapping("/{slug}/reviews")
    public ResponseEntity<Response> getReviews(@PathVariable String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(bkRvwSrvc.getAll(slug)));
    }

    // Advanced API Endpoints
    @GetMapping("/top/prices")
    public ResponseEntity<Response> getTopPrices(@RequestParam int n) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(bkSrvc.listTopNPricedBook(n)));
    }
}
