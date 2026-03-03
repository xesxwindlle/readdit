<script setup>
import api from '../util/api'
import { ENDPOINTS } from '../util/endpoints'
import { Divider, Fieldset } from 'primevue'
import { isAxiosError } from 'axios'
import { onBeforeMount, ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const slug = route.params.slug
const author = ref(null)
const isLoading = ref(true)
const error = ref(null)

onBeforeMount(async () => {
  try {
    const { data: response } = await api.get(ENDPOINTS.AUTHOR_BY_SLUG(slug))
    author.value = response.data
  } catch (e) {
    error.value = isAxiosError(e) && e.response?.status === 404 ? '404 Author not found' : 'Unknown error'
  } finally {
    isLoading.value = false
  }
})
</script>

<template>
  <section class="w-[min(100%,80em)] px-4 py-4">
    <div v-if="isLoading" class="text-neutral-400 py-8 text-center">Loading...</div>
    <div v-else-if="error" class="py-8 text-center">
      <h1 class="text-2xl">{{ error }}</h1>
    </div>
    <div v-else-if="author" class="flex flex-col gap-6 mx-2">
      <div class="flex flex-col md:grid md:grid-cols-[150px_1fr] gap-6 items-start">
        <div class="w-[150px] h-[150px] rounded-full bg-slate-200 flex items-center justify-center overflow-hidden mx-auto md:mx-0">
          <img v-if="author.imageUrl" :src="author.imageUrl" :alt="author.name" class="w-full h-full object-cover" />
          <span v-else class="text-5xl font-bold text-neutral-400">{{ author.name?.charAt(0) }}</span>
        </div>

        <div class="flex flex-col gap-3">
          <h1 class="text-4xl font-black">{{ author.name }}</h1>
          <dl class="grid grid-cols-[150px_1fr] text-sm gap-y-2">
            <dt v-if="author.dateOfBirth" class="font-semibold">Date of birth</dt>
            <dd v-if="author.dateOfBirth">{{ author.dateOfBirth }}</dd>
            <dt v-if="author.dateOfDeath" class="font-semibold">Date of death</dt>
            <dd v-if="author.dateOfDeath">{{ author.dateOfDeath }}</dd>
          </dl>
        </div>
      </div>

      <Divider v-if="author.biography" />
      <Fieldset v-if="author.biography" legend="Biography">
        <p class="whitespace-pre-wrap text-neutral-700 leading-relaxed">{{ author.biography }}</p>
      </Fieldset>
    </div>
  </section>
</template>
