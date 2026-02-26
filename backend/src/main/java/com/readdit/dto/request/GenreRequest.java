package com.readdit.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenreRequest {

    @NotBlank
    private String name;

    public GenreRequest() {}

}
