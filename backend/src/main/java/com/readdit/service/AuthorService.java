package com.readdit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.AuthorRequest;
import com.readdit.model.Author;
import com.readdit.repository.AuthorRepository;
import com.readdit.repository.BookAuthorRepository;
import com.readdit.util.RegexHelper;

@Service
public class AuthorService {
    
    @Autowired 
    public AuthorRepository athrRepo;

    @Autowired
    public BookAuthorRepository bkAthrRepo;

    public Author createAuthor(AuthorRequest req) {
        Author author = athrRepo.insert(req.toAuthor());
        author.setSlug(RegexHelper.toSlug(author.getName()) + "-" + author.getId());
        athrRepo.updateSlug(author);
        return author;
    }

    public Author update(String slug, Author athr) {
        Author existing = athrRepo.getBySlug(slug);

        if (athr.getName() != null && !athr.getName().isEmpty())
            existing.setName(athr.getName());
        if (athr.getDateOfBirth() != null)
            existing.setDateOfBirth(athr.getDateOfBirth());
        if (athr.getDateOfDeath() != null)
            existing.setDateOfDeath(athr.getDateOfDeath());
        if (athr.getImageUrl() != null && !athr.getImageUrl().isEmpty())
            existing.setImageUrl(athr.getImageUrl());
        if (athr.getBiography() != null && !athr.getBiography().isEmpty())
            existing.setBiography(athr.getBiography());
        if (athr.getReviewStatus() != null && !athr.getReviewStatus().isEmpty())
            existing.setReviewStatus(athr.getReviewStatus());

        athrRepo.update(existing);
        return existing;
    }

    public Author replace(String slug, Author athr) {
        Author existing = athrRepo.getBySlug(slug);
        existing.setName(athr.getName());
        existing.setDateOfBirth(athr.getDateOfBirth());
        existing.setDateOfDeath(athr.getDateOfDeath());
        existing.setImageUrl(athr.getImageUrl());
        existing.setBiography(athr.getBiography());
        existing.setReviewStatus(athr.getReviewStatus());
        athrRepo.update(existing);
        return existing;
    }

    public void deleteById(int id) {
        athrRepo.deleteById(id);
    }

    public void deleteBySlug(String slug) {
        int id = RegexHelper.extractDigits(slug);
        bkAthrRepo.deleteByAuthorId(id);
        athrRepo.deleteById(id);
    }

    public Author getById(int id) {
        return athrRepo.getById(id);
    }

    public Author getBySlug(String slug) {
        return athrRepo.getBySlug(slug);
    }

    public List<Author> getAll() {
        return athrRepo.getAll();
    }
}
