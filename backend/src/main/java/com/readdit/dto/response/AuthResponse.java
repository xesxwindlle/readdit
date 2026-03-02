package com.readdit.dto.response;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private int id;
    private String displayName;
    private String email;
    private String role;

    public AuthResponse(String token, int id, String displayName, String email, String role) {
        this.token = token;
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.role = role;
    }
}
