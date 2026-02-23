package com.readdit.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.readdit.model.Author;

@Repository
public class BookAuthorRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<Author> findAuthorsByBookId(int bookId) {
        String sql = """
                    SELECT a.*
                    FROM author a
                    JOIN book_author ba ON a.id = ba.author_id
                    WHERE ba.book_id = :bookId
                """;

        return jdbc.query(
                sql,
                Map.of("bookId", bookId),
                new BeanPropertyRowMapper<>(Author.class));
    }

    public int save(int bookId, int authorId) {
        String sql = """
            INSERT INTO book_author (book_id, author_id)
            VALUES (:bookId, :authorId)
        """;

        return jdbc.update(sql, Map.of("bookId", bookId, "authorId", authorId));
    }

    public int deleteByAuthorId(int authorId) {
        String sql = """
            DELETE FROM book_author
            WHERE author_id = :authorId
        """;

        return jdbc.update(sql, Map.of("authorId", authorId));
    }

    public int deleteByBookId(int bookId) {
        String sql = """
            DELETE FROM book_author
            WHERE book_id = :bookId
        """;

        return jdbc.update(sql, Map.of("bookId", bookId));
    }

    public int deleteByIds(int bookId, int authorId) {
        String sql = """
            DELETE FROM book_author
            WHERE book_id = :bookId
            AND author_id = :authorId
        """;

        return jdbc.update(sql, Map.of("bookId", bookId, "authorId", authorId));
    }

    public boolean exists(int bookId, int authorId) {
        String sql = """
                SELECT COUNT(*)
                FROM book_author
                WHERE book_id = :bookId
                AND author_id = :authorId
                """;

        Integer count = jdbc.queryForObject(sql, Map.of("bookId", bookId, "authorId", authorId), Integer.class);
        return count != null && count > 0;
    }
}
