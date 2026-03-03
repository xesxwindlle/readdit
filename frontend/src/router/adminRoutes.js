import BooksAdmin from '../pages/admin/BooksAdmin.vue'
import AuthorsAdmin from '../pages/admin/AuthorsAdmin.vue'
import GenresAdmin from '../pages/admin/GenresAdmin.vue'
import PublishersAdmin from '../pages/admin/PublishersAdmin.vue'
import UsersAdmin from '../pages/admin/UsersAdmin.vue'

const adminRoutes = [
  { path: '/admin/books', component: BooksAdmin, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/authors', component: AuthorsAdmin, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/genres', component: GenresAdmin, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/publishers', component: PublishersAdmin, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/users', component: UsersAdmin, meta: { requiresAuth: true, requiresAdmin: true } },
]

export default adminRoutes
