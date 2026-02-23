package com.readdit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.GenreRequest;
import com.readdit.model.Genre;
import com.readdit.repository.GenreRepository;
import com.readdit.util.RegexHelper;

@Service
public class GenreService {

    @Autowired
    public GenreRepository genreRepository;

    public Genre insert(GenreRequest req) {
        Genre genre = new Genre();
        genre.setName(req.getName());
        // Temporary slug to satisfy NOT NULL — overwritten after insert with id suffix
        genre.setSlug(RegexHelper.toSlug(req.getName()));
        genreRepository.insert(genre); // Sets genre.id via keyHolder
        genre.setSlug(RegexHelper.toSlug(req.getName()) + "-" + genre.getId());
        genreRepository.updateSlug(genre);
        return genre;
    }

    public Genre update(int genreId, GenreRequest req) {
        Genre existing = genreRepository.get(genreId);

        if (req.getName() != null && !req.getName().isEmpty()) {
            existing.setName(req.getName());
            existing.setSlug(RegexHelper.toSlug(req.getName()) + "-" + existing.getId());
        }

        genreRepository.update(existing);
        return existing;
    }

    public void deleteGenreById(int id) {
        genreRepository.deleteById(id);
    }

    public Genre get(int id) {
        return genreRepository.get(id);
    }

    public List<Genre> getAll() {
        return genreRepository.getAll();
    }
}
