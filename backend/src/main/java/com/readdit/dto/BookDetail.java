package com.readdit.dto;

import java.util.List;

import com.readdit.model.Book;

public class BookDetail {

    private Book book;
    private List<Integer> authorIds;
    private List<Integer> genreIds;

    public BookDetail() {}

    public BookDetail(Book book, List<Integer> authorIds, List<Integer> genreIds) {
        this.book = book;
        this.authorIds = authorIds;
        this.genreIds = genreIds;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
}
