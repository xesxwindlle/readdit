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
    private byte[] coverImage;
    private List<String> authorNames;

    private List<String> genreNames;

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

    public static BookResponse fromBook(Book book, List<String> authorNames, List<String> genreNames) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setIsbn(book.getIsbn());
        response.setTitle(book.getTitle());
        response.setSlug(book.getSlug());
        response.setPublisherId(book.getPublisherId());
        response.setReleaseDate(book.getReleaseDate());
        response.setPrice(book.getPrice());
        response.setCoverImage(book.getCoverImage());
        response.setAuthorNames(authorNames);
        response.setGenreNames(genreNames);
        return response;
    }
}
