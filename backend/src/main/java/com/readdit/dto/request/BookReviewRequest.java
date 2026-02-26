package com.readdit.dto.request;

import java.sql.Timestamp;


import com.readdit.model.BookReview;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookReviewRequest {

    @Positive
    private int userId;

    @NotBlank
    private String content;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    public BookReviewRequest() {}

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
