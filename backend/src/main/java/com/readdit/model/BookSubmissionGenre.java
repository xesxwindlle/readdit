package com.readdit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table
public class BookSubmissionGenre {

    @Id
    private int id; 

    private int bookSubmissionId;

    private int genreId; 

    public BookSubmissionGenre(){};
}
