package com.readdit.dto.response;


import java.sql.Date;
import java.sql.Timestamp;

import com.readdit.model.AuthorSubmission;
import com.readdit.model.Book;
import com.readdit.model.BookReview;
import com.readdit.model.User;

import lombok.Data;

@Data
public class AuthorSubmissionResponse {

    private int id;

    private Timestamp createdAt;

    private Timestamp updatedAt;
 
    private Integer authorId; 

    private String authorName;

    private Date dateOfBirth;

    private Date dateOfDeath;

    private String authorImageUrl;

    private String biography;       

    private int submitterId;

    private String submitterComment;

    private Integer reviewerId;

    private String reviewerComment;

    private Timestamp reviewedAt;

    private String reviewStatus;
    
    
    private String submitterDisplayName; 

    private String reviewerDisplayName; 
    
    public AuthorSubmissionResponse() {}

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
