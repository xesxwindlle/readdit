package com.readdit.dto.request;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.readdit.model.Book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookRequest {

    // @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    private String slug;
    
    @NotBlank
    private String publisherId;

    @PastOrPresent
    private Date releaseDate;

    @Positive
    private double price;

    @Size(max = 5 * 1024 * 1024, 
          message = "Cover image must be less than 5MB") 
    private byte[] coverImage;

    private List<@NotNull(message = "Author ID cannot be null")
                 @Positive(message = "Invalid Author not valid")
                Integer> authorIds = new ArrayList<>();

    private List<@NotNull(message = "Author ID cannot be null")
                 @Positive(message = "Invalid Author not valid")
                Integer> genreIds = new ArrayList<>();

    public BookRequest() {}

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
