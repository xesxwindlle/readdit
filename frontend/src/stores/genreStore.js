import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '../util/api'
import { ENDPOINTS } from '../util/endpoints'

export const useGenreStore = defineStore('genre', () => {
  const genres = ref([])

  async function fetchGenres() {
    try {
      const { data: response } = await api.get(ENDPOINTS.GENRES)
      genres.value = response.data
    } catch {
      genres.value = []
    }
  }

  return { genres, fetchGenres }
})
