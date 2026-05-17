import { createRouter, createWebHistory } from 'vue-router'
import { gsap } from 'gsap'
import { ScrollTrigger } from 'gsap/ScrollTrigger'
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

// 路由切换前全局清理所有 GSAP 动画与 ScrollTrigger，防止旧页面动画残留干扰新页面
router.beforeEach(() => {
  gsap.killTweensOf('*')
  ScrollTrigger.getAll().forEach((st) => st.kill())
})

// 路由切换完成后刷新 ScrollTrigger 位置计算
router.afterEach(() => {
  // 等待 DOM 更新后再刷新
  requestAnimationFrame(() => {
    ScrollTrigger.refresh()
  })
})

export default router
