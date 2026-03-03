import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import api from '../util/api'
import { ENDPOINTS } from '../util/endpoints'
import { roles } from '../util/enums'

export const useUserStore = defineStore('user', () => {
  const userId = ref(null)
  const displayName = ref(null)
  const email = ref(null)
  const role = ref(roles.guest)

  const isLoggedIn = computed(() => role.value >= roles.user)

  function setUser(data) {
    userId.value = data.id
    displayName.value = data.displayName
    email.value = data.email
    role.value = parseRole(data.role)
  }

  function clearUser() {
    userId.value = null
    displayName.value = null
    email.value = null
    role.value = roles.guest
    localStorage.removeItem('token')
  }

  function parseRole(roleStr) {
    switch (roleStr) {
      case 'ADMIN': return roles.admin
      case 'MODERATOR': return roles.moderator
      case 'USER': return roles.user
      default: return roles.guest
    }
  }

  let lastVerified = null

  async function verify() {
    const token = localStorage.getItem('token')
    if (!token) return

    // Only re-verify after 30 minutes
    const now = new Date()
    if (lastVerified && now.getTime() - lastVerified.getTime() < 1800000) return

    try {
      const { data: response } = await api.get(ENDPOINTS.VERIFY)
      setUser(response.data)
      lastVerified = new Date()
    } catch {
      clearUser()
    }
  }

  return { userId, displayName, email, role, isLoggedIn, setUser, clearUser, verify }
})
