package com.readdit.dto.request;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @Email
    private String email;

    @UUID
    private String password;

    @URL
    private String avatarUrl;

    private String bio;

    @PastOrPresent
    private Timestamp createdAt;

    @PastOrPresent
    private Timestamp updatedAt;

    @NotNull
    private String role;

    public UserRequest() {};
}
