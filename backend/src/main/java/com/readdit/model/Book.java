package com.readdit.model;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class Book implements Serializable {
    static final long serialVersionUID = 2009L;

    @Id
    private int id;

    private String isbn, title, slug;
    
    private String publisherId;

    private Date releaseDate;

    private double price;

    private byte[] coverImage;

    public Book() {
        this.id = 0;
        this.isbn = "";
        this.title = "";
        this.slug = "";
        this.publisherId = "";
        this.releaseDate = null;
        this.price = 0;
        this.coverImage = null;
    }

    public Book(String isbn, String title, String slug, String publisherId,
        Date releaseDate, double price, byte[] coverImage) {
        this.isbn = isbn;
        this.title = title;
        this.slug = slug;
        this.publisherId = publisherId;
        this.releaseDate = releaseDate;
        this.price = price;
        this.coverImage = coverImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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



    // public static Book createBook() {
    //     String url = "https://cf-assets2.tenlong.com.tw/ig/024/199/841/9781617297571.jpg";

    //     return new Book(
    //             "1617297577", "Spring in Action, 6th Ed", "MP", new Date(122, 2, 1),
    //             70, ImageDownloader.downloadImage(url));
    // }
}
