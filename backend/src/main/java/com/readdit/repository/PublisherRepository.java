package com.readdit.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.readdit.model.Publisher;

@Repository
public class PublisherRepository {

    @Autowired
    NamedParameterJdbcTemplate jdbc;

    public int insert(Publisher pblshr) {
        String sql = """
                    INSERT INTO publisher (id, name)
                    VALUES (:id, :name)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", pblshr.getName())
                .addValue("id", pblshr.getId());
        return jdbc.update(sql, params);
    }

    public int update(Publisher pblshr) {
        String sql = """
                UPDATE publisher
                SET id = :id,
                    name = :name
                WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", pblshr.getId())
                .addValue("name", pblshr.getName());

        return jdbc.update(sql, params);
    }

    public int deleteById(String id) {
        String sql = """
                DELETE FROM publisher
                WHERE id = :id
                """;
        return jdbc.update(sql, Map.of("id", id));
    }

    public Publisher getById(String id) {
        String sql = """
                SELECT * 
                FROM publisher
                WHERE id = :id
                """;
        List<Publisher> results = jdbc.query(sql, Map.of("id", id), new BeanPropertyRowMapper<>(Publisher.class));
        return results.isEmpty() ? null : results.getFirst();
    }

    public List<Publisher> getAll() {
        String sql = """
                SELECT * 
                FROM publisher
                """;
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Publisher.class));
    }
}