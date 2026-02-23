package com.readdit.model;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("book_submission")
public class BookSubmission {

    @Id
    private int id;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Submission meta
    private Integer previousSubmissionId;
    private Integer bookId;           // null = new book, non-null = edit to existing
    private int submitterId;
    private String submitterComment;
    private Integer reviewerId;
    private String reviewerComment;
    private Timestamp reviewedAt;
    private String reviewStatus;

    // Book data carried in the submission
    private String title;
    private String isbn;
    private String bookDescription;
    private String publisherId;
    private Date releaseDate;
    private String coverUrl;
    private byte[] coverImage;

    public BookSubmission() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public Integer getPreviousSubmissionId() { return previousSubmissionId; }
    public void setPreviousSubmissionId(Integer previousSubmissionId) { this.previousSubmissionId = previousSubmissionId; }

    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

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

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getBookDescription() { return bookDescription; }
    public void setBookDescription(String bookDescription) { this.bookDescription = bookDescription; }

    public String getPublisherId() { return publisherId; }
    public void setPublisherId(String publisherId) { this.publisherId = publisherId; }

    public Date getReleaseDate() { return releaseDate; }
    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }

    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

    public byte[] getCoverImage() { return coverImage; }
    public void setCoverImage(byte[] coverImage) { this.coverImage = coverImage; }
}
