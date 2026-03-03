<script setup>
import { onBeforeMount, ref } from 'vue'
import { ConfirmDialog } from 'primevue'
import './App.css'
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import SingularToast from './components/SingularToast.vue'
import { useUserStore } from './stores/userStore'
import { useGenreStore } from './stores/genreStore'

const userStore = useUserStore()
const genreStore = useGenreStore()
const isVerifying = ref(true)

onBeforeMount(async () => {
  try {
    await userStore.verify()
    await genreStore.fetchGenres()
  } catch (error) {
    console.error(error)
  } finally {
    isVerifying.value = false
  }
})
</script>

<template>
  <div v-if="!isVerifying">
    <Header />
    <main class="flex justify-center mt-4 mb-auto mx-auto w-[min(100%,80em)] min-h-screen">
      <RouterView />
    </main>
    <Footer />
    <div id="overlay">
      <SingularToast />
      <ConfirmDialog />
    </div>
  </div>
</template>
