package com.readdit.dto.response;

import java.util.List;

import com.readdit.model.BookSubmission;
import com.readdit.model.User;

public class BookSubmissionResponse extends BookSubmission {

    private String submitterDisplayName;

    private String reviewerDisplayName;

    private List<String> authorNames;

    private List<String> genreNames;

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

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }

    public List<String> getGenreNames() {
        return genreNames;
    }

    public void setGenreNames(List<String> genreNames) {
        this.genreNames = genreNames;
    }

    public static BookSubmissionResponse fromBookSubmission(
            BookSubmission submission, User submitter, User reviewer,
            List<String> authorNames, List<String> genreNames) {
        BookSubmissionResponse response = new BookSubmissionResponse();
        response.setId(submission.getId());
        response.setCreatedAt(submission.getCreatedAt());
        response.setUpdatedAt(submission.getUpdatedAt());
        response.setBookId(submission.getBookId());
        response.setTitle(submission.getTitle());
        response.setIsbn(submission.getIsbn());
        response.setBookDescription(submission.getBookDescription());
        response.setPublisherName(submission.getPublisherName());
        response.setReleaseDate(submission.getReleaseDate());
        response.setCoverUrl(submission.getCoverUrl());
        response.setCoverImage(submission.getCoverImage());
        response.setSubmitterId(submission.getSubmitterId());
        response.setSubmitterDisplayName(submitter.getDisplayName());
        response.setSubmitterComment(submission.getSubmitterComment());
        response.setReviewerId(submission.getReviewerId());
        response.setReviewerDisplayName(reviewer != null ? reviewer.getDisplayName() : null);
        response.setReviewerComment(submission.getReviewerComment());
        response.setReviewedAt(submission.getReviewedAt());
        response.setReviewStatus(submission.getReviewStatus());
        response.setAuthorNames(authorNames);
        response.setGenreNames(genreNames);
        return response;
    }
}
