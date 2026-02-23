-- =============================================
--  INITIAL DATA
-- =============================================

USE readdit;

-- ----------------------
-- Authors
-- ----------------------
INSERT INTO author (slug, review_status, name, date_of_birth, date_of_death, image_url, biography) VALUES
('1-craig-walls',              'approved', 'Craig Walls',              '1967-03-15', NULL,         NULL, 'Craig Walls is a software developer and author best known for Spring in Action.'),
('2-tom-white',                'approved', 'Tom White',                '1974-06-22', NULL,         NULL, 'Tom White is an engineer at Cloudera and author of Hadoop: The Definitive Guide.'),
('3-john-resig',               'approved', 'John Resig',               '1984-08-28', NULL,         NULL, 'John Resig is the creator of jQuery and a JavaScript evangelist.'),
('4-bear-bibeault',            'approved', 'Bear Bibeault',            NULL,         NULL,         NULL, 'Bear Bibeault is a web developer and co-author of Secrets of the JavaScript Ninja.'),
('5-rod-johnson',              'approved', 'Rod Johnson',              '1970-01-01', NULL,         NULL, 'Rod Johnson is the founder of the Spring Framework and former CEO of SpringSource.'),
('6-christian-bauer',          'approved', 'Christian Bauer',          NULL,         NULL,         NULL, 'Christian Bauer is a core Hibernate developer and co-author of Java Persistence with Hibernate.'),
('7-gavin-king',               'approved', 'Gavin King',               '1974-01-01', NULL,         NULL, 'Gavin King is the creator of Hibernate and a key contributor to the Java EE platform.'),
('8-douglas-crockford',        'approved', 'Douglas Crockford',        '1955-05-15', NULL,         NULL, 'Douglas Crockford is the inventor of JSON and author of JavaScript: The Good Parts.'),
('9-jesse-james-garrett',      'approved', 'Jesse James Garrett',      '1968-01-01', NULL,         NULL, 'Jesse James Garrett coined the term Ajax and authored The Elements of User Experience.'),
('10-matsumoto-yukihiro',      'approved', '松本行弘',                  '1965-04-14', NULL,         NULL, 'Yukihiro Matsumoto, known as Matz, is the creator of the Ruby programming language.'),
('11-sam-ruby',                'approved', 'Sam Ruby',                 NULL,         NULL,         NULL, 'Sam Ruby is an IBM software engineer and co-author of Agile Web Development with Rails.'),
('12-dave-thomas',             'approved', 'Dave Thomas',              NULL,         NULL,         NULL, 'Dave Thomas is a programmer and co-author of The Pragmatic Programmer and Agile Web Development with Rails.'),
('13-david-heinemeier-hansson','approved', 'David Heinemeier Hansson', '1979-10-15', NULL,         NULL, 'David Heinemeier Hansson is the creator of Ruby on Rails and co-founder of Basecamp.'),
('14-james-gosling',           'approved', 'James Gosling',            '1955-05-19', NULL,         NULL, 'James Gosling is the father of the Java programming language.'),
('15-bill-joy',                'approved', 'Bill Joy',                 '1954-11-08', NULL,         NULL, 'Bill Joy is a co-founder of Sun Microsystems and co-author of The Java Language Specification.'),
('16-guy-steele',              'approved', 'Guy L. Steele Jr.',        '1954-10-02', NULL,         NULL, 'Guy L. Steele Jr. is a computer scientist and co-author of The Java Language Specification.'),
('17-gilad-bracha',            'approved', 'Gilad Bracha',             NULL,         NULL,         NULL, 'Gilad Bracha is a programming language researcher and co-author of The Java Language Specification.'),
('18-alex-buckley',            'approved', 'Alex Buckley',             NULL,         NULL,         NULL, 'Alex Buckley is the specification lead for the Java programming language at Oracle.'),
('19-kathy-sierra',            'approved', 'Kathy Sierra',             NULL,         NULL,         NULL, 'Kathy Sierra is an American programming instructor and game developer, co-author of Head First Java.'),
('20-bert-bates',              'approved', 'Bert Bates',               NULL,         NULL,         NULL, 'Bert Bates is a software developer and co-author of Head First Java.');

-- ----------------------
-- Publishers
-- ----------------------
INSERT INTO publisher VALUES
('MP', 'Manning Publication'),
('OA', 'O''Reilly & Associates'),
('WP', 'Wrox Press'),
('NR', 'New Riders'),
('GI', 'Gotop Information'),
('PB', 'Pragmatic Bookshelf'),
('AW', 'Addison Wesley');

-- ----------------------
-- Genres
-- ----------------------
INSERT INTO genre(name) VALUES
('Programming'),
('Web Development'),
('Java'),
('JavaScript'),
('Database'),
('Software Engineering'),
('User Experience'),
('Ruby'),
('Big Data'),
('Language Design');

-- ----------------------
-- Userss
-- ----------------------
INSERT INTO `user`(first_name, middle_name, last_name, display_name, email, password, avatar_url, bio, last_login_at) VALUES
('Alice', NULL, 'Chen', 'alice_reads', 'alice@example.com', '$2a$10$abcdefghijklmnopqrstuuABCDEFGHIJKLMNOPQRSTUVWX', NULL, 'Avid reader and Java developer.', '2026-02-10'),
('Bob', 'James', 'Smith', 'bookworm_bob', 'bob@example.com', '$2a$10$abcdefghijklmnopqrstuuABCDEFGHIJKLMNOPQRSTUVWX', NULL, 'Full-stack developer who loves tech books.', '2026-02-12'),
('Carol', NULL, 'Wang', 'carol_w', 'carol@example.com', '$2a$10$abcdefghijklmnopqrstuuABCDEFGHIJKLMNOPQRSTUVWX', NULL, 'UX designer and occasional writer.', '2026-02-14'),
('David', 'Lee', 'Kim', 'dave_k', 'david@example.com', '$2a$10$abcdefghijklmnopqrstuuABCDEFGHIJKLMNOPQRSTUVWX', NULL, NULL, '2026-01-30'),
('Eve', NULL, 'Johnson', 'eve_j', 'eve@example.com', '$2a$10$abcdefghijklmnopqrstuuABCDEFGHIJKLMNOPQRSTUVWX', NULL, 'Ruby and Rails enthusiast.', '2026-02-08');

-- ----------------------
-- Books (cover_image loaded separately)
-- ----------------------
INSERT INTO book(isbn, title, slug, release_date, price, publisher_id, cover_image)
VALUES
('161729120X', 'Spring in Action, 4/e', 'spring-in-action-4e-1', '2014-11-27', 5.00, 'MP', NULL),
('1491901632', 'Hadoop: The Definitive Guide, 4/e', 'hadoop-the-definitive-guide-4e-2', '2015-04-10', 4.00, 'OA', NULL),
('193398869X', 'Secrets of the JavaScript Ninja', 'secrets-of-the-javascript-ninja-3', '2012-12-27', 7.00, 'MP', NULL),
('0764543857', 'Expert One-on-One J2EE Design and Development', 'expert-one-on-one-j2ee-design-and-development-4', '2002-09-30', 3.00, 'WP', NULL),
('1932394885', 'Java Persistence with Hibernate', 'java-persistence-with-hibernate-5', '2006-11-23', 7.00, 'MP', NULL),
('0596517742', 'JavaScript: The Good Parts', 'javascript-the-good-parts-6', '2008-05-30', 2.00, 'OA', NULL),
('0321683684', 'The Elements of User Experience', 'the-elements-of-user-experience-7', '2010-12-25', 10.00, 'NR', NULL),
('9863473316', '松本行弘談程式世界的未來', 'matsumoto-yukihiro-talks-about-the-future-of-programming-8', '2014-10-30', 8.00, 'GI', NULL),
('1937785564', 'Agile Web Development with Rails 4', 'agile-web-development-with-rails-4-9', '2013-10-07', 7.45, 'PB', NULL),
('013390069X', 'The Java Language Specification, Java SE 8', 'the-java-language-specification-java-se-8-10', '2014-05-15', 9.99, 'AW', NULL),
('0596009208', 'Head First Java, 2/e', 'head-first-java-2e-11', '2005-02-09', 6.50, 'OA', NULL);

-- ----------------------
-- Book <-> Author
-- ----------------------
INSERT INTO book_author(book_id, author_id) VALUES
(1,1),(2,2),(3,3),(3,4),(4,5),(5,6),(5,7),
(6,8),(7,9),(8,10),(9,11),(9,12),(9,13),
(10,14),(10,15),(10,16),(10,17),(10,18),
(11,19),(11,20);

-- ----------------------
-- Book <-> Genre
-- ----------------------
INSERT INTO book_genre(book_id, genre_id) VALUES
(1, 1),(1, 3),(1, 6),
(2, 1),(2, 9),
(3, 1),(3, 2),(3, 4),
(4, 3),(4, 6),
(5, 3),(5, 5),
(6, 4),(6, 2),
(7, 7),
(8, 1),(8, 10),
(9, 2),(9, 8),
(10, 3),(10, 10),
(11, 1),(11, 3);

-- ----------------------
-- Book Reviews
-- ----------------------
INSERT INTO book_review(user_id, book_id, content, likes_count, rating, is_featured) VALUES
(1, 1, 'Excellent introduction to Spring. Clear examples and well structured.', 12, 5, TRUE),
(2, 1, 'Good reference but some sections feel dated now.', 3, 4, FALSE),
(1, 2, 'The best Hadoop book out there. Very thorough.', 8, 5, TRUE),
(3, 3, 'Learned so many JavaScript tricks from this book!', 15, 5, TRUE),
(2, 4, 'A classic J2EE book. Still relevant for understanding design patterns.', 5, 4, FALSE),
(4, 5, 'Hibernate finally makes sense after reading this.', 7, 4, FALSE),
(3, 6, 'Short but packed with wisdom. Every JS dev should read this.', 20, 5, TRUE),
(5, 7, 'Great UX primer. Not too technical, very approachable.', 9, 4, FALSE),
(4, 9, 'Solid Rails tutorial. Good for beginners.', 4, 3, FALSE),
(1, 10, 'Dense but essential reading for serious Java developers.', 6, 4, FALSE);

-- ----------------------
-- Publisher No PK (mirror of publisher)
-- ----------------------
INSERT INTO publisher_no_pk VALUES
('MP', 'Manning Publication'),
('OA', 'O''Reilly & Associates'),
('WP', 'Wrox Press'),
('NR', 'New Riders'),
('GI', 'Gotop Information'),
('PB', 'Pragmatic Bookshelf'),
('AW', 'Addison Wesley');

-- =============================================
-- END OF SCRIPT
-- =============================================
