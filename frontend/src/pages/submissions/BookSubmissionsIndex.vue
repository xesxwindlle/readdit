<script setup>
import api from '../../util/api'
import { ENDPOINTS } from '../../util/endpoints'
import { roles } from '../../util/enums'
import { useUserStore } from '../../stores/userStore'
import { Button, Column, DataTable, Tab, TabList, TabPanel, TabPanels, Tabs } from 'primevue'
import { onBeforeMount, ref } from 'vue'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

const isAdmin = userStore.role >= roles.admin

// Admin: all submissions by status
const allSubmissions = ref([])
const pendingSubmissions = ref([])
const approvedSubmissions = ref([])
const rejectedSubmissions = ref([])

// Regular user: own submissions only
const mySubmissions = ref([])

const isLoading = ref(true)

onBeforeMount(async () => {
  try {
    if (isAdmin) {
      const [allRes, pendingRes, approvedRes, rejectedRes] = await Promise.all([
        api.get(ENDPOINTS.BOOK_SUBMISSIONS),
        api.get(ENDPOINTS.BOOK_SUBMISSION_BY_STATUS('pending')),
        api.get(ENDPOINTS.BOOK_SUBMISSION_BY_STATUS('approved')),
        api.get(ENDPOINTS.BOOK_SUBMISSION_BY_STATUS('rejected')),
      ])
      allSubmissions.value = allRes.data.data || []
      pendingSubmissions.value = pendingRes.data.data || []
      approvedSubmissions.value = approvedRes.data.data || []
      rejectedSubmissions.value = rejectedRes.data.data || []
    } else {
      const { data: res } = await api.get(ENDPOINTS.BOOK_SUBMISSION_BY_SUBMITTER(userStore.userId))
      mySubmissions.value = res.data || []
    }
  } catch {}
  finally { isLoading.value = false }
})

function handleRowClick(e) {
  router.push(`/submissions/books/${e.data.id}`)
}
</script>

<template>
  <section class="w-[min(100%,80em)] px-4 py-4">
    <div class="flex justify-between items-center mb-6 px-2">
      <h1 class="text-4xl">Book Submissions</h1>
      <RouterLink to="/submissions/books/new">
        <Button label="Submit a book" />
      </RouterLink>
    </div>

    <div v-if="isLoading" class="text-neutral-400 text-center py-8">Loading...</div>

    <!-- Admin: full view with all tabs -->
    <template v-else-if="isAdmin">
      <Tabs value="all">
        <TabList>
          <Tab value="all">All ({{ allSubmissions.length }})</Tab>
          <Tab value="pending">Pending ({{ pendingSubmissions.length }})</Tab>
          <Tab value="approved">Approved ({{ approvedSubmissions.length }})</Tab>
          <Tab value="rejected">Rejected ({{ rejectedSubmissions.length }})</Tab>
        </TabList>
        <TabPanels>
          <TabPanel v-for="(list, key) in { all: allSubmissions, pending: pendingSubmissions, approved: approvedSubmissions, rejected: rejectedSubmissions }" :key="key" :value="key">
            <DataTable :value="list" @rowClick="handleRowClick" rowHover class="cursor-pointer text-sm">
              <Column field="id" header="ID" style="width:60px" />
              <Column field="title" header="Title" />
              <Column field="publisherName" header="Publisher" />
              <Column field="submitterDisplayName" header="Submitter" />
              <Column field="reviewStatus" header="Status">
                <template #body="{ data }">
                  <span
                    class="text-xs px-2 py-0.5 rounded font-semibold"
                    :class="{
                      'bg-yellow-100 text-yellow-700': data.reviewStatus === 'pending',
                      'bg-green-100 text-green-700': data.reviewStatus === 'approved',
                      'bg-red-100 text-red-700': data.reviewStatus === 'rejected',
                    }"
                  >{{ data.reviewStatus }}</span>
                </template>
              </Column>
              <Column field="createdAt" header="Submitted">
                <template #body="{ data }">{{ data.createdAt?.slice(0,10) }}</template>
              </Column>
            </DataTable>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </template>

    <!-- Regular user: personal submissions -->
    <template v-else>
      <div v-if="mySubmissions.length === 0" class="text-neutral-400 px-2 py-4">You haven't submitted any books yet.</div>
      <DataTable v-else :value="mySubmissions" @rowClick="handleRowClick" rowHover class="cursor-pointer text-sm">
        <Column field="id" header="ID" style="width:60px" />
        <Column field="title" header="Title" />
        <Column field="publisherName" header="Publisher" />
        <Column field="reviewStatus" header="Status">
          <template #body="{ data }">
            <span
              class="text-xs px-2 py-0.5 rounded font-semibold"
              :class="{
                'bg-yellow-100 text-yellow-700': data.reviewStatus === 'pending',
                'bg-green-100 text-green-700': data.reviewStatus === 'approved',
                'bg-red-100 text-red-700': data.reviewStatus === 'rejected',
              }"
            >{{ data.reviewStatus }}</span>
          </template>
        </Column>
        <Column field="createdAt" header="Submitted">
          <template #body="{ data }">{{ data.createdAt?.slice(0,10) }}</template>
        </Column>
      </DataTable>
    </template>
  </section>
</template>
