package com.readdit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.model.Genre;
import com.readdit.repository.GenreRepository;

@Service
public class GenreService {

    @Autowired
    public GenreRepository genreRepository;

    public Genre insert(Genre genre) {
        genreRepository.insert(genre);
        return genre;
    }

    public Genre update(int genreId, Genre genre) {
        Genre existing = genreRepository.get(genreId);

        if (genre.getName() != null && !genre.getName().isEmpty())
            existing.setName(genre.getName());

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
