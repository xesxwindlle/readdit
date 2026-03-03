<script setup>
import { computed } from 'vue'
import defaultCover from '../assets/default_cover.svg'

const props = defineProps({
  book: { type: Object, required: true },
})

const imageSrc = computed(() => {
  if (props.book.coverUrl) return props.book.coverUrl
  if (props.book.coverImage) return `data:image/jpeg;base64,${props.book.coverImage}`
  return defaultCover
})
</script>

<template>
  <RouterLink :to="`/books/${book.slug}`" class="grid grid-cols-[80px_1fr] gap-3 p-2 hover:bg-slate-100 transition-colors">
    <img
      :src="imageSrc"
      :alt="book.title"
      class="w-[80px] h-[110px] object-cover"
    />
    <div class="flex flex-col justify-center overflow-hidden gap-1">
      <span class="font-bold truncate text-sm">{{ book.title }}</span>
      <span v-if="book.authorNames?.length" class="text-xs text-neutral-500 truncate">
        {{ book.authorNames.join(', ') }}
      </span>
      <span v-if="book.genreNames?.length" class="text-xs text-neutral-400 truncate">
        {{ book.genreNames.join(', ') }}
      </span>
    </div>
  </RouterLink>
</template>
