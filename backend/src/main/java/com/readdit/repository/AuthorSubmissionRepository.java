// package com.readdit.repository;

// import java.util.List;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.jdbc.core.BeanPropertyRowMapper;
// import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
// import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
// import org.springframework.jdbc.support.GeneratedKeyHolder;
// import org.springframework.stereotype.Repository;

// import com.readdit.model.AuthorSubmission;

// @Repository
// public class AuthorSubmissionRepository {

//     @Autowired
//     private NamedParameterJdbcTemplate jdbc;

//     public AuthorSubmission insert(AuthorSubmission submission) {
//         String sql = """
//                 INSERT INTO author_submission (
//                     author_id, submitter_id, submitter_comment,
//                     review_status, author_name, date_of_birth, date_of_death,
//                     author_image_url, biography
//                 ) VALUES (
//                     :authorId, :submitterId, :submitterComment,
//                     :reviewStatus, :authorName, :dateOfBirth, :dateOfDeath,
//                     :authorImageUrl, :biography
//                 )
//                 """;

//         MapSqlParameterSource params = new MapSqlParameterSource()
//                 .addValue("authorId", submission.getAuthorId())
//                 .addValue("submitterId", submission.getSubmitterId())
//                 .addValue("submitterComment", submission.getSubmitterComment())
//                 .addValue("reviewStatus", submission.getReviewStatus())
//                 .addValue("authorName", submission.getAuthorName())
//                 .addValue("dateOfBirth", submission.getDateOfBirth())
//                 .addValue("dateOfDeath", submission.getDateOfDeath())
//                 .addValue("authorImageUrl", submission.getAuthorImageUrl())
//                 .addValue("biography", submission.getBiography());

//         GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//         jdbc.update(sql, params, keyHolder);
//         submission.setId(keyHolder.getKey().intValue());
//         return submission;
//     }

//     public int update(AuthorSubmission submission) {
//         String sql = """
//                 UPDATE author_submission
//                 SET author_id         = :authorId,
//                     reviewer_id       = :reviewerId,
//                     reviewer_comment  = :reviewerComment,
//                     reviewed_at       = :reviewedAt,
//                     review_status     = :reviewStatus
//                 WHERE id = :id
//                 """;

//         MapSqlParameterSource params = new MapSqlParameterSource()
//                 .addValue("authorId", submission.getAuthorId())
//                 .addValue("reviewerId", submission.getReviewerId())
//                 .addValue("reviewerComment", submission.getReviewerComment())
//                 .addValue("reviewedAt", submission.getReviewedAt())
//                 .addValue("reviewStatus", submission.getReviewStatus())
//                 .addValue("id", submission.getId());

//         return jdbc.update(sql, params);
//     }

//     public AuthorSubmission getById(int id) {
//         String sql = """
//                 SELECT *
//                 FROM author_submission
//                 WHERE id = :id
//                 """;

//         List<AuthorSubmission> results = jdbc.query(
//                 sql,
//                 Map.of("id", id),
//                 new BeanPropertyRowMapper<>(AuthorSubmission.class));
//         return results.isEmpty() ? null : results.getFirst();
//     }

//     public List<AuthorSubmission> getAll() {
//         String sql = """
//                 SELECT *
//                 FROM author_submission
//                 ORDER BY created_at DESC
//                 """;

//         return jdbc.query(sql, new BeanPropertyRowMapper<>(AuthorSubmission.class));
//     }

//     public List<AuthorSubmission> getByStatus(String status) {
//         String sql = """
//                 SELECT *
//                 FROM author_submission
//                 WHERE review_status = :status
//                 ORDER BY created_at DESC
//                 """;

//         return jdbc.query(sql, Map.of("status", status), new BeanPropertyRowMapper<>(AuthorSubmission.class));
//     }

//     public int deleteById(int id) {
//         String sql = """
//                 DELETE FROM author_submission
//                 WHERE id = :id
//                 """;

//         return jdbc.update(sql, Map.of("id", id));
//     }
// }


package com.readdit.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.readdit.model.AuthorSubmission;

@RepositoryRestResource(exported = false)
public interface AuthorSubmissionRepository extends CrudRepository<AuthorSubmission, Integer> {
    List<AuthorSubmission> findAll();
    List<AuthorSubmission> findByReviewStatus(String reviewStatus);
};
