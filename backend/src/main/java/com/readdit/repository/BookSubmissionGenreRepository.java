package com.readdit.repository;

import org.springframework.data.repository.CrudRepository;

import com.readdit.model.BookSubmissionGenre;

public interface BookSubmissionGenreRepository extends CrudRepository <BookSubmissionGenre, Integer>{
    
}
