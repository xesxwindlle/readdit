<script setup>
import api from '../../util/api'
import { ENDPOINTS } from '../../util/endpoints'
import { useSingularToast } from '../../util/useSingularToast'
import { useUserStore } from '../../stores/userStore'
import { useGenreStore } from '../../stores/genreStore'
import { useForm } from 'vee-validate'
import { object, string, date } from 'yup'
import { AutoComplete, Button, Chip, DatePicker, Fieldset, InputText, Message, MultiSelect, Textarea } from 'primevue'
import { isAxiosError } from 'axios'
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const toast = useSingularToast()
const userStore = useUserStore()
const genreStore = useGenreStore()

const schema = object({
  title: string().required('Title is required').max(255),
  publisherName: string().required('Publisher name is required'),
  isbn: string().test('len', 'ISBN must be 10 or 13 digits', (v) => !v || v.length === 10 || v.length === 13),
  bookDescription: string(),
  releaseDate: date().max(new Date(), 'Cannot be in the future').nullable(),
  coverUrl: string().url('Must be a valid URL').nullable(),
})

const { defineField, handleSubmit, errors } = useForm({ validationSchema: schema, initialValues: { title: '', publisherName: '' } })
const [title] = defineField('title')
const [publisherName] = defineField('publisherName')
const [isbn] = defineField('isbn')
const [bookDescription] = defineField('bookDescription')
const [releaseDate] = defineField('releaseDate')
const [coverUrl] = defineField('coverUrl')
const [submitterComment] = defineField('submitterComment')

const selectedAuthors = ref([])
const selectedGenres = ref([])
const authorSearchResult = ref([])
const authorSearchInput = ref('')

async function searchAuthors(e) {
  try {
    const { data: response } = await api.get(ENDPOINTS.AUTHORS)
    const q = e.query.toLowerCase()
    authorSearchResult.value = (response.data || []).filter((a) => a.name?.toLowerCase().includes(q))
  } catch {}
}

function addAuthor(e) {
  if (!selectedAuthors.value.find((a) => a.id === e.value.id)) {
    selectedAuthors.value.push(e.value)
  }
  authorSearchInput.value = ''
}

const sortedGenres = computed(() => [...selectedGenres.value].sort((a, b) => a.name.localeCompare(b.name)))

const submit = handleSubmit(async (values) => {
  try {
    const payload = {
      submitterId: userStore.userId,
      title: values.title,
      publisherName: values.publisherName,
      isbn: values.isbn || null,
      bookDescription: values.bookDescription || null,
      releaseDate: values.releaseDate ? values.releaseDate.toISOString().split('T')[0] : null,
      coverUrl: values.coverUrl || null,
      submitterComment: values.submitterComment || null,
      authorIds: selectedAuthors.value.map((a) => a.id),
      genreIds: selectedGenres.value.map((g) => g.id),
    }
    const { data: response } = await api.post(ENDPOINTS.BOOK_SUBMISSIONS, payload)
    toast({ severity: 'success', summary: 'Submission created!', group: 'message', life: 3000 })
    router.push(`/submissions/books/${response.data.id}`)
  } catch (error) {
    const msg = isAxiosError(error) ? `Error ${error.response?.status}` : 'Unknown error'
    toast({ severity: 'error', summary: msg, group: 'message', life: 4000 })
  }
}, () => {
  toast({ severity: 'error', summary: 'Please fix form errors', group: 'message', life: 3000 })
})
</script>

<template>
  <section class="w-[min(100%,80em)] px-4 py-4">
    <div class="px-2 py-2 mb-4">
      <h1 class="text-4xl">Submit a new book</h1>
    </div>
    <Fieldset legend="Before you submit" class="mb-6 max-w-3xl">
      <p class="text-sm text-neutral-500">Your submission will be reviewed by a moderator before it appears on the site.</p>
    </Fieldset>

    <form class="flex flex-col gap-5 max-w-3xl" @submit.prevent="submit">
      <div class="flex flex-col gap-1">
        <label>Title</label>
        <InputText v-model="title" :invalid="!!errors.title" />
        <Transition>
          <div v-if="errors.title">
            <Message severity="error" variant="simple" size="small">{{ errors.title }}</Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1">
        <label>Publisher name</label>
        <InputText v-model="publisherName" :invalid="!!errors.publisherName" />
        <Transition>
          <div v-if="errors.publisherName">
            <Message severity="error" variant="simple" size="small">{{ errors.publisherName }}</Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1">
        <label>Authors <span class="text-neutral-400 text-xs">(optional)</span></label>
        <AutoComplete
          v-model="authorSearchInput"
          :delay="300"
          :suggestions="authorSearchResult"
          optionLabel="name"
          placeholder="Search for authors"
          @complete="searchAuthors"
          @option-select="addAuthor"
        >
          <template #option="{ option }">
            <span>{{ option.name }}</span>
          </template>
        </AutoComplete>
        <div class="flex flex-wrap gap-2 mt-1">
          <Chip
            v-for="author in selectedAuthors"
            :key="author.id"
            :label="author.name"
            removable
            @remove="selectedAuthors = selectedAuthors.filter(a => a.id !== author.id)"
          />
        </div>
      </div>

      <div class="flex flex-col gap-1">
        <label>Genres <span class="text-neutral-400 text-xs">(optional)</span></label>
        <MultiSelect
          v-model="selectedGenres"
          :options="genreStore.genres"
          optionLabel="name"
          placeholder="Select genres"
          filter
          class="self-start"
        />
        <div class="flex flex-wrap gap-2 mt-1">
          <Chip
            v-for="genre in sortedGenres"
            :key="genre.id"
            :label="genre.name"
            removable
            @remove="selectedGenres = selectedGenres.filter(g => g.id !== genre.id)"
          />
        </div>
      </div>

      <div class="flex flex-col gap-1">
        <label>ISBN <span class="text-neutral-400 text-xs">(optional)</span></label>
        <InputText v-model="isbn" :invalid="!!errors.isbn" placeholder="10 or 13 digits" />
        <Transition>
          <div v-if="errors.isbn">
            <Message severity="error" variant="simple" size="small">{{ errors.isbn }}</Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1">
        <label>Release date <span class="text-neutral-400 text-xs">(optional)</span></label>
        <DatePicker v-model="releaseDate" :maxDate="new Date()" dateFormat="MM dd, yy" class="self-start" />
        <Transition>
          <div v-if="errors.releaseDate">
            <Message severity="error" variant="simple" size="small">{{ errors.releaseDate }}</Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1">
        <label>Cover URL <span class="text-neutral-400 text-xs">(optional)</span></label>
        <InputText v-model="coverUrl" :invalid="!!errors.coverUrl" placeholder="https://..." />
        <Transition>
          <div v-if="errors.coverUrl">
            <Message severity="error" variant="simple" size="small">{{ errors.coverUrl }}</Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1">
        <label>Description <span class="text-neutral-400 text-xs">(optional)</span></label>
        <Textarea v-model="bookDescription" rows="4" autoResize />
      </div>

      <div class="flex flex-col gap-1">
        <label>Comment for reviewer <span class="text-neutral-400 text-xs">(optional)</span></label>
        <Textarea v-model="submitterComment" rows="3" placeholder="Any notes for the moderator..." autoResize />
      </div>

      <div class="flex gap-3 mt-2">
        <Button type="submit" label="Submit" />
        <RouterLink to="/submissions/books">
          <Button label="Cancel" severity="secondary" />
        </RouterLink>
      </div>
    </form>
  </section>
</template>

<style scoped>
@reference "tailwindcss";
.v-enter-active, .v-leave-active { transition: opacity 0.2s ease; }
.v-enter-from, .v-leave-to { opacity: 0; }
</style>
