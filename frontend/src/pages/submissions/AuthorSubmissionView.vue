<script setup>
import api from '../../util/api'
import { ENDPOINTS } from '../../util/endpoints'
import { useSingularToast } from '../../util/useSingularToast'
import { useUserStore } from '../../stores/userStore'
import { roles } from '../../util/enums'
import { Button, Divider, Fieldset, Textarea } from 'primevue'
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
  const { data: response } = await api.get(ENDPOINTS.AUTHOR_SUBMISSION_BY_ID(id))
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
    await api.patch(ENDPOINTS.AUTHOR_SUBMISSION_REVIEW(id), {
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
        await api.delete(ENDPOINTS.AUTHOR_SUBMISSION_BY_ID(id))
        toast({ severity: 'success', summary: 'Submission deleted', group: 'message', life: 3000 })
        router.push('/submissions/authors')
      } catch {
        toast({ severity: 'error', summary: 'Failed to delete', group: 'message', life: 3000 })
      }
    },
  })
}
</script>

<template>
  <section class="w-[min(100%,80em)] px-4 py-4">
    <div v-if="isLoading" class="text-neutral-400 text-center py-8">Loading...</div>
    <div v-else-if="error" class="text-center py-8"><h1 class="text-2xl">{{ error }}</h1></div>
    <div v-else-if="submission" class="flex flex-col gap-6 max-w-3xl mx-2">
      <div class="flex items-start gap-3">
        <h1 class="text-4xl font-black">{{ submission.authorName }}</h1>
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
        <dt class="font-semibold">Date of birth</dt>
        <dd>{{ submission.dateOfBirth ?? 'N/A' }}</dd>
        <dt class="font-semibold">Date of death</dt>
        <dd>{{ submission.dateOfDeath ?? 'N/A' }}</dd>
        <dt v-if="submission.submitterDisplayName" class="font-semibold">Submitted by</dt>
        <dd v-if="submission.submitterDisplayName">{{ submission.submitterDisplayName }}</dd>
        <dt v-if="submission.submitterComment" class="font-semibold">Submitter comment</dt>
        <dd v-if="submission.submitterComment" class="italic text-neutral-500">{{ submission.submitterComment }}</dd>
      </dl>

      <Fieldset v-if="submission.biography" legend="Biography">
        <p class="whitespace-pre-wrap text-neutral-700 text-sm leading-relaxed">{{ submission.biography }}</p>
      </Fieldset>

      <Divider />

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
