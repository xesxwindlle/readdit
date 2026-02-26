package com.readdit.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ReviewStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String value;

    ReviewStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ReviewStatus fromValue(String value) {
        if (value == null) throw new IllegalArgumentException("reviewStatus is required");
        for (ReviewStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException(
            "Invalid reviewStatus: '" + value + "'. Allowed values: pending, approved, rejected"
        );
    }
}
