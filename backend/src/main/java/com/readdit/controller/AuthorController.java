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
import org.springframework.web.bind.annotation.RestController;

import com.readdit.dto.request.AuthorRequest;
import com.readdit.model.Author;
import com.readdit.service.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    public AuthorService athrSrvc;

    @PostMapping
    @Transactional
    public ResponseEntity<Author> post(@RequestBody AuthorRequest req) {
        Author resp = athrSrvc.createAuthor(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // @PutMapping("/{slug}")
    // public ResponseEntity<Author> update(@PathVariable String slug, @RequestBody
    // Author athr) {
    // Author updated = athrSrvc.update(authorId, athr);
    // return ResponseEntity.ok(updated);

    // }

    @PatchMapping("/{slug}")
    @Transactional
    public ResponseEntity<Author> patch(@PathVariable String slug, @RequestBody AuthorRequest req) {
        Author resp = athrSrvc.update(slug, req.toAuthor());
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PutMapping("/{slug}")
    @Transactional
    public ResponseEntity<Author> put(@PathVariable String slug, @RequestBody AuthorRequest req) {
        Author resp = athrSrvc.replace(slug, req.toAuthor());
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @DeleteMapping("/{slug}")
    @Transactional
    public ResponseEntity<Void> deleteBySlug(@PathVariable String slug) {
        athrSrvc.deleteBySlug(slug);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Author> getBySlug(@PathVariable String slug) {
        Author resp = athrSrvc.getBySlug(slug);
        if (resp != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(athrSrvc.getAll());
    }
}
