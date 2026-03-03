<script setup>
import api from '../../util/api'
import { ENDPOINTS } from '../../util/endpoints'
import { useSingularToast } from '../../util/useSingularToast'
import { Button, Column, DataTable, DatePicker, Dialog, InputText, Message, Textarea } from 'primevue'
import { useConfirm } from 'primevue/useconfirm'
import { useForm } from 'vee-validate'
import { date, object, string } from 'yup'
import { onBeforeMount, ref } from 'vue'

const toast = useSingularToast()
const confirm = useConfirm()

const authors = ref([])
const isLoading = ref(true)
const dialogVisible = ref(false)
const editingAuthor = ref(null)

onBeforeMount(async () => {
  await loadAuthors()
  isLoading.value = false
})

async function loadAuthors() {
  const { data: response } = await api.get(ENDPOINTS.AUTHORS)
  authors.value = response.data || []
}

const schema = object({
  name: string().required('Name is required'),
  imageUrl: string().url('Must be a valid URL').nullable(),
  dateOfBirth: date().max(new Date(), 'Cannot be in the future').nullable(),
  dateOfDeath: date().max(new Date(), 'Cannot be in the future').nullable(),
  biography: string().nullable(),
})

const { defineField, handleSubmit, errors, resetForm, setValues } = useForm({ validationSchema: schema })
const [name] = defineField('name')
const [imageUrl] = defineField('imageUrl')
const [dateOfBirth] = defineField('dateOfBirth')
const [dateOfDeath] = defineField('dateOfDeath')
const [biography] = defineField('biography')

function openCreate() {
  editingAuthor.value = null
  resetForm()
  dialogVisible.value = true
}

function openEdit(author) {
  editingAuthor.value = author
  setValues({
    name: author.name,
    imageUrl: author.imageUrl || '',
    dateOfBirth: author.dateOfBirth ? new Date(author.dateOfBirth) : null,
    dateOfDeath: author.dateOfDeath ? new Date(author.dateOfDeath) : null,
    biography: author.biography || '',
  })
  dialogVisible.value = true
}

const submit = handleSubmit(async (values) => {
  try {
    const payload = {
      name: values.name,
      imageUrl: values.imageUrl || null,
      dateOfBirth: values.dateOfBirth instanceof Date ? values.dateOfBirth.toISOString().split('T')[0] : (values.dateOfBirth || null),
      dateOfDeath: values.dateOfDeath instanceof Date ? values.dateOfDeath.toISOString().split('T')[0] : (values.dateOfDeath || null),
      biography: values.biography || null,
    }
    if (editingAuthor.value) {
      await api.patch(ENDPOINTS.AUTHOR_BY_SLUG(editingAuthor.value.slug), payload)
      toast({ severity: 'success', summary: 'Author updated', group: 'message', life: 3000 })
    } else {
      await api.post(ENDPOINTS.AUTHORS, payload)
      toast({ severity: 'success', summary: 'Author created', group: 'message', life: 3000 })
    }
    dialogVisible.value = false
    await loadAuthors()
  } catch {
    toast({ severity: 'error', summary: 'Operation failed', group: 'message', life: 3000 })
  }
})

function confirmDelete(author) {
  confirm.require({
    message: `Delete author "${author.name}"?`,
    header: 'Confirm Delete',
    acceptSeverity: 'danger',
    accept: async () => {
      try {
        await api.delete(ENDPOINTS.AUTHOR_BY_SLUG(author.slug))
        toast({ severity: 'success', summary: 'Author deleted', group: 'message', life: 3000 })
        await loadAuthors()
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
      <h1 class="text-4xl">Authors</h1>
      <Button label="Create author" @click="openCreate" />
    </div>

    <div v-if="isLoading" class="text-neutral-400 text-center py-8">Loading...</div>
    <DataTable v-else :value="authors" class="text-sm">
      <Column field="id" header="ID" style="width:60px" />
      <Column field="name" header="Name" />
      <Column field="slug" header="Slug" />
      <Column field="dateOfBirth" header="Born" style="width:110px" />
      <Column field="dateOfDeath" header="Died" style="width:110px" />
      <Column header="Actions" style="width:150px">
        <template #body="{ data }">
          <div class="flex gap-2">
            <Button label="Edit" size="small" severity="secondary" @click="openEdit(data)" />
            <Button label="Delete" size="small" severity="danger" @click="confirmDelete(data)" />
          </div>
        </template>
      </Column>
    </DataTable>

    <Dialog modal header="Author" v-model:visible="dialogVisible" :draggable="false" class="w-[min(100%,500px)]">
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

        <div class="flex flex-col gap-1">
          <label>Image URL <span class="text-neutral-400 text-xs">(optional)</span></label>
          <InputText v-model="imageUrl" :invalid="!!errors.imageUrl" placeholder="https://..." />
          <Transition>
            <div v-if="errors.imageUrl">
              <Message severity="error" variant="simple" size="small">{{ errors.imageUrl }}</Message>
            </div>
          </Transition>
        </div>

        <div class="flex gap-4">
          <div class="flex flex-col gap-1 flex-1">
            <label>Date of birth <span class="text-neutral-400 text-xs">(optional)</span></label>
            <DatePicker v-model="dateOfBirth" :maxDate="new Date()" dateFormat="MM dd, yy" />
          </div>
          <div class="flex flex-col gap-1 flex-1">
            <label>Date of death <span class="text-neutral-400 text-xs">(optional)</span></label>
            <DatePicker v-model="dateOfDeath" :maxDate="new Date()" dateFormat="MM dd, yy" />
          </div>
        </div>

        <div class="flex flex-col gap-1">
          <label>Biography <span class="text-neutral-400 text-xs">(optional)</span></label>
          <Textarea v-model="biography" rows="4" autoResize />
        </div>

        <div class="flex gap-3 justify-end">
          <Button label="Cancel" severity="secondary" @click="dialogVisible = false" />
          <Button type="submit" :label="editingAuthor ? 'Update' : 'Create'" />
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
