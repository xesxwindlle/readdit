package com.readdit.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.readdit.model.Genre;

@Repository
public class GenreRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public int insert(Genre genre) {
        String sql = """
                    INSERT INTO genre (name)
                    VALUES (:name)
                """;

        return jdbc.update(sql, Map.of("name", genre.getName()));
    }

    public int update(Genre genre) {
        String sql = """
                    UPDATE genre
                    SET name = :name
                    WHERE id = :id
                """;

        return jdbc.update(sql, Map.of("id", genre.getId(), "name", genre.getName()));
    }

     public int deleteById(int id) {
        String sql = """
                    DELETE FROM genre
                    WHERE id = :id
                """;

        return jdbc.update(sql, Map.of("id", id));
    }

    public Genre get(int id) {
        String sql = """
                    SELECT *
                    FROM genre
                    WHERE id = :id
                """;

        List<Genre> results = jdbc.query(
                sql,
                Map.of("id", id),
                new BeanPropertyRowMapper<>(Genre.class));
        return results.isEmpty() ? null : results.getFirst();
    }

    public List<Genre> getAll() {
        String sql = """
                    SELECT *
                    FROM genre
                """;

        return jdbc.query(
                sql,
                new BeanPropertyRowMapper<>(Genre.class));
    }
}
