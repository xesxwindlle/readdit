package com.readdit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readdit.dto.request.LoginRequest;
import com.readdit.dto.request.UserRequest;
import com.readdit.dto.response.AuthResponse;
import com.readdit.dto.response.Response;
import com.readdit.model.User;
import com.readdit.repository.UserRepository;
import com.readdit.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authSrvc;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody LoginRequest req) {
        AuthResponse resp = authSrvc.login(req);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody UserRequest req) {
        AuthResponse resp = authSrvc.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(resp));
    }

    @GetMapping("/verify")
    public ResponseEntity<Response> verify() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.error("Invalid token"));
        }
        AuthResponse resp = new AuthResponse(null, user.getId(), user.getDisplayName(), user.getEmail(), user.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(resp));
    }
}
