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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readdit.dto.request.BookGenreRequest;
import com.readdit.dto.request.BookRequest;
import com.readdit.dto.request.BookReviewRequest;
import com.readdit.dto.response.BookResponse;
import com.readdit.dto.response.BookReviewResponse;
import com.readdit.model.Book;
import com.readdit.model.BookReview;
import com.readdit.service.BookReviewService;
import com.readdit.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bkSrvc;

    @Autowired
    private BookReviewService bkRvwSrvc;

    // Basic API Endpoints
    @PostMapping
    @Transactional
    public ResponseEntity<BookResponse> post(@RequestBody BookRequest bookRequest) {
        BookResponse inserted = bkSrvc.insert(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(inserted);
    }

    @PatchMapping("/{slug}")
    @Transactional
    public ResponseEntity<BookResponse> patch(@PathVariable String slug, @RequestBody BookRequest bookRequest) {
        BookResponse updated = bkSrvc.patchBySlug(slug, bookRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PutMapping("/{slug}")
    @Transactional
    public ResponseEntity<BookResponse> put(@PathVariable String slug, @RequestBody BookRequest bookRequest) {
        BookResponse updated = bkSrvc.updateBySlug(slug, bookRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{slug}")
    @Transactional
    public ResponseEntity<Void> deleteBySlug(@PathVariable String slug) {
        bkSrvc.deleteBySlug(slug);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<BookResponse> getBySlug(@PathVariable String slug) {
        BookResponse resp = bkSrvc.getBySlug(slug);
        if (resp != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(bkSrvc.getAll());
    }

    @PostMapping("/{slug}/genres")
    @Transactional
    public ResponseEntity<BookResponse> post(@PathVariable String slug, @RequestBody BookGenreRequest req) {
        BookResponse resp = bkSrvc.addGenreBySlug(slug, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @DeleteMapping("/{slug}/genres/{genreId}")
    @Transactional
    public ResponseEntity<Void> deleteByGenreId(@PathVariable String slug, @PathVariable int genreId) {
        bkSrvc.removeGenreBySlug(slug, genreId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{slug}/reviews")
    public ResponseEntity<BookReview> post(@PathVariable String slug, @RequestBody BookReviewRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bkRvwSrvc.createReview(slug, req));
    }

    @GetMapping("/{slug}/reviews")
    public ResponseEntity<List<BookReviewResponse>> getAll(@PathVariable String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(bkRvwSrvc.getAll(slug));
    }

    // Advanced API Endpoints
    @GetMapping("/top/prices")
    public ResponseEntity<List<Book>> getTopPrices(@RequestParam int n) {
        return ResponseEntity.status(HttpStatus.OK).body(bkSrvc.listTopNPricedBook(n));
    }
}
