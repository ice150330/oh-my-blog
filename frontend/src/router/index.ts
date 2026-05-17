import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home/index.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'Home', component: Home },
    { path: '/projects', name: 'Projects', component: () => import('@/views/Projects/index.vue') },
    { path: '/projects/:slug', name: 'ProjectDetail', component: () => import('@/views/ProjectDetail/index.vue') },
    { path: '/blog', name: 'Blog', component: () => import('@/views/Blog/index.vue') },
    { path: '/blog/:slug', name: 'BlogDetail', component: () => import('@/views/BlogDetail/index.vue') },
    { path: '/tech-stack', name: 'TechStack', component: () => import('@/views/TechStack/index.vue') },
    { path: '/about', name: 'About', component: () => import('@/views/About/index.vue') },
  ],
  scrollBehavior() {
    return { top: 0 }
  },
})

export default router
