package com.readdit.model;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("author_submission")
public class AuthorSubmission {

    @Id
    private int id;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Submission meta
    private Integer previousSubmissionId;
    private Integer authorId;         // null = new author, non-null = edit to existing
    private int submitterId;
    private String submitterComment;
    private Integer reviewerId;
    private String reviewerComment;
    private Timestamp reviewedAt;
    private String reviewStatus;

    // Author data carried in the submission
    private String authorName;
    private Date dateOfBirth;
    private Date dateOfDeath;
    private String authorImageUrl;
    private String biography;

    public AuthorSubmission() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public Integer getPreviousSubmissionId() { return previousSubmissionId; }
    public void setPreviousSubmissionId(Integer previousSubmissionId) { this.previousSubmissionId = previousSubmissionId; }

    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }

    public int getSubmitterId() { return submitterId; }
    public void setSubmitterId(int submitterId) { this.submitterId = submitterId; }

    public String getSubmitterComment() { return submitterComment; }
    public void setSubmitterComment(String submitterComment) { this.submitterComment = submitterComment; }

    public Integer getReviewerId() { return reviewerId; }
    public void setReviewerId(Integer reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewerComment() { return reviewerComment; }
    public void setReviewerComment(String reviewerComment) { this.reviewerComment = reviewerComment; }

    public Timestamp getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(Timestamp reviewedAt) { this.reviewedAt = reviewedAt; }

    public String getReviewStatus() { return reviewStatus; }
    public void setReviewStatus(String reviewStatus) { this.reviewStatus = reviewStatus; }

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
}
