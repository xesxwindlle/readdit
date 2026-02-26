package com.readdit.dto.request;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.validator.constraints.URL;

import com.readdit.model.BookSubmission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookSubmissionRequest {
    
    @Positive
    private int submitterId;

    private String submitterComment;

    // Book data
    @NotBlank
    private String title;

    @NotBlank
    private String isbn;

    private String bookDescription;

    @NotBlank
    private String publisherId;

    @PastOrPresent
    private Date releaseDate;

    @URL
    private String coverUrl;

    @Size(max = 5 * 1024 * 1024, 
          message = "Cover image must be less than 5MB") 
    private byte[] coverImage;

    public BookSubmissionRequest() {}

     public BookSubmission toBookSubmission() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        BookSubmission submission = new BookSubmission();
        submission.setCreatedAt(now);
        submission.setUpdatedAt(now);
        submission.setSubmitterId(submitterId);
        submission.setSubmitterComment(submitterComment);
        // submission.setReviewStatus("pending");
        submission.setTitle(title);
        submission.setIsbn(isbn);
        submission.setBookDescription(bookDescription);
        submission.setPublisherId(publisherId);
        submission.setReleaseDate(releaseDate);
        submission.setCoverUrl(coverUrl);
        submission.setCoverImage(coverImage);
        return submission;
    }
}
