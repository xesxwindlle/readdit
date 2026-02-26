package com.readdit.dto.request;

import com.readdit.enums.ReviewStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReviewRequest {

    @Positive
    private int reviewerId;

    private String reviewerComment;

    @NotNull
    private ReviewStatus reviewStatus;

    public ReviewRequest() {}

    // public int getReviewerId() { return reviewerId; }
    // public void setReviewerId(int reviewerId) { this.reviewerId = reviewerId; }

    // public String getReviewerComment() { return reviewerComment; }
    // public void setReviewerComment(String reviewerComment) { this.reviewerComment = reviewerComment; }

    // public ReviewStatus getReviewStatus() { return reviewStatus; }
    // public void setReviewStatus(ReviewStatus reviewStatus) { this.reviewStatus = reviewStatus; }
}
