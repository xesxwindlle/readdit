import Index from '../pages/Index.vue'
import BookDetails from '../pages/BookDetails.vue'

const bookRoutes = [
  { path: '/', component: Index },
  { path: '/books/:slug', component: BookDetails },
]

export default bookRoutes
