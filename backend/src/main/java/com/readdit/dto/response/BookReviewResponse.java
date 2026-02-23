package com.readdit.dto.response;

import java.sql.Timestamp;
import java.util.List;

import com.readdit.model.Book;
import com.readdit.model.BookReview;
import com.readdit.model.User;

public class BookReviewResponse {

    private int id;

    private int bookId;

    private String bookTitle;

    private int userId;

    private String userDisplayName;

    private String userImageUrl;

    private String content;

    private int rating;

    private int likesCount;

    private boolean isFeatured;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    public BookReviewResponse() {
    }

    public int getUserId() {
        return userId;
    }

    public BookReviewResponse setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BookReviewResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public BookReviewResponse setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public BookReviewResponse setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
        return this;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public BookReviewResponse setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
        return this;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public BookReviewResponse setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public int getId() {
        return id;
    }

    public BookReviewResponse setId(int id) {
        this.id = id;
        return this;
    }

    public int getBookId() {
        return bookId;
    }

    public BookReviewResponse setBookId(int bookId) {
        this.bookId = bookId;
        return this;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public BookReviewResponse setLikesCount(int likesCount) {
        this.likesCount = likesCount;
        return this;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public BookReviewResponse setFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
        return this;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public BookReviewResponse setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public BookReviewResponse setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public static BookReviewResponse fromBookReview(BookReview bookReview, Book book, User user) {
        BookReviewResponse response = new BookReviewResponse();
        return response.setId(bookReview.getId())
                .setBookId(book.getId())
                .setBookTitle(book.getTitle())
                .setUserId(user.getId())
                .setUserDisplayName(user.getDisplayName())
                .setUserImageUrl(user.getAvatarUrl())
                .setContent(bookReview.getContent())
                .setRating(bookReview.getRating())
                .setLikesCount(bookReview.getLikesCount())
                .setFeatured(bookReview.isFeatured())
                .setCreatedAt(bookReview.getCreatedAt())
                .setModifiedAt(bookReview.getModifiedAt());
    }

}
