import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/userStore'
import { roles } from '../util/enums'

import authRoutes from './authRoutes'
import bookRoutes from './bookRoutes'
import authorRoutes from './authorRoutes'
import submissionsRoutes from './submissionsRoutes'
import adminRoutes from './adminRoutes'

const routes = [
  ...bookRoutes,
  ...authorRoutes,
  ...authRoutes,
  ...submissionsRoutes,
  ...adminRoutes,
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

router.beforeEach(async (to) => {
  if (to.meta.requiresAuth) {
    const userStore = useUserStore()
    if (!userStore.isLoggedIn) {
      await userStore.verify()
    }
    if (!userStore.isLoggedIn) {
      return '/login'
    }
    if (to.meta.requiresAdmin && userStore.role < roles.admin) {
      return '/'
    }
  }
})

export default router
