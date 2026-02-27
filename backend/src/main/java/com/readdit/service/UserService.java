package com.readdit.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.UserRequest;
import com.readdit.enums.Role;
import com.readdit.exception.ResourceAlreadyExistsException;
import com.readdit.exception.ResourceNotFoundException;
import com.readdit.model.User;
import com.readdit.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public User create(UserRequest req) {
        if (userRepository.getByEmail(req.getEmail()) != null) {
            throw new ResourceAlreadyExistsException("User with email " + req.getEmail() + " already exists");
        }

        if (userRepository.getByDisplayName(req.getDisplayName()) != null) {
            throw new ResourceAlreadyExistsException("User with display name " + req.getDisplayName() + " already exists");
        }

        User resp = userRepository.insert(req.toUser());
        return resp;
    }

    public User update(int userId, UserRequest req) {
        User existing = userRepository.getById(userId);
        if (existing == null) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }

        existing.setFirstName(req.getFirstName());
        existing.setLastName(req.getLastName());
        existing.setMiddleName(req.getMiddleName());
        existing.setDisplayName(req.getDisplayName());
        existing.setEmail(req.getEmail());
        existing.setPassword(req.getPassword());
        existing.setAvatarUrl(req.getAvatarUrl());
        existing.setBio(req.getBio());

        //set to be informative to the client side
        existing.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        userRepository.update(existing);
        return existing;
    }

    public User patch(int userId, UserRequest req) {
        User existing = userRepository.getById(userId);
        if (existing == null) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }

        if (req.getFirstName() != null && !req.getFirstName().isEmpty())
            existing.setFirstName(req.getFirstName());
        if (req.getLastName() != null && !req.getLastName().isEmpty())
            existing.setLastName(req.getLastName());
        if (req.getMiddleName() != null && !req.getMiddleName().isEmpty())
            existing.setMiddleName(req.getMiddleName());
        if (req.getDisplayName() != null && !req.getDisplayName().isEmpty())
            existing.setDisplayName(req.getDisplayName());
        if (req.getEmail() != null && !req.getEmail().isEmpty())
            existing.setEmail(req.getEmail());
        if (req.getPassword() != null && !req.getPassword().isEmpty())
            existing.setPassword(req.getPassword());
        if (req.getAvatarUrl() != null && !req.getAvatarUrl().isEmpty())
            existing.setAvatarUrl(req.getAvatarUrl());
        if (req.getBio() != null && !req.getBio().isEmpty())
            existing.setBio(req.getBio());

        //set to be informative to the client side
        existing.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        userRepository.update(existing);
        return existing;
    }

    public User updateRole(int userId, Role role) {
        User existing = userRepository.getById(userId);
        if (existing == null) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }
        existing.setRole(role.getValue());
        existing.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        userRepository.update(existing);
        return existing;
    }

    public void deleteById(int id) {
        User usr = userRepository.getById(id);
        if (usr == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
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
