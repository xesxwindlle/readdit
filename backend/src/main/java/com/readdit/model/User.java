package com.readdit.model;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class User {

    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String displayName;
    private String email;
    private String password;
    private String avatarUrl;
    private String bio;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String role;

    public User() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.middleName = "";
        this.displayName = "";
        this.email = "";
        this.password = "";
        this.avatarUrl = "";
        this.bio = "";
        this.createdAt = null;
        this.updatedAt = null;
        this.role = "ROLE_USER";
    }

    public User(int id, String firstName, String lastName, String displayName, String email,
            String password, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = "ROLE_USER";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
