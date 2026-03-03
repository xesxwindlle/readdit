<template>
  <section class="w-[min(100%,80em)] px-4 py-4">
    <div class="px-2 py-4">
      <h1 class="text-4xl">Submissions</h1>
      <p class="text-neutral-500">Help grow our catalog by submitting new books and authors.</p>
    </div>
    <div class="flex flex-col md:grid md:grid-cols-2 gap-6 mt-2">
      <RouterLink
        to="/submissions/books"
        class="border border-slate-200 rounded-md p-6 hover:bg-slate-100 transition-colors block"
      >
        <h2 class="text-xl font-bold mb-2">Book Submissions</h2>
        <p class="text-neutral-500 text-sm">Browse all book submissions, submit a new book, or review pending ones.</p>
      </RouterLink>
      <RouterLink
        to="/submissions/authors"
        class="border border-slate-200 rounded-md p-6 hover:bg-slate-100 transition-colors block"
      >
        <h2 class="text-xl font-bold mb-2">Author Submissions</h2>
        <p class="text-neutral-500 text-sm">Browse all author submissions, submit a new author, or review pending ones.</p>
      </RouterLink>
    </div>
  </section>
</template>
