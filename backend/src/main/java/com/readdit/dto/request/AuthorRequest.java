package com.readdit.dto.request;

import java.sql.Date;

import org.hibernate.validator.constraints.URL;

import com.readdit.model.Author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Data
public class AuthorRequest {

    @NotBlank
    private String name;

    @PastOrPresent
    private Date dateOfBirth;

    @PastOrPresent
    private Date dateOfDeath;

    @URL
    private String imageUrl;

    private String biography;

    public AuthorRequest() {}

    public Author toAuthor() {
        Author author = new Author();
        author.setName(name);
        author.setDateOfBirth(dateOfBirth);
        author.setDateOfDeath(dateOfDeath);
        author.setImageUrl(imageUrl);
        author.setBiography(biography);
        return author;
    }
}
