<script setup>
import api from '../util/api'
import { ENDPOINTS } from '../util/endpoints'
import AuthorCard from '../components/AuthorCard.vue'
import { Divider } from 'primevue'
import { onBeforeMount, ref } from 'vue'

const authors = ref([])
const isLoading = ref(true)

onBeforeMount(async () => {
  try {
    const { data: response } = await api.get(ENDPOINTS.AUTHORS)
    authors.value = response.data || []
  } catch {}
  finally {
    isLoading.value = false
  }
})
</script>

<template>
  <section class="w-[min(100%,80em)] px-4">
    <div class="px-2 py-4">
      <h1 class="text-4xl">Authors</h1>
    </div>
    <div v-if="isLoading" class="text-neutral-400 py-8 text-center">Loading...</div>
    <div v-else>
      <div v-if="authors.length === 0" class="text-neutral-400 px-2">No authors found.</div>
      <ul v-else class="flex flex-col">
        <li v-for="(author, index) in authors" :key="author.id">
          <AuthorCard :author="author" />
          <Divider v-if="index !== authors.length - 1" class="!my-0" />
        </li>
      </ul>
    </div>
  </section>
</template>
