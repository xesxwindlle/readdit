package com.readdit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class BookGenre {

    @Id
    private int id;

    private int bookId;

    private int genreId;

    public BookGenre(int bookId, int genreId) {
        this.bookId = bookId;
        this.genreId = genreId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

}
