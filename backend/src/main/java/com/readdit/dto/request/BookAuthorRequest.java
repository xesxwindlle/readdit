package com.readdit.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookAuthorRequest {

    @Positive
    int authorId;

    public BookAuthorRequest() {
    }
}
