import SubmissionsIndex from '../pages/submissions/SubmissionsIndex.vue'
import BookSubmissionsIndex from '../pages/submissions/BookSubmissionsIndex.vue'
import BookSubmissionView from '../pages/submissions/BookSubmissionView.vue'
import NewBookSubmission from '../pages/submissions/NewBookSubmission.vue'
import AuthorSubmissionsIndex from '../pages/submissions/AuthorSubmissionsIndex.vue'
import AuthorSubmissionView from '../pages/submissions/AuthorSubmissionView.vue'
import NewAuthorSubmission from '../pages/submissions/NewAuthorSubmission.vue'

const submissionsRoutes = [
  { path: '/submissions', component: SubmissionsIndex },
  { path: '/submissions/books', component: BookSubmissionsIndex },
  { path: '/submissions/books/new', component: NewBookSubmission, meta: { requiresAuth: true } },
  { path: '/submissions/books/:id', component: BookSubmissionView },
  { path: '/submissions/authors', component: AuthorSubmissionsIndex },
  { path: '/submissions/authors/new', component: NewAuthorSubmission, meta: { requiresAuth: true } },
  { path: '/submissions/authors/:id', component: AuthorSubmissionView },
]

export default submissionsRoutes
