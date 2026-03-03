<script setup>
import api from '../util/api'
import { ENDPOINTS } from '../util/endpoints'
import BookCard from '../components/BookCard.vue'
import AuthorCard from '../components/AuthorCard.vue'
import { Divider } from 'primevue'
import { onBeforeMount, ref } from 'vue'

const books = ref([])
const recentAuthors = ref([])
const isLoading = ref(true)

onBeforeMount(async () => {
  try {
    const [booksRes, authorsRes] = await Promise.all([
      api.get(ENDPOINTS.BOOKS),
      api.get(ENDPOINTS.AUTHORS),
    ])
    books.value = booksRes.data.data || []
    recentAuthors.value = (authorsRes.data.data || []).slice(0, 5)
  } catch {}
  finally {
    isLoading.value = false
  }
})
</script>

<template>
  <section class="w-[min(100%,80em)] px-4">
    <div class="px-2 py-4">
      <h1 class="text-4xl">Welcome to Readdit</h1>
      <p class="text-neutral-500">Discover books, explore authors, and share your reading journey.</p>
    </div>

    <div v-if="isLoading" class="text-neutral-400 py-8 text-center">Loading...</div>
    <div v-else class="flex flex-col lg:grid lg:grid-cols-[2fr_1fr] lg:items-start gap-10">
      <div>
        <h2 class="px-2 mb-2 text-2xl">All Books</h2>
        <div v-if="books.length === 0" class="text-neutral-400 px-2">No books found.</div>
        <ul v-else class="flex flex-col">
          <li v-for="(book, index) in books" :key="book.id">
            <BookCard :book="book" />
            <Divider v-if="index !== books.length - 1" class="!my-0" />
          </li>
        </ul>
      </div>

      <div>
        <h2 class="px-2 mb-2 text-2xl">Authors</h2>
        <div v-if="recentAuthors.length === 0" class="text-neutral-400 px-2">No authors found.</div>
        <ul v-else class="flex flex-col">
          <li v-for="(author, index) in recentAuthors" :key="author.id">
            <AuthorCard :author="author" />
            <Divider v-if="index !== recentAuthors.length - 1" class="!my-0" />
          </li>
        </ul>
      </div>
    </div>
  </section>
</template>
