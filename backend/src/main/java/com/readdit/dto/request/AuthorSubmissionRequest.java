package com.readdit.dto.request;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.validator.constraints.URL;

import com.readdit.model.AuthorSubmission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AuthorSubmissionRequest {

    @Positive
    private int submitterId;

    private String submitterComment;

    // Author data
    @NotBlank
    private String authorName;

    @PastOrPresent
    private Date dateOfBirth;

    @PastOrPresent
    private Date dateOfDeath;

    @URL
    private String authorImageUrl;

    private String biography;

    public AuthorSubmissionRequest() {}

    public AuthorSubmission toAuthorSubmission() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        AuthorSubmission submission = new AuthorSubmission();
        submission.setCreatedAt(now);
        submission.setUpdatedAt(now);
        submission.setSubmitterId(submitterId);
        submission.setSubmitterComment(submitterComment);
        submission.setReviewStatus("pending");
        submission.setAuthorName(authorName);
        submission.setDateOfBirth(dateOfBirth);
        submission.setDateOfDeath(dateOfDeath);
        submission.setAuthorImageUrl(authorImageUrl);
        submission.setBiography(biography);
        return submission;
    }
}
