package com.readdit.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.GenreRequest;
import com.readdit.exception.ResourceAlreadyExistsException;
import com.readdit.exception.ResourceNotFoundException;
import com.readdit.model.Genre;
import com.readdit.repository.GenreRepository;
import org.springframework.transaction.annotation.Transactional;

import com.readdit.util.RegexHelper;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    public Genre create(GenreRequest req) {
        if (genreRepository.getBySlug(RegexHelper.toSlug(req.getName())) != null) {
             throw new ResourceAlreadyExistsException("Genre with name " + req.getName() + " already exists");
        }

        Genre gnr = req.toGenre();
        gnr.setSlug(RegexHelper.toSlug(gnr.getName()));
        gnr.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        gnr.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        genreRepository.insert(gnr);
        return gnr;
    }

    @Transactional
    public Genre update(int genreId, GenreRequest req) {
        Genre existing = genreRepository.get(genreId);
        if (existing == null) {
            throw new ResourceNotFoundException("Genre with id " + genreId + " not found");
        }
        if (req.getName() != null && !req.getName().isEmpty()) {
            existing.setName(req.getName());
            existing.setSlug(RegexHelper.toSlug(req.getName()));
            existing.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        }
        genreRepository.update(existing);
        return existing;
    }

    @Transactional
    public void deleteGenreById(int id) {
        if (genreRepository.get(id) == null) {
            throw new ResourceNotFoundException("Genre with id " + id + " not found");
        }
        genreRepository.deleteById(id);
    }

    public Genre get(int id) {
        Genre genre = genreRepository.get(id);
        if (genre == null) {
            throw new ResourceNotFoundException("Genre with id " + id + " not found");
        }
        return genre;
    }

    public List<Genre> getAll() {
        return genreRepository.getAll();
    }
}
