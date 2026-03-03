import AuthorsIndex from '../pages/AuthorsIndex.vue'
import AuthorProfile from '../pages/AuthorProfile.vue'

const authorRoutes = [
  { path: '/authors', component: AuthorsIndex },
  { path: '/authors/:slug', component: AuthorProfile },
]

export default authorRoutes
