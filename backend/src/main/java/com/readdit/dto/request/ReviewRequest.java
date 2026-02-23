package com.readdit.dto.request;

public class ReviewRequest {

    private int reviewerId;
    private String reviewerComment;
    private String reviewStatus; // "approved" or "rejected"

    public ReviewRequest() {}

    public int getReviewerId() { return reviewerId; }
    public void setReviewerId(int reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewerComment() { return reviewerComment; }
    public void setReviewerComment(String reviewerComment) { this.reviewerComment = reviewerComment; }

    public String getReviewStatus() { return reviewStatus; }
    public void setReviewStatus(String reviewStatus) { this.reviewStatus = reviewStatus; }
}
