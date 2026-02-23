package com.readdit.dto.request;

import java.sql.Date;

public class BookSubmissionRequest {

    private Integer bookId;            // null = new book, non-null = edit to existing
    private Integer previousSubmissionId;
    private int submitterId;
    private String submitterComment;

    // Book data
    private String title;
    private String isbn;
    private String bookDescription;
    private String publisherId;
    private Date releaseDate;
    private String coverUrl;
    private byte[] coverImage;

    public BookSubmissionRequest() {}

    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public Integer getPreviousSubmissionId() { return previousSubmissionId; }
    public void setPreviousSubmissionId(Integer previousSubmissionId) { this.previousSubmissionId = previousSubmissionId; }

    public int getSubmitterId() { return submitterId; }
    public void setSubmitterId(int submitterId) { this.submitterId = submitterId; }

    public String getSubmitterComment() { return submitterComment; }
    public void setSubmitterComment(String submitterComment) { this.submitterComment = submitterComment; }

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
