package com.readdit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.LoginRequest;
import com.readdit.dto.request.UserRequest;
import com.readdit.dto.response.AuthResponse;
import com.readdit.exception.ResourceAlreadyExistsException;
import com.readdit.exception.ResourceNotFoundException;
import com.readdit.model.User;
import com.readdit.repository.UserRepository;
import com.readdit.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.getByEmail(req.getEmail());
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getId(), user.getDisplayName(), user.getEmail(), user.getRole());
    }

    public AuthResponse register(UserRequest req) {
        if (userRepository.getByEmail(req.getEmail()) != null) {
            throw new ResourceAlreadyExistsException("User with email " + req.getEmail() + " already exists");
        }
        if (userRepository.getByDisplayName(req.getDisplayName()) != null) {
            throw new ResourceAlreadyExistsException("User with display name " + req.getDisplayName() + " already exists");
        }
        User user = req.toUser();
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        User saved = userRepository.insert(user);
        String token = jwtUtil.generateToken(saved);
        return new AuthResponse(token, saved.getId(), saved.getDisplayName(), saved.getEmail(), saved.getRole());
    }
}
