package com.readdit.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.readdit.model.Book;

@Repository
public class BookRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public Book insert(Book book) {
    String sql = """
                INSERT INTO book (isbn, title, slug, release_date, price, publisher_id, cover_image)
                VALUES (:isbn, :title, :slug, :releaseDate, :price, :publisherId, :coverImage)
            """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("isbn", book.getIsbn())
        .addValue("title", book.getTitle())
        .addValue("slug", book.getSlug())
        .addValue("releaseDate", book.getReleaseDate())
        .addValue("price", book.getPrice())
        .addValue("publisherId", book.getPublisherId())
        .addValue("coverImage", book.getCoverImage());

    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbc.update(sql, params, keyHolder);

    book.setId(keyHolder.getKey().intValue());
    return book;
}


    public int update(int bookId, Book book) {
        String sql = """
                UPDATE book
                SET isbn = :isbn,
                    title = :title,
                    slug = :slug,
                    release_date = :releaseDate,
                    price = :price,
                    publisher_id = :publisherId,
                    cover_image = :coverImage
                WHERE id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("isbn", book.getIsbn())
                .addValue("title", book.getTitle())
                .addValue("slug", book.getSlug())
                .addValue("releaseDate", book.getReleaseDate())
                .addValue("price", book.getPrice())
                .addValue("publisherId", book.getPublisherId())
                .addValue("coverImage", book.getCoverImage())
                .addValue("id", bookId);
        return jdbc.update(sql, params);
    }

      public int deleteById(int id) {
        String sql = """
                    DELETE FROM book
                    WHERE id = :id
                """;

        return jdbc.update(sql, Map.of("id", id));
    }

    public Book getById(int id) {
        String sql = """
            SELECT *
            FROM book
            WHERE id = :id
        """;

        List<Book> results = jdbc.query(
            sql,
            Map.of("id", id),
            new BeanPropertyRowMapper<>(Book.class)
        );
        return results.isEmpty() ? null : results.getFirst();
    }

     public List<Book> getAll() {
        String sql = """
                    SELECT *
                    FROM book
                """;

        return jdbc.query(
                sql,
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBySlug(String slug) {
        String sql = """
            SELECT *
            FROM book
            WHERE slug = :slug
        """;
        List<Book> results = jdbc.query(
            sql,
            Map.of("slug", slug),
            new BeanPropertyRowMapper<>(Book.class)
        );
        return results.isEmpty() ? null : results.getFirst();
    }

    //Advanced Search
       public Book getByTitle(String title) {
        String sql = """
            SELECT *
            FROM book
            WHERE title = :title
        """;

        List<Book> results = jdbc.query(
            sql,
            Map.of("title", title),
            new BeanPropertyRowMapper<>(Book.class)
        );
        return results.isEmpty() ? null : results.getFirst();
    }

    public List<Book> listTopNPricedBook(int n) {
        String sql = """
            SELECT *
            FROM book
            ORDER BY price DESC
            LIMIT :limit
        """;

        return jdbc.query(
            sql,
            Map.of("limit", n),
            new BeanPropertyRowMapper<>(Book.class)
        );
    }
}
