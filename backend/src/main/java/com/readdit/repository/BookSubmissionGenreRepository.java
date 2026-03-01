package com.readdit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.readdit.model.BookSubmissionGenre;

@RepositoryRestResource(exported = false)
public interface BookSubmissionGenreRepository extends CrudRepository<BookSubmissionGenre, Integer> {
    List<BookSubmissionGenre> findBySubmissionId(int submissionId);
}
