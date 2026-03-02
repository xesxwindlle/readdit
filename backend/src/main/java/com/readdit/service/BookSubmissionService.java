package com.readdit.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.readdit.exception.ResourceAlreadyExistsException;
import com.readdit.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.BookSubmissionRequest;
import com.readdit.dto.request.ReviewRequest;
import com.readdit.dto.response.BookSubmissionResponse;
import com.readdit.enums.ReviewStatus;
import com.readdit.model.Book;
import com.readdit.model.BookSubmission;
import com.readdit.model.BookSubmissionAuthor;
import com.readdit.model.BookSubmissionGenre;
import com.readdit.model.Publisher;
import com.readdit.model.User;
import com.readdit.repository.AuthorRepository;
import com.readdit.repository.BookRepository;
import com.readdit.repository.BookSubmissionAuthorRepository;
import com.readdit.repository.BookSubmissionGenreRepository;
import com.readdit.repository.BookSubmissionRepository;
import com.readdit.repository.GenreRepository;
import com.readdit.repository.PublisherRepository;
import com.readdit.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import com.readdit.util.RegexHelper;

@Service
public class BookSubmissionService {

    @Autowired
    private BookSubmissionRepository submissionRepo;

    @Autowired
    private UserRepository usrRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private PublisherRepository publisherRepo;

    @Autowired
    private BookSubmissionAuthorRepository subAuthorRepo;

    @Autowired
    private BookSubmissionGenreRepository subGenreRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private GenreRepository genreRepo;

    @Transactional
    public BookSubmissionResponse submit(BookSubmissionRequest req) {
        if (usrRepo.getById(req.getSubmitterId()) == null) {
            throw new ResourceNotFoundException("User with id " + req.getSubmitterId() + " not found");
        }

        if (req.getIsbn() != null && bookRepo.getByIsbn(req.getIsbn()) != null) {
            throw new ResourceAlreadyExistsException("Book with ISBN " + req.getIsbn() + " already exists");
        }

        BookSubmission sub = submissionRepo.save(req.toBookSubmission());

        if (req.getAuthorIds() != null) {
            for (int authorId : req.getAuthorIds()) {
                BookSubmissionAuthor link = new BookSubmissionAuthor();
                link.setSubmissionId(sub.getId());
                link.setAuthorId(authorId);
                subAuthorRepo.save(link);
            }
        }

        if (req.getGenreIds() != null) {
            for (int genreId : req.getGenreIds()) {
                BookSubmissionGenre link = new BookSubmissionGenre();
                link.setSubmissionId(sub.getId());
                link.setGenreId(genreId);
                subGenreRepo.save(link);
            }
        }

        User submitter = usrRepo.getById(sub.getSubmitterId());
        User reviewer = sub.getReviewerId() != null ? usrRepo.getById(sub.getReviewerId()) : null;
        return BookSubmissionResponse.fromBookSubmission(sub, submitter, reviewer,
                buildAuthors(sub.getId()), buildGenres(sub.getId()));
    }

    @Transactional
    public BookSubmissionResponse review(int submissionId, ReviewRequest req) {
        BookSubmission submission = submissionRepo.findById(submissionId)
            .orElseThrow(() -> new ResourceNotFoundException("Submission with id " + submissionId + " not found"));
        if (usrRepo.getById(req.getReviewerId()) == null) {
            throw new ResourceNotFoundException("User with id " + req.getReviewerId() + " not found");
        }

        submission.setReviewerId(req.getReviewerId());
        submission.setReviewerComment(req.getReviewerComment());
        submission.setReviewStatus(req.getReviewStatus().getValue());
        submission.setReviewedAt(new Timestamp(System.currentTimeMillis()));
        submission.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        if (ReviewStatus.APPROVED == req.getReviewStatus()) {
            // Resolve publisher: derive slug from name, create if not exists
            String publisherId = RegexHelper.toSlug(submission.getPublisherName());
            if (publisherRepo.getById(publisherId) == null) {
                Publisher publisher = new Publisher();
                publisher.setId(publisherId);
                publisher.setName(submission.getPublisherName());
                publisherRepo.insert(publisher);
            }

            if (submission.getBookId() == null) {
                // New book — create it and link back
                Book book = new Book();
                book.setTitle(submission.getTitle());
                book.setIsbn(submission.getIsbn());
                book.setPublisherId(publisherId);
                book.setReleaseDate(submission.getReleaseDate());
                book.setCoverImage(submission.getCoverImage());
                book.setCoverUrl(submission.getCoverUrl());
                bookRepo.insert(book);
                // Update slug to include id suffix for uniqueness
                book.setSlug(RegexHelper.toSlug(submission.getTitle(), book.getId()));
                bookRepo.update(book.getId(), book);
                submission.setBookId(book.getId());
            } else {
                // Edit — update existing book
                Book existing = bookRepo.getById(submission.getBookId());
                existing.setTitle(submission.getTitle());
                existing.setIsbn(submission.getIsbn());
                existing.setPublisherId(publisherId);
                existing.setReleaseDate(submission.getReleaseDate());
                if (submission.getCoverImage() != null)
                    existing.setCoverImage(submission.getCoverImage());
                if (submission.getCoverUrl() != null && !submission.getCoverUrl().isEmpty()) {
                    existing.setCoverUrl(submission.getCoverUrl());
                }
                bookRepo.update(existing.getId(), existing);
            }
        }

        submissionRepo.save(submission);
        User submitter = usrRepo.getById(submission.getSubmitterId());
        User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
        return BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer,
                buildAuthors(submission.getId()), buildGenres(submission.getId()));
    }

    public BookSubmissionResponse getById(int id) {
        BookSubmission submission = submissionRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Submission with id " + id + " not found"));
        User submitter = usrRepo.getById(submission.getSubmitterId());
        User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
        return BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer,
                buildAuthors(id), buildGenres(id));
    }

    public List<BookSubmissionResponse> getAll() {
        List<BookSubmissionResponse> resp = new ArrayList<>();
        List<BookSubmission> submissions = submissionRepo.findAll();
        for (BookSubmission submission : submissions) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            resp.add(BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer,
                    buildAuthors(submission.getId()), buildGenres(submission.getId())));
        }
        return resp;
    }

    public List<BookSubmissionResponse> getByReviewStatus(ReviewStatus status) {
        List<BookSubmissionResponse> resp = new ArrayList<>();
        List<BookSubmission> submissions = submissionRepo.findByReviewStatus(status.getValue());
        for (BookSubmission submission : submissions) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            resp.add(BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer,
                    buildAuthors(submission.getId()), buildGenres(submission.getId())));
        }
        return resp;
    }

    public List<BookSubmissionResponse> getBySubmitterId(int submitterId) {
        List<BookSubmissionResponse> resp = new ArrayList<>();
        for (BookSubmission submission : submissionRepo.findBySubmitterId(submitterId)) {
            User submitter = usrRepo.getById(submission.getSubmitterId());
            User reviewer = submission.getReviewerId() != null ? usrRepo.getById(submission.getReviewerId()) : null;
            resp.add(BookSubmissionResponse.fromBookSubmission(submission, submitter, reviewer,
                    buildAuthors(submission.getId()), buildGenres(submission.getId())));
        }
        return resp;
    }

    @Transactional
    public void deleteById(int id) {
        submissionRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Submission with id " + id + " not found"));
        submissionRepo.deleteById(id);
    }

    private List<String> buildAuthors(int submissionId) {
        List<String> result = new ArrayList<>();
        for (BookSubmissionAuthor link : subAuthorRepo.findBySubmissionId(submissionId)) {
            var author = authorRepo.getById(link.getAuthorId());
            if (author != null) result.add(author.getName());
        }
        return result;
    }

    private List<String> buildGenres(int submissionId) {
        List<String> result = new ArrayList<>();
        for (BookSubmissionGenre link : subGenreRepo.findBySubmissionId(submissionId)) {
            var genre = genreRepo.get(link.getGenreId());
            if (genre != null) result.add(genre.getName());
        }
        return result;
    }
}
