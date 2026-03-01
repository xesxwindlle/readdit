package com.readdit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.readdit.model.BookSubmissionAuthor;

@RepositoryRestResource(exported = false)
public interface BookSubmissionAuthorRepository extends CrudRepository<BookSubmissionAuthor, Integer> {
    List<BookSubmissionAuthor> findBySubmissionId(int submissionId);
}
