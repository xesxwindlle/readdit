package com.readdit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import com.readdit.dto.request.AuthorRequest;
import com.readdit.exception.ResourceNotFoundException;
import com.readdit.model.Author;
import com.readdit.repository.AuthorRepository;
import com.readdit.repository.BookAuthorRepository;
import org.springframework.transaction.annotation.Transactional;

import com.readdit.util.RegexHelper;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Transactional
    public Author create(AuthorRequest req) {
        Author author = authorRepository.insert(req.toAuthor());
        author.setSlug(RegexHelper.toSlug(author.getName()) + "-" + author.getId());
        authorRepository.updateSlug(author);
        return author;
    }

    @Transactional
    public Author update(String slug, AuthorRequest req) {
        Author existing = authorRepository.getBySlug(slug);
        if (existing == null) {
            throw new ResourceNotFoundException("Author with slug " + slug + " not found");
        }
        existing.setName(req.getName());
        existing.setSlug(RegexHelper.toSlug(req.getName(), RegexHelper.extractDigits(slug)));
        existing.setDateOfBirth(req.getDateOfBirth());
        existing.setDateOfDeath(req.getDateOfDeath());
        existing.setImageUrl(req.getImageUrl());
        existing.setBiography(req.getBiography());
        existing.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        authorRepository.update(existing);
        return existing;
    }

    @Transactional
    public Author patch(String slug, AuthorRequest req) {
        Author existing = authorRepository.getBySlug(slug);
        if (existing == null) {
            throw new ResourceNotFoundException("Author with slug " + slug + " not found");
        }

        if (req.getName() != null && !req.getName().isEmpty())
            existing.setName(req.getName());
            existing.setSlug(RegexHelper.toSlug(req.getName(), RegexHelper.extractDigits(slug)));
        if (req.getDateOfBirth() != null)
            existing.setDateOfBirth(req.getDateOfBirth());
        if (req.getDateOfDeath() != null)
            existing.setDateOfDeath(req.getDateOfDeath());
        if (req.getImageUrl() != null && !req.getImageUrl().isEmpty())
            existing.setImageUrl(req.getImageUrl());
        if (req.getBiography() != null && !req.getBiography().isEmpty())
            existing.setBiography(req.getBiography());
        existing.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        authorRepository.update(existing);
        return existing;
    }

    @Transactional
    public void deleteBySlug(String slug) {
        Author existing = authorRepository.getBySlug(slug);
        if (existing == null) {
            throw new ResourceNotFoundException("Author with slug " + slug + " not found");
        }
        int id = RegexHelper.extractDigits(slug);
        bookAuthorRepository.deleteByAuthorId(id);
        authorRepository.deleteById(id);
    }

    public Author getById(int id) {
        Author author = authorRepository.getById(id);
        if (author == null) {
            throw new ResourceNotFoundException("Author with id " + id + " not found");
        }
        return author;
    }

    public Author getBySlug(String slug) {
        Author author = authorRepository.getBySlug(slug);
        if (author == null) {
            throw new ResourceNotFoundException("Author with slug " + slug + " not found");
        }
        return author;
    }

    public Author getByNamePattern(String name) {
        return authorRepository.getByNamePattern(name);
    }

    public List<Author> getAll() {
        return authorRepository.getAll();
    }
}
