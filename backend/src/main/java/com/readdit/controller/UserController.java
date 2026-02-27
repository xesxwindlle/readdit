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

import com.readdit.dto.request.UserRequest;
import com.readdit.dto.response.Response;
import com.readdit.model.User;
import com.readdit.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService usrSrvc;

    @PostMapping
    public ResponseEntity<Response> create(@Valid @RequestBody UserRequest req) {
        User resp = usrSrvc.insert(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(resp));
    }

    // @PutMapping("/{userId}")
    // public ResponseEntity<Response> update(
    //     @PathVariable int userId, 
    //     @Valid
    //     @RequestBody
    //     UserRequest req) {
    // User resp = usrSrvc.update(userId, req);
    // return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    // }

    // @DeleteMapping("/{userId}")
    // public ResponseEntity<Void> deleteById(@PathVariable int userId) {
    //     usrSrvc.deleteById(userId);
    //     return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    // }

    // @GetMapping("/{userId}")
    // public ResponseEntity<User> getById(@PathVariable int userId) {
    //     User resp = usrSrvc.getById(userId);
    //     if (resp != null) {
    //         return ResponseEntity.status(HttpStatus.OK).body(resp);
    //     }
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }

    // @GetMapping
    // public ResponseEntity<List<User>> getAll() {
    //     return ResponseEntity.status(HttpStatus.OK).body(usrSrvc.getAll());
    // }
}
