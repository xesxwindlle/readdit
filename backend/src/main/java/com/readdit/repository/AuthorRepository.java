package com.readdit.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.readdit.model.Author;

@Repository
public class AuthorRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public Author insert(Author athr) {
        String sql = """
                    INSERT INTO author (name, date_of_birth, date_of_death, image_url, biography)
                    VALUES (:name, :dateOfBirth, :dateOfDeath, :imageUrl, :biography)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", athr.getName())
                .addValue("dateOfBirth", athr.getDateOfBirth())
                .addValue("dateOfDeath", athr.getDateOfDeath())
                .addValue("imageUrl", athr.getImageUrl())
                .addValue("biography", athr.getBiography());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder);
        athr.setId(keyHolder.getKey().intValue());
        return athr;
    }

    public int updateSlug(Author athr) {
        String sql = """
                UPDATE author
                SET slug = :slug
                WHERE id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("slug", athr.getSlug())
                .addValue("id", athr.getId());
        return jdbc.update(sql, params);
    }

    public int update(Author athr) {
        String sql = """
                UPDATE author
                SET name = :name,
                    date_of_birth = :dateOfBirth,
                    date_of_death = :dateOfDeath,
                    image_url = :imageUrl,
                    biography = :biography
                WHERE id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", athr.getName())
                .addValue("dateOfBirth", athr.getDateOfBirth())
                .addValue("dateOfDeath", athr.getDateOfDeath())
                .addValue("imageUrl", athr.getImageUrl())
                .addValue("biography", athr.getBiography())
                .addValue("id", athr.getId());
        return jdbc.update(sql, params);
    }

    public int deleteById(int id) {
        String sql = """
                DELETE FROM author
                WHERE id = :id
                """;
        return jdbc.update(sql, Map.of("id", id));
    }

    public int deleteBySlug(String slug) {
        String sql = """
                DELETE FROM author
                WHERE slug = :slug
                """;
        return jdbc.update(sql, Map.of("slug", slug));
    }

    public Author getById(int id) {
        String sql = """
                SELECT *
                FROM author
                WHERE id = :id
                """;
        List<Author> results = jdbc.query(
                sql,
                Map.of("id", id),
                new BeanPropertyRowMapper<>(Author.class));
        return results.isEmpty() ? null : results.getFirst();
    }

       public Author getBySlug(String slug) {
        String sql = """
                SELECT *
                FROM author
                WHERE slug = :slug
                """;
        List<Author> results = jdbc.query(
                sql,
                Map.of("slug", slug),
                new BeanPropertyRowMapper<>(Author.class));
        return results.isEmpty() ? null : results.getFirst();
    }

    public List<Author> getAll() {
        String sql = """
                SELECT *
                FROM author
                """;
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Author.class));
    }
}