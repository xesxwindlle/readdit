-- =============================================
-- START OF SCRIPT
-- =============================================

SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

DROP DATABASE IF EXISTS `readdit`;

CREATE DATABASE `readdit`;

USE `readdit`;

CREATE TABLE author (

    -- Meta
    id          INT           NOT NULL AUTO_INCREMENT,
    slug        VARCHAR(255)  UNIQUE,                
    created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    review_status ENUM('approved', 'pending', 'rejected') DEFAULT 'pending',

    -- Author data
    name       VARCHAR(255) NOT NULL,
    date_of_birth     DATE,
    date_of_death     DATE,
    image_url  VARCHAR(500),
    biography         TEXT,

    PRIMARY KEY (id)
);

CREATE TABLE publisher (
  id VARCHAR(2) PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE genre (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    last_name VARCHAR(50) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(500),
    bio TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE book (
  id INT AUTO_INCREMENT PRIMARY KEY,
  isbn VARCHAR(13),
  title VARCHAR(100) NOT NULL,
  slug VARCHAR(150) NOT NULL UNIQUE,
  release_date TIMESTAMP,
  price DECIMAL(6,2),
  publisher_id VARCHAR(2) NOT NULL,
  cover_image BLOB,
  CONSTRAINT fk_book_publisher FOREIGN KEY (publisher_id)
      REFERENCES publisher (id)
);

CREATE TABLE book_review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    content TEXT NOT NULL,
    likes_count INT DEFAULT 0,
    rating INT DEFAULT 0,
    is_featured BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_book_review_user
        FOREIGN KEY (user_id) REFERENCES user(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_book_review_book
        FOREIGN KEY (book_id) REFERENCES book(id)
        ON DELETE CASCADE
);

CREATE TABLE book_genre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    genre_id INT NOT NULL,
    UNIQUE (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);

CREATE TABLE book_author (
  id INT AUTO_INCREMENT PRIMARY KEY,
  book_id INT NOT NULL,
  author_id INT NOT NULL,
  UNIQUE (book_id, author_id),
  FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
  FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE publisher_no_pk (
  id VARCHAR(2) NOT NULL,
  name VARCHAR(50) NOT NULL
);


DELIMITER $$

CREATE TRIGGER book_review_content_update
BEFORE UPDATE ON book_review
FOR EACH ROW
BEGIN
    IF OLD.content <> NEW.content THEN
        SET NEW.modified_at = CURRENT_TIMESTAMP;
    END IF;
END$$

DELIMITER ;

-- =============================================
-- END OF SCRIPT
-- =============================================
