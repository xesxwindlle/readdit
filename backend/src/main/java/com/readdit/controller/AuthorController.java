package com.readdit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readdit.dto.request.AuthorRequest;
import com.readdit.dto.response.Response;
import com.readdit.model.Author;
import com.readdit.service.AuthorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService athrSrvc;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> create(@Valid @RequestBody AuthorRequest req) {
        Author resp = athrSrvc.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(resp));
    }

    @PutMapping("/{slug}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> update(@PathVariable String slug, @Valid @RequestBody AuthorRequest req) {
        Author resp = athrSrvc.update(slug, req);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @PatchMapping("/{slug}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> patch(@PathVariable String slug, @RequestBody AuthorRequest req) {
        Author resp = athrSrvc.patch(slug, req);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @DeleteMapping("/{slug}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBySlug(@PathVariable String slug) {
        athrSrvc.deleteBySlug(slug);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Response> getBySlug(@PathVariable String slug) {
        Author resp = athrSrvc.getBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(athrSrvc.getAll()));
    }
}
