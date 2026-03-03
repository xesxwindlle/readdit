<script setup>
import api from '../../util/api'
import { ENDPOINTS } from '../../util/endpoints'
import { useSingularToast } from '../../util/useSingularToast'
import { Button, Column, DataTable, DatePicker, Dialog, InputText, Message, MultiSelect, Select } from 'primevue'
import { useConfirm } from 'primevue/useconfirm'
import { useForm } from 'vee-validate'
import { object, string } from 'yup'
import { onBeforeMount, ref } from 'vue'

const toast = useSingularToast()
const confirm = useConfirm()

const books = ref([])
const publishers = ref([])
const authors = ref([])
const genres = ref([])
const isLoading = ref(true)
const dialogVisible = ref(false)
const editingBook = ref(null)

const selectedPublisher = ref(null)
const selectedAuthors = ref([])
const selectedGenres = ref([])
const coverUrl = ref('')
const coverImageBase64 = ref(null)
const coverFileInput = ref(null)

onBeforeMount(async () => {
  await Promise.all([loadBooks(), loadPublishers(), loadAuthors(), loadGenres()])
  isLoading.value = false
})

async function loadBooks() {
  const { data: response } = await api.get(ENDPOINTS.BOOKS)
  books.value = response.data || []
}

async function loadPublishers() {
  const { data: response } = await api.get(ENDPOINTS.PUBLISHERS)
  publishers.value = response.data || []
}

async function loadAuthors() {
  const { data: response } = await api.get(ENDPOINTS.AUTHORS)
  authors.value = response.data || []
}

async function loadGenres() {
  const { data: response } = await api.get(ENDPOINTS.GENRES)
  genres.value = response.data || []
}

const schema = object({
  title: string().required('Title is required'),
  isbn: string().nullable(),
})

const { defineField, handleSubmit, errors, resetForm, setValues } = useForm({ validationSchema: schema })
const [title] = defineField('title')
const [isbn] = defineField('isbn')
const [releaseDate] = defineField('releaseDate')

function onFileChange(e) {
  const file = e.target.files[0]
  if (!file) { coverImageBase64.value = null; return }
  const reader = new FileReader()
  reader.onload = () => { coverImageBase64.value = reader.result.split(',')[1] }
  reader.readAsDataURL(file)
}

function openCreate() {
  editingBook.value = null
  resetForm()
  selectedPublisher.value = null
  selectedAuthors.value = []
  selectedGenres.value = []
  coverUrl.value = ''
  coverImageBase64.value = null
  dialogVisible.value = true
}

function openEdit(book) {
  editingBook.value = book
  setValues({ title: book.title, isbn: book.isbn || '', releaseDate: book.releaseDate ? new Date(book.releaseDate) : null })
  selectedPublisher.value = publishers.value.find(p => p.id === book.publisherId) || null
  selectedAuthors.value = (book.authorNames || []).map(n => authors.value.find(a => a.name === n)).filter(Boolean)
  selectedGenres.value = (book.genreNames || []).map(n => genres.value.find(g => g.name === n)).filter(Boolean)
  coverUrl.value = book.coverUrl || ''
  coverImageBase64.value = null
  dialogVisible.value = true
}

const submit = handleSubmit(async (values) => {
  if (!selectedPublisher.value) {
    toast({ severity: 'error', summary: 'Publisher is required', group: 'message', life: 3000 })
    return
  }
  try {
    const payload = {
      title: values.title,
      isbn: values.isbn || null,
      publisherId: selectedPublisher.value.id,
      releaseDate: values.releaseDate instanceof Date ? values.releaseDate.toISOString().split('T')[0] : (values.releaseDate || null),
      authorIds: selectedAuthors.value.map(a => a.id),
      genreIds: selectedGenres.value.map(g => g.id),
      ...(coverUrl.value ? { coverUrl: coverUrl.value } : {}),
      ...(coverImageBase64.value ? { coverImage: coverImageBase64.value } : {}),
    }
    if (editingBook.value) {
      await api.patch(ENDPOINTS.BOOK_BY_SLUG(editingBook.value.slug), payload)
      toast({ severity: 'success', summary: 'Book updated', group: 'message', life: 3000 })
    } else {
      await api.post(ENDPOINTS.BOOKS, payload)
      toast({ severity: 'success', summary: 'Book created', group: 'message', life: 3000 })
    }
    dialogVisible.value = false
    await loadBooks()
  } catch {
    toast({ severity: 'error', summary: 'Operation failed', group: 'message', life: 3000 })
  }
})

function confirmDelete(book) {
  confirm.require({
    message: `Delete book "${book.title}"?`,
    header: 'Confirm Delete',
    acceptSeverity: 'danger',
    accept: async () => {
      try {
        await api.delete(ENDPOINTS.BOOK_BY_SLUG(book.slug))
        toast({ severity: 'success', summary: 'Book deleted', group: 'message', life: 3000 })
        await loadBooks()
      } catch {
        toast({ severity: 'error', summary: 'Delete failed', group: 'message', life: 3000 })
      }
    },
  })
}
</script>

<template>
  <section class="w-[min(100%,80em)] px-4 py-4">
    <div class="flex justify-between items-center mb-6 px-2">
      <h1 class="text-4xl">Books</h1>
      <Button label="Create book" @click="openCreate" />
    </div>

    <div v-if="isLoading" class="text-neutral-400 text-center py-8">Loading...</div>
    <DataTable v-else :value="books" class="text-sm">
      <Column field="id" header="ID" style="width:60px" />
      <Column field="title" header="Title" />
      <Column field="isbn" header="ISBN" />
      <Column field="publisherName" header="Publisher" />
      <Column header="Authors">
        <template #body="{ data }">{{ data.authorNames?.join(', ') }}</template>
      </Column>
      <Column header="Genres">
        <template #body="{ data }">{{ data.genreNames?.join(', ') }}</template>
      </Column>
      <Column header="Actions" style="width:150px">
        <template #body="{ data }">
          <div class="flex gap-2">
            <Button label="Edit" size="small" severity="secondary" @click="openEdit(data)" />
            <Button label="Delete" size="small" severity="danger" @click="confirmDelete(data)" />
          </div>
        </template>
      </Column>
    </DataTable>

    <Dialog modal header="Book" v-model:visible="dialogVisible" :draggable="false" class="w-[min(100%,560px)]">
      <form class="flex flex-col gap-4 p-2" @submit.prevent="submit">
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
          <label>Publisher <span class="text-red-500">*</span></label>
          <Select v-model="selectedPublisher" :options="publishers" optionLabel="name" placeholder="Select publisher" />
        </div>

        <div class="flex flex-col gap-1">
          <label>ISBN <span class="text-neutral-400 text-xs">(optional)</span></label>
          <InputText v-model="isbn" placeholder="10 or 13 digits" />
        </div>

        <div class="flex flex-col gap-1">
          <label>Release date <span class="text-neutral-400 text-xs">(optional)</span></label>
          <DatePicker v-model="releaseDate" :maxDate="new Date()" dateFormat="MM dd, yy" class="self-start" />
        </div>

        <div class="flex flex-col gap-1">
          <label>Authors <span class="text-neutral-400 text-xs">(optional)</span></label>
          <MultiSelect v-model="selectedAuthors" :options="authors" optionLabel="name" placeholder="Select authors" filter />
        </div>

        <div class="flex flex-col gap-1">
          <label>Genres <span class="text-neutral-400 text-xs">(optional)</span></label>
          <MultiSelect v-model="selectedGenres" :options="genres" optionLabel="name" placeholder="Select genres" filter />
        </div>

        <div class="flex flex-col gap-1">
          <label>Cover URL <span class="text-neutral-400 text-xs">(optional — paste an image URL)</span></label>
          <InputText v-model="coverUrl" placeholder="https://..." />
        </div>

        <div class="flex flex-col gap-1">
          <label>Cover image file <span class="text-neutral-400 text-xs">(optional — overrides URL if provided)</span></label>
          <label class="flex items-center gap-3 cursor-pointer w-fit px-4 py-2 rounded border border-dashed border-slate-400 bg-slate-50 hover:bg-slate-100 transition-colors">
            <span class="text-sm font-medium text-slate-600">{{ coverImageBase64 ? 'Change image' : 'Choose image…' }}</span>
            <input ref="coverFileInput" type="file" accept="image/*" class="hidden" @change="onFileChange" />
          </label>
          <p v-if="coverImageBase64" class="text-xs text-green-600">Image selected</p>
          <p v-else-if="editingBook" class="text-xs text-neutral-400">Leave empty to keep existing cover</p>
        </div>

        <div class="flex gap-3 justify-end">
          <Button label="Cancel" severity="secondary" @click="dialogVisible = false" />
          <Button type="submit" :label="editingBook ? 'Update' : 'Create'" />
        </div>
      </form>
    </Dialog>
  </section>
</template>

<style scoped>
@reference "tailwindcss";
.v-enter-active, .v-leave-active { transition: opacity 0.2s ease; }
.v-enter-from, .v-leave-to { opacity: 0; }
</style>
