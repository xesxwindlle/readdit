package com.readdit.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.readdit.model.User;

@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public int insert(User user) {
        String sql = """
                    INSERT INTO user (first_name, last_name, middle_name, display_name,
                        email, password, avatar_url, bio, created_at, updated_at, role)
                    VALUES (:firstName, :lastName, :middleName, :displayName,
                        :email, :password, :avatarUrl, :bio, :createdAt, :updatedAt, :role)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("middleName", user.getMiddleName())
                .addValue("displayName", user.getDisplayName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("avatarUrl", user.getAvatarUrl())
                .addValue("bio", user.getBio())
                .addValue("createdAt", user.getCreatedAt())
                .addValue("updatedAt", user.getUpdatedAt())
                .addValue("role", user.getRole());

        return jdbc.update(sql, params);
    }

    public int update(User user) {
        String sql = """
                    UPDATE user
                    SET first_name = :firstName,
                        last_name = :lastName,
                        middle_name = :middleName,
                        display_name = :displayName,
                        email = :email,
                        password = :password,
                        avatar_url = :avatarUrl,
                        bio = :bio,
                        created_at = :createdAt,
                        updated_at = :updatedAt,
                        role = :role
                    WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("middleName", user.getMiddleName())
                .addValue("displayName", user.getDisplayName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("avatarUrl", user.getAvatarUrl())
                .addValue("bio", user.getBio())
                .addValue("createdAt", user.getCreatedAt())
                .addValue("updatedAt", user.getUpdatedAt())
                .addValue("role", user.getRole())
                .addValue("id", user.getId());

        return jdbc.update(sql, params);
    }

    public int deleteById(int id) {
        String sql = """
                    DELETE FROM user
                    WHERE id = :id
                """;

        return jdbc.update(sql, Map.of("id", id));
    }

    public User getById(int id) {
        String sql = """
                    SELECT *
                    FROM user
                    WHERE id = :id
                """;

        List<User> results = jdbc.query(
                sql,
                Map.of("id", id),
                new BeanPropertyRowMapper<>(User.class));
        return results.isEmpty() ? null : results.getFirst();
    }

    public List<User> getAll() {
        String sql = """
                    SELECT *
                    FROM user
                """;

        return jdbc.query(
                sql,
                new BeanPropertyRowMapper<>(User.class));
    }
}
