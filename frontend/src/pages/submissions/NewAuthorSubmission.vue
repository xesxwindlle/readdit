<script setup>
import api from '../../util/api'
import { ENDPOINTS } from '../../util/endpoints'
import { useSingularToast } from '../../util/useSingularToast'
import { useUserStore } from '../../stores/userStore'
import { useForm } from 'vee-validate'
import { object, string, date } from 'yup'
import { Button, DatePicker, Fieldset, InputText, Message, Textarea } from 'primevue'
import { isAxiosError } from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const toast = useSingularToast()
const userStore = useUserStore()

const schema = object({
  name: string().required('Name is required'),
  dateOfBirth: date().max(new Date(), 'Cannot be in the future').nullable(),
  dateOfDeath: date().nullable(),
  authorImageUrl: string().url('Must be a valid URL').nullable(),
  biography: string(),
})

const { defineField, handleSubmit, errors } = useForm({ validationSchema: schema })
const [name] = defineField('name')
const [dateOfBirth] = defineField('dateOfBirth')
const [dateOfDeath] = defineField('dateOfDeath')
const [authorImageUrl] = defineField('authorImageUrl')
const [biography] = defineField('biography')
const [submitterComment] = defineField('submitterComment')

const submit = handleSubmit(async (values) => {
  try {
    const payload = {
      submitterId: userStore.userId,
      submitterComment: values.submitterComment || null,
      authorName: values.name,
      dateOfBirth: values.dateOfBirth ? values.dateOfBirth.toISOString().split('T')[0] : null,
      dateOfDeath: values.dateOfDeath ? values.dateOfDeath.toISOString().split('T')[0] : null,
      authorImageUrl: values.authorImageUrl || null,
      biography: values.biography || null,
    }
    const { data: response } = await api.post(ENDPOINTS.AUTHOR_SUBMISSIONS, payload)
    toast({ severity: 'success', summary: 'Submission created!', group: 'message', life: 3000 })
    router.push(`/submissions/authors/${response.data.id}`)
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
      <h1 class="text-4xl">Submit a new author</h1>
    </div>
    <Fieldset legend="Before you submit" class="mb-6 max-w-3xl">
      <p class="text-sm text-neutral-500">Your submission will be reviewed by a moderator before it appears on the site.</p>
    </Fieldset>

    <form class="flex flex-col gap-5 max-w-3xl" @submit.prevent="submit">
      <div class="flex flex-col gap-1">
        <label>Name</label>
        <InputText v-model="name" :invalid="!!errors.name" />
        <Transition>
          <div v-if="errors.name">
            <Message severity="error" variant="simple" size="small">{{ errors.name }}</Message>
          </div>
        </Transition>
      </div>

      <div class="flex gap-4 flex-wrap">
        <div class="flex flex-col gap-1 grow">
          <label>Date of birth <span class="text-neutral-400 text-xs">(optional)</span></label>
          <DatePicker v-model="dateOfBirth" :maxDate="new Date()" dateFormat="MM dd, yy" />
          <Transition>
            <div v-if="errors.dateOfBirth">
              <Message severity="error" variant="simple" size="small">{{ errors.dateOfBirth }}</Message>
            </div>
          </Transition>
        </div>
        <div class="flex flex-col gap-1 grow">
          <label>Date of death <span class="text-neutral-400 text-xs">(optional)</span></label>
          <DatePicker v-model="dateOfDeath" dateFormat="MM dd, yy" />
        </div>
      </div>

      <div class="flex flex-col gap-1">
        <label>Image URL <span class="text-neutral-400 text-xs">(optional)</span></label>
        <InputText v-model="authorImageUrl" :invalid="!!errors.authorImageUrl" placeholder="https://..." />
        <Transition>
          <div v-if="errors.authorImageUrl">
            <Message severity="error" variant="simple" size="small">{{ errors.authorImageUrl }}</Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1">
        <label>Biography <span class="text-neutral-400 text-xs">(optional)</span></label>
        <Textarea v-model="biography" rows="5" autoResize />
      </div>

      <div class="flex flex-col gap-1">
        <label>Comment for reviewer <span class="text-neutral-400 text-xs">(optional)</span></label>
        <Textarea v-model="submitterComment" rows="3" placeholder="Any notes for the moderator..." autoResize />
      </div>

      <div class="flex gap-3 mt-2">
        <Button type="submit" label="Submit" />
        <RouterLink to="/submissions/authors">
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
