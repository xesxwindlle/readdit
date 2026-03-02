package com.readdit.dto.response;

import java.sql.Date;
import java.util.List;

import com.readdit.model.Book;

public class BookResponse {

    private int id;
    private String isbn;
    private String title;
    private String slug;
    private String publisherId;
    private Date releaseDate;
    private double price;
    private String coverUrl;
    private byte[] coverImage;
    private List<String> authorNames;

    private List<String> genreNames;

    private String publisherName;

    public BookResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
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

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public static BookResponse fromBook(Book book, List<String> authorNames, List<String> genreNames, String publisherName) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setIsbn(book.getIsbn());
        response.setTitle(book.getTitle());
        response.setSlug(book.getSlug());
        response.setPublisherId(book.getPublisherId());
        response.setReleaseDate(book.getReleaseDate());
        response.setCoverUrl(book.getCoverUrl());
        response.setCoverImage(book.getCoverImage());
        response.setAuthorNames(authorNames);
        response.setGenreNames(genreNames);
        response.setPublisherName(publisherName);
        return response;
    }
}
