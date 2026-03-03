<script setup>
import api from '../../util/api'
import { ENDPOINTS } from '../../util/endpoints'
import { useSingularToast } from '../../util/useSingularToast'
import { Button, Column, DataTable, Select } from 'primevue'
import { useConfirm } from 'primevue/useconfirm'
import { onBeforeMount, ref } from 'vue'

const toast = useSingularToast()
const confirm = useConfirm()

const users = ref([])
const isLoading = ref(true)
const roleOptions = ['USER', 'MODERATOR', 'ADMIN']

onBeforeMount(async () => {
  await loadUsers()
  isLoading.value = false
})

async function loadUsers() {
  const { data: response } = await api.get(ENDPOINTS.USERS)
  users.value = response.data || []
}

async function changeRole(user, newRole) {
  if (!newRole || newRole === user.role) return
  try {
    await api.patch(ENDPOINTS.USER_ROLE(user.id, newRole))
    toast({ severity: 'success', summary: `Role updated to ${newRole}`, group: 'message', life: 3000 })
    await loadUsers()
  } catch {
    toast({ severity: 'error', summary: 'Failed to update role', group: 'message', life: 3000 })
    await loadUsers()
  }
}

function confirmDelete(user) {
  confirm.require({
    message: `Delete user "${user.displayName}" (${user.email})?`,
    header: 'Confirm Delete',
    acceptSeverity: 'danger',
    accept: async () => {
      try {
        await api.delete(ENDPOINTS.USER_BY_ID(user.id))
        toast({ severity: 'success', summary: 'User deleted', group: 'message', life: 3000 })
        await loadUsers()
      } catch {
        toast({ severity: 'error', summary: 'Delete failed', group: 'message', life: 3000 })
      }
    },
  })
}
</script>

<template>
  <section class="w-[min(100%,80em)] px-4 py-4">
    <div class="flex justify-between items-center mb-6 px-2">
      <h1 class="text-4xl">Users</h1>
    </div>

    <div v-if="isLoading" class="text-neutral-400 text-center py-8">Loading...</div>
    <DataTable v-else :value="users" class="text-sm">
      <Column field="id" header="ID" style="width:60px" />
      <Column field="displayName" header="Display Name" />
      <Column field="email" header="Email" />
      <Column field="createdAt" header="Joined" style="width:120px">
        <template #body="{ data }">{{ data.createdAt?.slice(0, 10) }}</template>
      </Column>
      <Column header="Role" style="width:160px">
        <template #body="{ data }">
          <Select
            :modelValue="data.role"
            :options="roleOptions"
            class="w-full text-xs"
            @update:modelValue="(val) => changeRole(data, val)"
          />
        </template>
      </Column>
      <Column header="Actions" style="width:100px">
        <template #body="{ data }">
          <Button label="Delete" size="small" severity="danger" @click="confirmDelete(data)" />
        </template>
      </Column>
    </DataTable>
  </section>
</template>
