package com.readdit.dto.request;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.URL;

import com.readdit.enums.Role;
import com.readdit.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String middleName;

    @NotBlank
    private String displayName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    // @UUID
    private String password;

    @URL
    private String avatarUrl;

    private String bio;

    public UserRequest() {};

     public User toUser() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        User usr = new User();
        usr.setFirstName(firstName);
        usr.setMiddleName(middleName);
        usr.setLastName(lastName);
        usr.setDisplayName(displayName);
        usr.setEmail(email);
        usr.setDisplayName(displayName);
        usr.setPassword(password);
        usr.setAvatarUrl(avatarUrl);
        usr.setBio(bio);
        usr.setCreatedAt(now);
        usr.setUpdatedAt(now);
        usr.setRole(Role.USER.getValue());
        return usr;
    }
}
