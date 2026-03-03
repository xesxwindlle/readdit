<script setup>
import { Rating } from 'primevue'

defineProps({
  review: { type: Object, required: true },
})
</script>

<template>
  <div class="flex flex-col md:grid md:grid-cols-[130px_1fr] gap-3 p-2">
    <div class="flex flex-col items-start gap-1">
      <span class="font-semibold text-sm">{{ review.userDisplayName }}</span>
      <Rating :modelValue="review.rating" :readonly="true" :cancel="false" />
      <span class="text-xs text-neutral-400">{{ review.createdAt?.slice(0, 10) }}</span>
    </div>
    <div class="text-sm text-neutral-700 leading-relaxed">
      {{ review.content }}
    </div>
  </div>
</template>
