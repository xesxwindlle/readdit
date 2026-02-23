package com.readdit.dto.request;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.readdit.model.Book;

public class BookRequest {

    private String isbn;
    private String title;
    private String slug;
    private String publisherId;
    private Date releaseDate;
    private double price;
    private byte[] coverImage;
    private List<Integer> authorIds = new ArrayList<>();
    private List<Integer> genreIds = new ArrayList<>();

    public BookRequest() {}

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public List<Integer> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Integer> authorIds) {
        this.authorIds = authorIds;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Book toBook() {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setSlug(slug);
        book.setPublisherId(publisherId);
        book.setReleaseDate(releaseDate);
        book.setPrice(price);
        book.setCoverImage(coverImage);
        return book;
    }
}
