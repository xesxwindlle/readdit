package com.readdit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readdit.dto.request.GenreRequest;
import com.readdit.dto.response.Response;
import com.readdit.model.Genre;
import com.readdit.service.GenreService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService gnrSrvc;

    @PostMapping
    public ResponseEntity<Response> create(@Valid @RequestBody GenreRequest req) {
        Genre resp = gnrSrvc.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(resp));
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<Response> update(@PathVariable int genreId, @Valid @RequestBody GenreRequest req) {
        Genre resp = gnrSrvc.update(genreId, req);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<Void> deleteById(@PathVariable int genreId) {
        gnrSrvc.deleteGenreById(genreId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Response> getById(@PathVariable int genreId) {
        Genre resp = gnrSrvc.get(genreId);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(gnrSrvc.getAll()));
    }
}
