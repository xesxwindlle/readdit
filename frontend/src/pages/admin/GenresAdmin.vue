<script setup>
import api from '../../util/api'
import { ENDPOINTS } from '../../util/endpoints'
import { useSingularToast } from '../../util/useSingularToast'
import { Button, Column, DataTable, Dialog, InputText, Message } from 'primevue'
import { useConfirm } from 'primevue/useconfirm'
import { useForm } from 'vee-validate'
import { object, string } from 'yup'
import { onBeforeMount, ref } from 'vue'

const toast = useSingularToast()
const confirm = useConfirm()

const genres = ref([])
const isLoading = ref(true)
const dialogVisible = ref(false)
const editingGenre = ref(null)

onBeforeMount(async () => {
  await loadGenres()
  isLoading.value = false
})

async function loadGenres() {
  const { data: response } = await api.get(ENDPOINTS.GENRES)
  genres.value = response.data || []
}

const schema = object({ name: string().required('Name is required') })
const { defineField, handleSubmit, errors, resetForm, setValues } = useForm({ validationSchema: schema })
const [name] = defineField('name')

function openCreate() {
  editingGenre.value = null
  resetForm()
  dialogVisible.value = true
}

function openEdit(genre) {
  editingGenre.value = genre
  setValues({ name: genre.name })
  dialogVisible.value = true
}

const submit = handleSubmit(async (values) => {
  try {
    if (editingGenre.value) {
      await api.put(ENDPOINTS.GENRE_BY_ID(editingGenre.value.id), values)
      toast({ severity: 'success', summary: 'Genre updated', group: 'message', life: 3000 })
    } else {
      await api.post(ENDPOINTS.GENRES, values)
      toast({ severity: 'success', summary: 'Genre created', group: 'message', life: 3000 })
    }
    dialogVisible.value = false
    await loadGenres()
  } catch {
    toast({ severity: 'error', summary: 'Operation failed', group: 'message', life: 3000 })
  }
})

function confirmDelete(genre) {
  confirm.require({
    message: `Delete genre "${genre.name}"?`,
    header: 'Confirm Delete',
    acceptSeverity: 'danger',
    accept: async () => {
      try {
        await api.delete(ENDPOINTS.GENRE_BY_ID(genre.id))
        toast({ severity: 'success', summary: 'Genre deleted', group: 'message', life: 3000 })
        await loadGenres()
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
      <h1 class="text-4xl">Genres</h1>
      <Button label="Create genre" @click="openCreate" />
    </div>

    <div v-if="isLoading" class="text-neutral-400 text-center py-8">Loading...</div>
    <DataTable v-else :value="genres" class="text-sm">
      <Column field="id" header="ID" style="width:60px" />
      <Column field="name" header="Name" />
      <Column field="slug" header="Slug" />
      <Column header="Actions" style="width:150px">
        <template #body="{ data }">
          <div class="flex gap-2">
            <Button label="Edit" size="small" severity="secondary" @click="openEdit(data)" />
            <Button label="Delete" size="small" severity="danger" @click="confirmDelete(data)" />
          </div>
        </template>
      </Column>
    </DataTable>

    <Dialog modal header="Genre" v-model:visible="dialogVisible" :draggable="false" class="w-[min(100%,400px)]">
      <form class="flex flex-col gap-4 p-2" @submit.prevent="submit">
        <div class="flex flex-col gap-1">
          <label>Name</label>
          <InputText v-model="name" :invalid="!!errors.name" />
          <Transition>
            <div v-if="errors.name">
              <Message severity="error" variant="simple" size="small">{{ errors.name }}</Message>
            </div>
          </Transition>
        </div>
        <div class="flex gap-3 justify-end">
          <Button label="Cancel" severity="secondary" @click="dialogVisible = false" />
          <Button type="submit" :label="editingGenre ? 'Update' : 'Create'" />
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
