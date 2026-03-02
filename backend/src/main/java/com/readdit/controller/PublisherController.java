package com.readdit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readdit.dto.request.PublisherRequest;
import com.readdit.dto.response.Response;
import com.readdit.model.Publisher;
import com.readdit.service.PublisherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherService pblshrSrvc;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> create(@Valid @RequestBody PublisherRequest req) {
        Publisher resp = pblshrSrvc.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(resp));
    }

    @PutMapping("/{publisherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> update(@PathVariable String publisherId, @Valid @RequestBody PublisherRequest req) {
        Publisher resp = pblshrSrvc.update(publisherId, req);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @DeleteMapping("/{publisherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable String publisherId) {
        pblshrSrvc.deleteById(publisherId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<Response> getById(@PathVariable String publisherId) {
        Publisher resp = pblshrSrvc.getById(publisherId);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(pblshrSrvc.getAll()));
    }
}
