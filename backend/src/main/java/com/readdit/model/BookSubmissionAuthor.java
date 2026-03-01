package com.readdit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table
public class BookSubmissionAuthor {

    @Id
    private int id;

    private int submissionId;

    private int authorId;

    public BookSubmissionAuthor(){};
    
}
