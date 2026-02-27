package com.readdit.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    USER("USER"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Role fromValue(String value) {
        if (value == null) throw new IllegalArgumentException("role is required");
        for (Role role: values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException(
            "Invalid role: '" + value + "'. Allowed values: ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN"
        );
    }
}
