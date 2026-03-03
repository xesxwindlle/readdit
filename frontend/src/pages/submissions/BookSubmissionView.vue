<script setup>
import api from '../../util/api'
import { ENDPOINTS } from '../../util/endpoints'
import { useSingularToast } from '../../util/useSingularToast'
import { useUserStore } from '../../stores/userStore'
import { roles } from '../../util/enums'
import { Button, Divider, Fieldset, ProgressSpinner, Textarea } from 'primevue'
import { useConfirm } from 'primevue/useconfirm'
import { isAxiosError } from 'axios'
import { onBeforeMount, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const toast = useSingularToast()
const confirm = useConfirm()

const id = route.params.id
const submission = ref(null)
const isLoading = ref(true)
const error = ref(null)

async function load() {
  const { data: response } = await api.get(ENDPOINTS.BOOK_SUBMISSION_BY_ID(id))
  submission.value = response.data
}

onBeforeMount(async () => {
  try { await load() } catch (e) {
    error.value = isAxiosError(e) && e.response?.status === 404 ? '404 Not found' : 'Error loading submission'
  } finally { isLoading.value = false }
})

const reviewComment = ref('')

async function submitReview(status) {
  try {
    await api.patch(ENDPOINTS.BOOK_SUBMISSION_REVIEW(id), {
      reviewStatus: status,
      reviewerComment: reviewComment.value || null,
      reviewerId: userStore.userId,
    })
    await load()
    toast({ severity: 'success', summary: `Submission ${status}`, group: 'message', life: 3000 })
    reviewComment.value = ''
  } catch {
    toast({ severity: 'error', summary: 'Failed to submit review', group: 'message', life: 3000 })
  }
}

function confirmDelete() {
  confirm.require({
    message: 'Are you sure you want to delete this submission?',
    header: 'Confirm Delete',
    acceptSeverity: 'danger',
    accept: async () => {
      try {
        await api.delete(ENDPOINTS.BOOK_SUBMISSION_BY_ID(id))
        toast({ severity: 'success', summary: 'Submission deleted', group: 'message', life: 3000 })
        router.push('/submissions/books')
      } catch {
        toast({ severity: 'error', summary: 'Failed to delete', group: 'message', life: 3000 })
      }
    },
  })
}

// --- AI Verify ---
const aiResult = ref('')
const aiLoading = ref(false)

async function runAiVerify() {
  aiResult.value = ''
  aiLoading.value = true
  const s = submission.value
  const prompt = `You are a book submission validator. Review the following book submission and identify any potential issues:
- Missing important fields
- Potentially incorrect or suspicious information
- Any concerns a moderator should be aware of before approving

Submission details:
Title: ${s.title}
Publisher: ${s.publisherName || 'N/A'}
ISBN: ${s.isbn || 'N/A'}
Release date: ${s.releaseDate || 'N/A'}
Authors: ${s.authorNames?.join(', ') || 'N/A'}
Genres: ${s.genreNames?.join(', ') || 'N/A'}
Description: ${s.bookDescription || 'N/A'}
Submitter comment: ${s.submitterComment || 'N/A'}

Provide a brief assessment. Be specific about what looks correct, what is missing, and what might need verification. Respond in English.`

  try {
    const { data } = await api.post(ENDPOINTS.CHAT, { prompt })
    aiResult.value = data.content
  } catch {
    aiResult.value = 'Failed to run AI verification. Please try again.'
  } finally {
    aiLoading.value = false
  }
}
</script>

<template>
  <section class="w-[min(100%,80em)] px-4 py-4">
    <div v-if="isLoading" class="text-neutral-400 text-center py-8">Loading...</div>
    <div v-else-if="error" class="text-center py-8"><h1 class="text-2xl">{{ error }}</h1></div>
    <div v-else-if="submission" class="flex flex-col gap-6 max-w-3xl mx-2">
      <div class="flex items-start gap-3">
        <h1 class="text-4xl font-black">{{ submission.title }}</h1>
        <span
          class="text-xs px-2 py-1 rounded font-semibold mt-2 shrink-0"
          :class="{
            'bg-yellow-100 text-yellow-700': submission.reviewStatus === 'pending',
            'bg-green-100 text-green-700': submission.reviewStatus === 'approved',
            'bg-red-100 text-red-700': submission.reviewStatus === 'rejected',
          }"
        >{{ submission.reviewStatus }}</span>
      </div>

      <dl class="grid grid-cols-[160px_1fr] text-sm gap-y-2">
        <dt class="font-semibold">Publisher</dt>
        <dd>{{ submission.publisherName ?? 'N/A' }}</dd>
        <dt class="font-semibold">ISBN</dt>
        <dd>{{ submission.isbn ?? 'N/A' }}</dd>
        <dt class="font-semibold">Release date</dt>
        <dd>{{ submission.releaseDate ?? 'N/A' }}</dd>
        <dt v-if="submission.authorNames?.length" class="font-semibold">Authors</dt>
        <dd v-if="submission.authorNames?.length">{{ submission.authorNames.join(', ') }}</dd>
        <dt v-if="submission.genreNames?.length" class="font-semibold">Genres</dt>
        <dd v-if="submission.genreNames?.length">{{ submission.genreNames.join(', ') }}</dd>
        <dt v-if="submission.submitterDisplayName" class="font-semibold">Submitted by</dt>
        <dd v-if="submission.submitterDisplayName">{{ submission.submitterDisplayName }}</dd>
        <dt v-if="submission.submitterComment" class="font-semibold">Submitter comment</dt>
        <dd v-if="submission.submitterComment" class="italic text-neutral-500">{{ submission.submitterComment }}</dd>
      </dl>

      <Fieldset v-if="submission.bookDescription" legend="Description">
        <p class="whitespace-pre-wrap text-neutral-700 text-sm leading-relaxed">{{ submission.bookDescription }}</p>
      </Fieldset>

      <Divider />

      <!-- AI Verify — moderators and admins only -->
      <div v-if="userStore.role >= roles.moderator" class="flex flex-col gap-3">
        <div class="flex items-center gap-3">
          <h2 class="text-xl font-semibold">AI Verification</h2>
          <Button label="Run AI Verify" severity="secondary" size="small" :loading="aiLoading" @click="runAiVerify" />
        </div>
        <div v-if="aiLoading" class="flex items-center gap-2 text-sm text-neutral-400">
          <ProgressSpinner style="width:20px;height:20px" />
          <span>Analyzing submission...</span>
        </div>
        <Fieldset v-else-if="aiResult" legend="AI Assessment">
          <p class="text-sm leading-relaxed whitespace-pre-wrap text-neutral-700">{{ aiResult }}</p>
        </Fieldset>
      </div>

      <Divider v-if="userStore.role >= roles.moderator" />

      <div v-if="userStore.role >= roles.moderator && submission.reviewStatus === 'pending'" class="flex flex-col gap-3">
        <h2 class="text-2xl">Review this submission</h2>
        <Textarea v-model="reviewComment" rows="3" placeholder="Reviewer comment (optional)" autoResize />
        <div class="flex gap-3">
          <Button label="Approve" severity="success" @click="submitReview('approved')" />
          <Button label="Reject" severity="danger" @click="submitReview('rejected')" />
        </div>
      </div>

      <div v-else-if="submission.reviewStatus !== 'pending'" class="flex flex-col gap-2">
        <h2 class="text-xl font-semibold">Review</h2>
        <dl class="grid grid-cols-[160px_1fr] text-sm gap-y-2">
          <dt class="font-semibold">Reviewed by</dt>
          <dd>{{ submission.reviewerDisplayName ?? 'N/A' }}</dd>
          <dt v-if="submission.reviewerComment" class="font-semibold">Reviewer comment</dt>
          <dd v-if="submission.reviewerComment" class="italic text-neutral-500">{{ submission.reviewerComment }}</dd>
        </dl>
      </div>

      <div v-if="userStore.role >= roles.admin" class="mt-2">
        <Button label="Delete submission" severity="danger" @click="confirmDelete" />
      </div>
    </div>
  </section>
</template>
