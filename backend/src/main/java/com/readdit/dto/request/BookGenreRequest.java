package com.readdit.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookGenreRequest {

    @Positive
    int genreId;

    public BookGenreRequest() {
    }
}
