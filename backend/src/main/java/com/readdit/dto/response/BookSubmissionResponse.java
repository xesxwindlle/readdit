package com.readdit.dto.response;

import com.readdit.model.AuthorSubmission;
import com.readdit.model.BookSubmission;
import com.readdit.model.User;

public class BookSubmissionResponse extends BookSubmission {

    private String submitterDisplayName; 

    private String reviewerDisplayName; 
    
    public BookSubmissionResponse() {}

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

     public static BookSubmissionResponse fromBookSubmission(BookSubmission submission, User submitter, User reviewwer) {
        BookSubmissionResponse response = new BookSubmissionResponse();
        response.setId(submission.getId());
        response.setCreatedAt(submission.getCreatedAt());
        response.setUpdatedAt(submission.getUpdatedAt());
        response.setBookId(submission.getBookId());
        response.setTitle(submission.getTitle());
        response.setIsbn(submission.getIsbn());
        response.setBookDescription(submission.getBookDescription());
        response.setPublisherId(submission.getPublisherId());
        response.setSubmitterId(submission.getSubmitterId());
        response.setReleaseDate(submission.getReleaseDate());
        response.setCoverUrl(submission.getCoverUrl());
        response.setCoverImage(submission.getCoverImage());

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
