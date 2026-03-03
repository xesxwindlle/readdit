import { createApp } from 'vue'
import { createPinia } from 'pinia'
import PrimeVue from 'primevue/config'
import ToastService from 'primevue/toastservice'
import ConfirmationService from 'primevue/confirmationservice'
import Aura from '@primeuix/themes/aura'
import { definePreset } from '@primeuix/themes'

import App from './App.vue'
import router from './router'

const app = createApp(App)

const auraNoTransition = definePreset(Aura, {
  semantic: { transitionDuration: '0s' },
})

app.use(createPinia())
app.use(router)
app.use(PrimeVue, { theme: { preset: auraNoTransition } })
app.use(ToastService)
app.use(ConfirmationService)

app.mount('#app')
