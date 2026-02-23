package com.readdit.model;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class BookReview {

    @Id
    private int id;

    private int bookId;
    private int userId;

    private String content;

    private int likesCount = 0;       // DB default 0
    private int rating = 0;           // DB default 0
    private boolean isFeatured = false; // DB default FALSE

    private Timestamp createdAt;           // DB default CURRENT_TIMESTAMP
    private Timestamp modifiedAt;          // DB default CURRENT_TIMESTAMP on update

    // ---------------- Constructors ----------------

    // No-args constructor for Spring Data
    public BookReview() {}

    // Minimal constructor for creating a new review
    public BookReview(int userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    // Full constructor
    public BookReview(int id, int userId, String content,
                  int likesCount, int rating, boolean isFeatured,
                  Timestamp createdAt, Timestamp modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.likesCount = likesCount;
        this.rating = rating;
        this.isFeatured = isFeatured;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // ---------------- Getters and Setters ----------------

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getLikesCount() { return likesCount; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean isFeatured) { this.isFeatured = isFeatured; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getModifiedAt() { return modifiedAt; }
    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }
}
