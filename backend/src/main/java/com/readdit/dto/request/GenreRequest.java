package com.readdit.dto.request;

import com.readdit.model.Genre;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenreRequest {

    @NotBlank
    private String name;

    public GenreRequest() {}


    public Genre toGenre() {
        Genre genre = new Genre();
        genre.setName(name);
        return genre;
    }

}
