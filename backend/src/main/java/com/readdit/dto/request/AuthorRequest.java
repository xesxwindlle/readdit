package com.readdit.dto.request;

import java.sql.Date;

import org.hibernate.validator.constraints.URL;

import com.readdit.model.Author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Date getDateOfDeath() { return dateOfDeath; }
    public void setDateOfDeath(Date dateOfDeath) { this.dateOfDeath = dateOfDeath; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }

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
