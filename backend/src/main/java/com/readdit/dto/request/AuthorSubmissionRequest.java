package com.readdit.dto.request;

import java.sql.Date;
import java.sql.Timestamp;

import com.readdit.model.AuthorSubmission;

public class AuthorSubmissionRequest {

    private int submitterId;
    private String submitterComment;

    // Author data
    private String authorName;
    private Date dateOfBirth;
    private Date dateOfDeath;
    private String authorImageUrl;
    private String biography;

    public AuthorSubmissionRequest() {}

    public int getSubmitterId() { return submitterId; }
    public void setSubmitterId(int submitterId) { this.submitterId = submitterId; }

    public String getSubmitterComment() { return submitterComment; }
    public void setSubmitterComment(String submitterComment) { this.submitterComment = submitterComment; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Date getDateOfDeath() { return dateOfDeath; }
    public void setDateOfDeath(Date dateOfDeath) { this.dateOfDeath = dateOfDeath; }

    public String getAuthorImageUrl() { return authorImageUrl; }
    public void setAuthorImageUrl(String authorImageUrl) { this.authorImageUrl = authorImageUrl; }

    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }

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
