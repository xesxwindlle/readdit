<script setup>
defineProps({
  author: { type: Object, required: true },
})
</script>

<template>
  <RouterLink :to="`/authors/${author.slug}`" class="grid grid-cols-[60px_1fr] gap-3 p-2 hover:bg-slate-100 transition-colors items-center">
    <div class="w-[60px] h-[60px] rounded-full bg-slate-200 flex items-center justify-center text-neutral-500 shrink-0 overflow-hidden">
      <img v-if="author.imageUrl" :src="author.imageUrl" :alt="author.name" class="w-full h-full object-cover" />
      <span v-else class="text-lg font-bold">{{ author.name?.charAt(0) }}</span>
    </div>
    <div class="flex flex-col overflow-hidden">
      <span class="font-bold truncate">{{ author.name }}</span>
      <span v-if="author.dateOfBirth" class="text-sm text-neutral-500">b. {{ author.dateOfBirth }}</span>
    </div>
  </RouterLink>
</template>
