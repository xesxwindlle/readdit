package com.readdit.model;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class Author {

    @Id
    private int id;
    private String slug;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String reviewStatus;

    private String name;
    private Date dateOfBirth;
    private Date dateOfDeath;
    private String imageUrl;
    private String biography;

    public Author() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public String getReviewStatus() { return reviewStatus; }
    public void setReviewStatus(String reviewStatus) { this.reviewStatus = reviewStatus; }

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
}
