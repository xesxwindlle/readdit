<script setup>
import api from '../util/api'
import { ENDPOINTS } from '../util/endpoints'
import { useSingularToast } from '../util/useSingularToast'
import { useUserStore } from '../stores/userStore'
import { useForm } from 'vee-validate'
import { object, string } from 'yup'
import { InputText, Message } from 'primevue'
import { isAxiosError } from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const userStore = useUserStore()
const toast = useSingularToast()

const schema = object({
  email: string().required('Email is required').email('Must be a valid email'),
  password: string().required('Password is required'),
})

const { defineField, handleSubmit, errors } = useForm({ validationSchema: schema })
const [email] = defineField('email')
const [password] = defineField('password')

const submit = handleSubmit(async (values) => {
  try {
    const { data: response } = await api.post(ENDPOINTS.LOGIN, values)
    localStorage.setItem('token', response.data.token)
    userStore.setUser(response.data)
    toast({ severity: 'success', summary: 'Welcome back!', group: 'message', life: 3000 })
    router.push('/')
  } catch (error) {
    const msg =
      isAxiosError(error) && error.response?.status === 401
        ? 'Invalid email or password'
        : 'An error occurred. Please try again.'
    toast({ severity: 'error', summary: msg, group: 'message', life: 4000 })
  }
}, () => {
  toast({ severity: 'error', summary: 'Please fix the errors in the form.', group: 'message', life: 3000 })
})
</script>

<template>
  <section class="flex flex-col gap-6 p-6 w-[min(100%,500px)] h-fit rounded-md bg-slate-100">
    <h1 class="text-center font-bold text-2xl">Log in to Readdit</h1>
    <form id="login" class="flex flex-col p-2 w-full" @submit.prevent="submit">
      <label for="email">Email</label>
      <InputText id="email" v-model="email" type="email" :invalid="!!errors.email" />
      <Transition>
        <div v-if="errors.email">
          <Message severity="error" variant="simple" size="small">{{ errors.email }}</Message>
        </div>
      </Transition>

      <label for="password">Password</label>
      <InputText id="password" v-model="password" type="password" :invalid="!!errors.password" />
      <Transition>
        <div v-if="errors.password">
          <Message severity="error" variant="simple" size="small">{{ errors.password }}</Message>
        </div>
      </Transition>
    </form>
    <button form="login" type="submit" class="py-2 bg-blue-200 hover:bg-blue-300 border border-slate-100 rounded-sm">
      Log in
    </button>
    <p class="text-center text-sm">
      Don't have an account yet?
      <RouterLink to="/register" class="text-blue-600 hover:underline">Register here</RouterLink>
    </p>
  </section>
</template>

<style scoped>
@reference "tailwindcss";

label:not(:first-child) {
  @apply mt-4;
}

.v-enter-active, .v-leave-active {
  transition: opacity 0.2s ease;
}
.v-enter-from, .v-leave-to {
  opacity: 0;
}
</style>
