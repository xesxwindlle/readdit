export const ENDPOINTS = {
  // Auth
  LOGIN: '/auth/login',
  REGISTER: '/auth/register',
  VERIFY: '/auth/verify',

  // Users
  USERS: '/users',
  USER_BY_ID: (id) => `/users/${id}`,
  USER_ROLE: (userId, role) => `/users/${userId}/role/${role}`,

  // Authors
  AUTHORS: '/authors',
  AUTHOR_BY_SLUG: (slug) => `/authors/${slug}`,
  AUTHORS_SEARCH: (name) => `/authors?name=${encodeURIComponent(name)}`,

  // Books
  BOOKS: '/books',
  BOOK_BY_SLUG: (slug) => `/books/${slug}`,
  BOOK_REVIEWS: (slug) => `/books/${slug}/reviews`,
  BOOK_TOP_PRICES: (n) => `/books/top/prices?n=${n}`,
  BOOK_ADD_GENRE: (slug) => `/books/${slug}/genres`,
  BOOK_REMOVE_GENRE: (slug, genreId) => `/books/${slug}/genres/${genreId}`,
  BOOK_REVIEW_BY_ID: (id) => `/book-reviews/${id}`,

  // Genres
  GENRES: '/genres',
  GENRE_BY_ID: (id) => `/genres/${id}`,

  // Publishers
  PUBLISHERS: '/publishers',
  PUBLISHER_BY_ID: (id) => `/publishers/${id}`,

  // Book submissions
  BOOK_SUBMISSIONS: '/book-submissions',
  BOOK_SUBMISSION_BY_ID: (id) => `/book-submissions/${id}`,
  BOOK_SUBMISSION_BY_STATUS: (status) => `/book-submissions/status/${status}`,
  BOOK_SUBMISSION_BY_SUBMITTER: (submitterId) => `/book-submissions/submitter/${submitterId}`,
  BOOK_SUBMISSION_REVIEW: (id) => `/book-submissions/${id}/review`,

  // AI Chat
  CHAT: '/api/chat',

  // Author submissions
  AUTHOR_SUBMISSIONS: '/author-submissions',
  AUTHOR_SUBMISSION_BY_ID: (id) => `/author-submissions/${id}`,
  AUTHOR_SUBMISSION_BY_STATUS: (status) => `/author-submissions/status/${status}`,
  AUTHOR_SUBMISSION_BY_SUBMITTER: (submitterId) => `/author-submissions/submitter/${submitterId}`,
  AUTHOR_SUBMISSION_REVIEW: (id) => `/author-submissions/${id}/review`,
}
