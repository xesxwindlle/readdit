package com.readdit.controller;

import java.util.List;

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

import com.readdit.model.Genre;
import com.readdit.service.GenreService;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    public GenreService gnrSrvc;

    @PostMapping
    public ResponseEntity<Genre> post(@RequestBody Genre req) {
        Genre resp = gnrSrvc.insert(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<Genre> put(@PathVariable int genreId, @RequestBody Genre req) {
        Genre resp = gnrSrvc.update(genreId, req);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<Void> deleteById(@PathVariable int genreId) {
        gnrSrvc.deleteGenreById(genreId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Genre> getById(@PathVariable int genreId) {
        Genre resp = gnrSrvc.get(genreId);
        if (resp != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(gnrSrvc.getAll());
    }
}
