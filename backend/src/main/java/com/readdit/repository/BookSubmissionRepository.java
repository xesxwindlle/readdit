// package com.readdit.repository;

// import java.util.List;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.jdbc.core.BeanPropertyRowMapper;
// import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
// import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
// import org.springframework.jdbc.support.GeneratedKeyHolder;
// import org.springframework.stereotype.Repository;

// import com.readdit.model.BookSubmission;

// @Repository
// public class BookSubmissionRepository {

//     @Autowired
//     private NamedParameterJdbcTemplate jdbc;

//     public BookSubmission insert(BookSubmission submission) {
//         String sql = """
//                 INSERT INTO book_submission (
//                     previous_submission_id, book_id, submitter_id, submitter_comment,
//                     review_status, title, isbn, book_description, publisher_id,
//                     release_date, cover_url, cover_image
//                 ) VALUES (
//                     :previousSubmissionId, :bookId, :submitterId, :submitterComment,
//                     :reviewStatus, :title, :isbn, :bookDescription, :publisherId,
//                     :releaseDate, :coverUrl, :coverImage
//                 )
//                 """;

//         MapSqlParameterSource params = new MapSqlParameterSource()
//                 .addValue("previousSubmissionId", submission.getPreviousSubmissionId())
//                 .addValue("bookId", submission.getBookId())
//                 .addValue("submitterId", submission.getSubmitterId())
//                 .addValue("submitterComment", submission.getSubmitterComment())
//                 .addValue("reviewStatus", submission.getReviewStatus())
//                 .addValue("title", submission.getTitle())
//                 .addValue("isbn", submission.getIsbn())
//                 .addValue("bookDescription", submission.getBookDescription())
//                 .addValue("publisherId", submission.getPublisherId())
//                 .addValue("releaseDate", submission.getReleaseDate())
//                 .addValue("coverUrl", submission.getCoverUrl())
//                 .addValue("coverImage", submission.getCoverImage());

//         GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//         jdbc.update(sql, params, keyHolder);
//         submission.setId(keyHolder.getKey().intValue());
//         return submission;
//     }

//     public int update(BookSubmission submission) {
//         String sql = """
//                 UPDATE book_submission
//                 SET book_id          = :bookId,
//                     reviewer_id      = :reviewerId,
//                     reviewer_comment = :reviewerComment,
//                     reviewed_at      = :reviewedAt,
//                     review_status    = :reviewStatus
//                 WHERE id = :id
//                 """;

//         MapSqlParameterSource params = new MapSqlParameterSource()
//                 .addValue("bookId", submission.getBookId())
//                 .addValue("reviewerId", submission.getReviewerId())
//                 .addValue("reviewerComment", submission.getReviewerComment())
//                 .addValue("reviewedAt", submission.getReviewedAt())
//                 .addValue("reviewStatus", submission.getReviewStatus())
//                 .addValue("id", submission.getId());

//         return jdbc.update(sql, params);
//     }

//     public BookSubmission getById(int id) {
//         String sql = """
//                 SELECT *
//                 FROM book_submission
//                 WHERE id = :id
//                 """;

//         List<BookSubmission> results = jdbc.query(
//                 sql,
//                 Map.of("id", id),
//                 new BeanPropertyRowMapper<>(BookSubmission.class));
//         return results.isEmpty() ? null : results.getFirst();
//     }

//     public List<BookSubmission> getAll() {
//         String sql = """
//                 SELECT *
//                 FROM book_submission
//                 ORDER BY created_at DESC
//                 """;

//         return jdbc.query(sql, new BeanPropertyRowMapper<>(BookSubmission.class));
//     }

//     public List<BookSubmission> getByStatus(String status) {
//         String sql = """
//                 SELECT *
//                 FROM book_submission
//                 WHERE review_status = :status
//                 ORDER BY created_at DESC
//                 """;

//         return jdbc.query(sql, Map.of("status", status), new BeanPropertyRowMapper<>(BookSubmission.class));
//     }

//     public int deleteById(int id) {
//         String sql = """
//                 DELETE FROM book_submission
//                 WHERE id = :id
//                 """;

//         return jdbc.update(sql, Map.of("id", id));
//     }
// }

package com.readdit.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.readdit.model.BookSubmission;

@RepositoryRestResource(exported = false)
public interface BookSubmissionRepository extends CrudRepository<BookSubmission, Integer> {
    List<BookSubmission> getAll();
    List<BookSubmission> getByStatus(String status);
    BookSubmission deleteById(int id);
};
