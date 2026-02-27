package com.readdit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.UserRequest;
import com.readdit.exception.ResourceAlreadyExistsException;
import com.readdit.exception.ResourceNotFoundException;
import com.readdit.model.User;
import com.readdit.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public User insert(UserRequest req) {
        if (userRepository.getByEmail(req.getEmail()) != null) {
            throw new ResourceAlreadyExistsException("User with email " + req.getEmail() + " already exists");
        }

        if (userRepository.getByDisplayName(req.getDisplayName()) != null) {
            throw new ResourceAlreadyExistsException("User with display name " + req.getDisplayName() + " already exists");
        }

        User resp = userRepository.insert(req.toUser());
        return resp;
    }

    public User update(int userId, User user) {
        User existing = userRepository.getById(userId);

        if (user.getFirstName() != null && !user.getFirstName().isEmpty())
            existing.setFirstName(user.getFirstName());
        if (user.getLastName() != null && !user.getLastName().isEmpty())
            existing.setLastName(user.getLastName());
        if (user.getMiddleName() != null && !user.getMiddleName().isEmpty())
            existing.setMiddleName(user.getMiddleName());
        if (user.getDisplayName() != null && !user.getDisplayName().isEmpty())
            existing.setDisplayName(user.getDisplayName());
        if (user.getEmail() != null && !user.getEmail().isEmpty())
            existing.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty())
            existing.setPassword(user.getPassword());
        if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty())
            existing.setAvatarUrl(user.getAvatarUrl());
        if (user.getBio() != null && !user.getBio().isEmpty())
            existing.setBio(user.getBio());
        if (user.getCreatedAt() != null)
            existing.setCreatedAt(user.getCreatedAt());
        if (user.getUpdatedAt() != null)
            existing.setUpdatedAt(user.getUpdatedAt());
        if (user.getRole() != null && !user.getRole().isEmpty())
            existing.setRole(user.getRole());

        userRepository.update(existing);
        return existing;
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public User getById(int id) {
        User usr = userRepository.getById(id);
        if (usr == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        return usr;
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }
}
