package com.readdit.dto.response;


import com.readdit.model.AuthorSubmission;
import com.readdit.model.Book;
import com.readdit.model.BookReview;
import com.readdit.model.User;

public class AuthorSubmissionResponse extends AuthorSubmission{
    
    
    private String submitterDisplayName; 

    private String reviewerDisplayName; 
    
    public AuthorSubmissionResponse() {}

    public String getSubmitterDisplayName() {
        return submitterDisplayName;
    }

    public void setSubmitterDisplayName(String submitterDisplayName) {
        this.submitterDisplayName = submitterDisplayName;
    }

    public String getReviewerDisplayName() {
        return reviewerDisplayName;
    }

    public void setReviewerDisplayName(String reviewerDisplayName) {
        this.reviewerDisplayName = reviewerDisplayName;
    }

     public static AuthorSubmissionResponse fromAuthorSubmission(AuthorSubmission submission, User submitter, User reviewwer) {
        AuthorSubmissionResponse response = new AuthorSubmissionResponse();
        response.setId(submission.getId());
        response.setCreatedAt(submission.getCreatedAt());
        response.setUpdatedAt(submission.getUpdatedAt());
        response.setAuthorId(submission.getAuthorId());
        response.setAuthorName(submission.getAuthorName());
        response.setDateOfBirth(submission.getDateOfBirth());
        response.setDateOfDeath(submission.getDateOfDeath());
        response.setAuthorImageUrl(submission.getAuthorImageUrl());
        response.setBiography(submission.getBiography());
        response.setSubmitterId(submission.getSubmitterId());
        response.setSubmitterDisplayName(submitter.getDisplayName());
        response.setSubmitterComment(submission.getSubmitterComment());
        response.setReviewerId(submission.getReviewerId());
        response.setReviewerDisplayName(reviewwer != null ? reviewwer.getDisplayName() : null);
        response.setReviewerComment(submission.getReviewerComment());
        response.setReviewedAt(submission.getReviewedAt());
        response.setReviewStatus(submission.getReviewStatus());
        return response;
    }

}
