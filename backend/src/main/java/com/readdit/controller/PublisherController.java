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

import com.readdit.model.Publisher;
import com.readdit.service.PublisherService;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    public PublisherService pblshrSrvc;

    @PostMapping
    public ResponseEntity<Publisher> post(@RequestBody Publisher req) {
        Publisher resp = pblshrSrvc.insert(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/{publisherId}")
    public ResponseEntity<Publisher> put(@PathVariable String publisherId, @RequestBody Publisher req) {
        Publisher resp = pblshrSrvc.update(publisherId, req);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> deleteById(@PathVariable String publisherId) {
        pblshrSrvc.deleteById(publisherId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<Publisher> getById(@PathVariable String publisherId) {
        Publisher resp = pblshrSrvc.getById(publisherId);
        if (resp != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(pblshrSrvc.getAll());
    }
}
