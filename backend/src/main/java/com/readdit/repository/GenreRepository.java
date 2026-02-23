package com.readdit.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.readdit.model.Genre;

@Repository
public class GenreRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public Genre insert(Genre genre) {
        String sql = """
                    INSERT INTO genre (name, slug)
                    VALUES (:name, :slug)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", genre.getName())
                .addValue("slug", genre.getSlug());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder);
        genre.setId(keyHolder.getKey().intValue());
        return genre;
    }

    public int updateSlug(Genre genre) {
        String sql = """
                    UPDATE genre
                    SET slug = :slug
                    WHERE id = :id
                """;

        return jdbc.update(sql, Map.of("slug", genre.getSlug(), "id", genre.getId()));
    }

    public int update(Genre genre) {
        String sql = """
                    UPDATE genre
                    SET name = :name,
                        slug = :slug
                    WHERE id = :id
                """;

        return jdbc.update(sql, Map.of("id", genre.getId(), "name", genre.getName(), "slug", genre.getSlug()));
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

    public Genre getBySlug(String slug) {
        String sql = """
                    SELECT *
                    FROM genre
                    WHERE slug = :slug
                """;

        List<Genre> results = jdbc.query(
                sql,
                Map.of("slug", slug),
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
