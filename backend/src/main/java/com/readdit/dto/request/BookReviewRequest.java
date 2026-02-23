package com.readdit.dto.request;

import java.sql.Timestamp;

import com.readdit.model.BookReview;

public class BookReviewRequest {

    private int userId;
    private String content;
    private int rating;

    public BookReviewRequest() {}

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public BookReview toBookReview() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        BookReview review = new BookReview();
        review.setUserId(userId);
        review.setContent(content);
        review.setRating(rating);
        review.setCreatedAt(now);
        review.setModifiedAt(now);
        return review;
    }
}
