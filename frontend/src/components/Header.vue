<script setup>
import defaultCover from '../assets/default_cover.svg'
import api from '../util/api'
import { ENDPOINTS } from '../util/endpoints'
import { roles } from '../util/enums'
import { useSingularToast } from '../util/useSingularToast'
import { useUserStore } from '../stores/userStore'
import { Icon } from '@iconify/vue'
import { AutoComplete, Menu, Menubar } from 'primevue'
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const toast = useSingularToast()
const userStore = useUserStore()
const menu = ref()

const menuItems = computed(() => [
  {
    label: `Hello, ${userStore.displayName}`,
    items: [
      { label: 'Log out', command: logout, class: 'text-red-400' },
    ],
  },
])

const navbarItems = computed(() => {
  const items = [
    { label: 'Home', route: '/' },
    { label: 'Authors', route: '/authors' },
  ]
  if (userStore.isLoggedIn) {
    items.push({ label: 'Submissions', route: '/submissions' })
  }
  if (userStore.role >= roles.admin) {
    items.push({
      label: 'Admin',
      items: [
        { label: 'Books', route: '/admin/books' },
        { label: 'Authors', route: '/admin/authors' },
        { label: 'Genres', route: '/admin/genres' },
        { label: 'Publishers', route: '/admin/publishers' },
        { label: 'Users', route: '/admin/users' },
      ],
    })
  }
  return items
})

function toggle(event) {
  menu.value.toggle(event)
}

function logout() {
  userStore.clearUser()
  router.push('/')
  toast({ severity: 'success', summary: 'Logged out', group: 'message', life: 3000 })
}

const searchInput = ref('')
const bookSearchResult = ref([])
const showMobileSearch = ref(false)

async function searchBooks(e) {
  try {
    const { data: response } = await api.get(ENDPOINTS.BOOKS)
    const query = e.query.toLowerCase()
    bookSearchResult.value = (response.data || []).filter((b) =>
      b.title?.toLowerCase().includes(query),
    )
  } catch {}
}

function navigateToBook(e) {
  router.push(`/books/${e.value.slug}`)
  searchInput.value = ''
}
</script>

<template>
  <header class="sticky top-0 bg-slate-50 shadow z-1">
    <Menubar
      :model="navbarItems"
      :dt="{ root: { background: 'none', transitionDuration: '0s' } }"
      :pt="{
        root: 'h-12 !max-w-[80em] mx-auto !border-0',
        rootList: '!p-0 !gap-0 !rounded-none',
        button: '!h-12 !w-12 !rounded-none !px-4 hover:!bg-slate-200',
        item: 'bg-slate-50',
        itemContent: '!rounded-none hover:!bg-slate-200',
        end: '!h-12 flex items-center',
      }"
    >
      <template #item="{ item }">
        <RouterLink
          v-if="item.route"
          :to="item.route"
          class="flex items-center h-12 px-4 py-2"
        >
          <span>{{ item.label }}</span>
        </RouterLink>
        <a v-else class="flex items-center h-12 px-4 py-2">
          <span>{{ item.label }}</span>
        </a>
      </template>
      <template #end>
        <div class="flex items-center">
          <div class="hidden lg:flex items-center">
            <AutoComplete
              v-model="searchInput"
              :delay="400"
              :suggestions="bookSearchResult"
              optionLabel="title"
              placeholder="Search books"
              @complete="searchBooks"
              @option-select="navigateToBook"
              :inputStyle="{ width: '260px', height: '36px' }"
              :panelStyle="{ width: '260px' }"
            >
              <template #option="{ option }">
                <div class="flex gap-3 items-center w-full">
                  <img
                    :src="option.coverUrl ?? defaultCover"
                    class="w-10 h-12 object-cover shrink-0"
                  />
                  <span class="truncate font-bold">{{ option.title }}</span>
                </div>
              </template>
            </AutoComplete>
          </div>

          <button
            @click="showMobileSearch = !showMobileSearch"
            class="flex items-center h-full px-4 lg:hidden"
          >
            <Icon icon="material-symbols:search" width="24" height="24" />
          </button>

          <div class="hover:bg-slate-200">
            <RouterLink
              v-if="!userStore.isLoggedIn"
              to="/login"
              class="flex items-center h-full px-4 h-12"
            >
              <span>Log in</span>
            </RouterLink>
            <div v-else @click="toggle" class="flex items-center h-full">
              <button class="flex items-center h-12 px-4">
                {{ userStore.displayName }}
              </button>
              <Menu ref="menu" :model="menuItems" :popup="true">
                <template #item="{ item }">
                  <button
                    v-if="item.command"
                    @click="item.command"
                    class="flex items-center px-4 py-2 w-full"
                  >
                    <span :class="item.class">{{ item.label }}</span>
                  </button>
                  <RouterLink
                    v-else-if="item.route"
                    :to="item.route"
                    class="flex items-center px-4 py-2"
                  >
                    <span :class="item.class">{{ item.label }}</span>
                  </RouterLink>
                </template>
              </Menu>
            </div>
          </div>
        </div>
      </template>
    </Menubar>

    <div v-if="showMobileSearch" class="bg-slate-50 border-t border-slate-200 lg:hidden">
      <div class="max-w-[80em] mx-auto py-2 flex justify-center">
        <AutoComplete
          v-model="searchInput"
          :delay="400"
          :suggestions="bookSearchResult"
          optionLabel="title"
          placeholder="Search books"
          @complete="searchBooks"
          @option-select="navigateToBook"
          :inputStyle="{ width: '300px', height: '36px' }"
        >
          <template #option="{ option }">
            <div class="flex gap-3 items-center w-full">
              <img :src="option.coverUrl ?? defaultCover" class="w-10 h-12 object-cover shrink-0" />
              <span class="truncate font-bold">{{ option.title }}</span>
            </div>
          </template>
        </AutoComplete>
      </div>
    </div>
  </header>
</template>

<style scoped>
@reference "tailwindcss";

:deep(.p-menubar-mobile-active > .p-menubar-root-list) {
  @apply shadow;
}

:deep(.p-menubar-mobile) {
  @apply !p-0;
}
</style>
