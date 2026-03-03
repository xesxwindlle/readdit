<script setup>
import defaultCover from '../assets/default_cover.svg'
import api from '../util/api'
import { ENDPOINTS } from '../util/endpoints'
import { useSingularToast } from '../util/useSingularToast'
import { useUserStore } from '../stores/userStore'
import ReviewBlock from '../components/ReviewBlock.vue'
import { Button, Dialog, Divider, ProgressSpinner, Rating, Textarea } from 'primevue'
import { Icon } from '@iconify/vue'
import { isAxiosError } from 'axios'
import { marked } from 'marked'
import { computed, onBeforeMount, ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const userStore = useUserStore()
const toast = useSingularToast()

const slug = route.params.slug
const imageSrc = computed(() => {
  if (!book.value) return defaultCover
  if (book.value.coverUrl) return book.value.coverUrl
  if (book.value.coverImage) return `data:image/jpeg;base64,${book.value.coverImage}`
  return defaultCover
})
const book = ref(null)
const reviews = ref([])
const isLoading = ref(true)
const error = ref(null)

onBeforeMount(async () => {
  try {
    const [bookRes, reviewsRes] = await Promise.all([
      api.get(ENDPOINTS.BOOK_BY_SLUG(slug)),
      api.get(ENDPOINTS.BOOK_REVIEWS(slug)),
    ])
    book.value = bookRes.data.data
    reviews.value = reviewsRes.data.data || []
  } catch (e) {
    error.value = isAxiosError(e) && e.response?.status === 404 ? '404 Book not found' : 'Unknown error'
  } finally {
    isLoading.value = false
  }
})

// --- Write a review ---
const dialogVisible = ref(false)
const rating = ref(0)
const reviewContent = ref('')

async function submitReview() {
  if (!userStore.isLoggedIn || !rating.value) return
  try {
    await api.post(ENDPOINTS.BOOK_REVIEWS(slug), {
      rating: rating.value,
      content: reviewContent.value || null,
      userId: userStore.userId,
    })
    const reviewsRes = await api.get(ENDPOINTS.BOOK_REVIEWS(slug))
    reviews.value = reviewsRes.data.data || []
    toast({ severity: 'success', summary: 'Review submitted!', group: 'message', life: 3000 })
  } catch {
    toast({ severity: 'error', summary: 'Failed to submit review', group: 'message', life: 3000 })
  } finally {
    dialogVisible.value = false
    rating.value = 0
    reviewContent.value = ''
  }
}

// --- AI Summary ---
const aiDialogVisible = ref(false)
const aiSummary = ref('')
const aiLoading = ref(false)

async function fetchAiSummary() {
  aiDialogVisible.value = true
  aiSummary.value = ''
  aiLoading.value = true
  const b = book.value
  const prompt = `You are a helpful book assistant. Based on the following book information, provide a brief summary including:
- A short overview of what this book is likely about according to the Internet
- Who this book would be suitable for
- Overall impression based on the available information

Book information:
Title: ${b.title}
Authors: ${b.authorNames?.join(', ') || 'N/A'}
Genres: ${b.genreNames?.join(', ') || 'N/A'}
Publisher: ${b.publisherName || 'N/A'}
Release date: ${b.releaseDate || 'N/A'}
ISBN: ${b.isbn || 'N/A'}

Keep your response concise and helpful. Respond in English.`

  try {
    const { data } = await api.post(ENDPOINTS.CHAT, { prompt })
    aiSummary.value = data.content
  } catch {
    aiSummary.value = 'Failed to generate summary. Please try again.'
  } finally {
    aiLoading.value = false
  }
}
</script>

<template>
  <section class="w-[min(100%,80em)] px-4 py-4">
    <div v-if="isLoading" class="text-neutral-400 py-8 text-center">Loading...</div>
    <div v-else-if="error" class="py-8 text-center">
      <h1 class="text-2xl">{{ error }}</h1>
    </div>
    <div v-else-if="book" class="flex flex-col gap-8 mx-2">
      <div class="flex flex-col md:grid md:grid-cols-[150px_1fr] gap-6">
        <img :src="imageSrc" :alt="book.title" class="w-[150px] h-auto mx-auto md:mx-0" />

        <div class="flex flex-col gap-3">
          <div>
            <h1 class="text-4xl font-black">{{ book.title }}</h1>
            <p v-if="book.isbn" class="text-neutral-400 text-sm mt-1">ISBN: {{ book.isbn }}</p>
          </div>

          <dl class="grid grid-cols-[150px_1fr] text-sm gap-y-2">
            <dt v-if="book.authorNames?.length" class="font-semibold">Authors</dt>
            <dd v-if="book.authorNames?.length">{{ book.authorNames.join(', ') }}</dd>
            <dt v-if="book.genreNames?.length" class="font-semibold">Genres</dt>
            <dd v-if="book.genreNames?.length">{{ book.genreNames.join(', ') }}</dd>
            <dt v-if="book.publisherName" class="font-semibold">Publisher</dt>
            <dd v-if="book.publisherName">{{ book.publisherName }}</dd>
            <dt v-if="book.releaseDate" class="font-semibold">Release date</dt>
            <dd v-if="book.releaseDate">{{ book.releaseDate }}</dd>
          </dl>

          <div class="mt-2 flex flex-wrap gap-2">
            <Button
              v-if="userStore.isLoggedIn"
              label="Write a review"
              @click="dialogVisible = true"
            />
            <RouterLink v-else to="/login">
              <Button label="Log in to write a review" severity="secondary" />
            </RouterLink>
            <Button label="AI Summary" severity="secondary" @click="fetchAiSummary" />
          </div>
        </div>
      </div>

      <Divider />

      <div>
        <h2 class="text-2xl mb-4">Reviews ({{ reviews.length }})</h2>
        <div v-if="reviews.length === 0" class="text-neutral-400">No reviews yet. Be the first!</div>
        <ul v-else class="flex flex-col">
          <li v-for="(review, index) in reviews" :key="review.id">
            <ReviewBlock :review="review" />
            <Divider v-if="index !== reviews.length - 1" class="!my-0" />
          </li>
        </ul>
      </div>
    </div>

    <!-- Write a review dialog -->
    <Dialog
      modal
      position="center"
      header="Write a review"
      :draggable="false"
      v-model:visible="dialogVisible"
      class="w-[min(100%,500px)] mx-4"
    >
      <div class="flex flex-col gap-4">
        <div class="flex flex-col gap-1">
          <label class="text-sm font-semibold">Rating</label>
          <Rating v-model="rating" :stars="5">
            <template #officon>
              <Icon icon="tdesign:star-filled" width="22" height="22" class="text-neutral-300" />
            </template>
            <template #onicon>
              <Icon icon="tdesign:star-filled" width="22" height="22" class="text-yellow-400" />
            </template>
          </Rating>
        </div>
        <div class="flex flex-col gap-1">
          <label class="text-sm font-semibold">Review</label>
          <Textarea v-model="reviewContent" rows="4" placeholder="Share your thoughts..." autoResize />
        </div>
        <div class="flex gap-3 self-end mt-2">
          <Button label="Cancel" severity="secondary" @click="dialogVisible = false" />
          <Button label="Submit" :disabled="!rating" @click="submitReview" />
        </div>
      </div>
    </Dialog>

    <!-- AI Summary dialog -->
    <Dialog
      modal
      position="center"
      header="AI Summary"
      :draggable="false"
      v-model:visible="aiDialogVisible"
      class="w-[min(100%,600px)] mx-4"
    >
      <div class="flex flex-col gap-4 min-h-[100px]">
        <div v-if="aiLoading" class="flex justify-center items-center py-8">
          <ProgressSpinner style="width:40px;height:40px" />
        </div>
        <div v-else class="ai-content text-sm text-neutral-700" v-html="marked(aiSummary)" />
      </div>
    </Dialog>
  </section>
</template>

<style scoped>
.ai-content :deep(h1), .ai-content :deep(h2), .ai-content :deep(h3) {
  font-weight: 700;
  margin-top: 0.75rem;
  margin-bottom: 0.25rem;
}
.ai-content :deep(h1) { font-size: 1.1rem; }
.ai-content :deep(h2) { font-size: 1rem; }
.ai-content :deep(ul), .ai-content :deep(ol) {
  padding-left: 1.25rem;
  margin: 0.25rem 0;
}
.ai-content :deep(li) { margin: 0.15rem 0; }
.ai-content :deep(p) { margin: 0.4rem 0; }
</style>
