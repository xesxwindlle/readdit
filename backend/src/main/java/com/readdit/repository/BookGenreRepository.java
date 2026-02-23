package com.readdit.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.readdit.model.Genre;

@Repository
public class BookGenreRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<Genre> findGenresByBookId(int bookId) {
        String sql = """
                    SELECT g.*
                    FROM genre g
                    JOIN book_genre bg ON g.id = bg.genre_id
                    WHERE bg.book_id = :bookId
                """;

        return jdbc.query(
                sql,
                Map.of("bookId", bookId),
                new BeanPropertyRowMapper<>(Genre.class));
    }

    public int save(int bookId, int genreId) {
        String sql = """
                    INSERT INTO book_genre (book_id, genre_id)
                    VALUES (:bookId, :genreId)
                """;

        return jdbc.update(sql, Map.of("bookId", bookId, "genreId", genreId));
    }

    public boolean exists(int bookId, int genreId) {
        String sql = """
                SELECT COUNT(*)
                FROM book_genre
                WHERE book_id = :bookId
                AND genre_id = :genreId
                """;

        Integer count = jdbc.queryForObject(sql, Map.of("bookId", bookId, "genreId", genreId), Integer.class);
        return count != null && count > 0;
    }

    public int deleteByBookId(int bookId) {
        String sql = """
                    DELETE FROM book_genre
                    WHERE book_id = :bookId
                """;

        return jdbc.update(sql, Map.of("bookId", bookId));
    }

    public int deleteByIds(int bookId, int genreId) {
        String sql = """
                    DELETE FROM book_genre
                    WHERE book_id = :bookId
                    AND genre_id = :genreId
                """;

        return jdbc.update(sql, Map.of("bookId", bookId, "genreId", genreId));
    }

}
