<script setup lang="ts">
import { useRoute } from 'vue-router'
import Logo from '@/components/ui/Logo.vue'
import ThemeToggle from '@/components/ui/ThemeToggle.vue'

const route = useRoute()

const links = [
  { to: '/', label: '首页' },
  { to: '/projects', label: '项目' },
  { to: '/blog', label: '博客' },
  { to: '/tech-stack', label: '技术栈' },
  { to: '/about', label: '关于' },
]
</script>

<template>
  <nav class="sticky top-0 z-50 border-b" style="background: var(--color-bg-elevated); border-color: var(--color-border);">
    <div class="max-w-7xl mx-auto px-6 h-[72px] flex items-center justify-between">
      <Logo />
      <div class="flex items-center gap-8">
        <RouterLink
          v-for="link in links"
          :key="link.to"
          :to="link.to"
          class="text-sm font-medium transition-colors relative py-1"
          :class="route.path === link.to || (link.to !== '/' && route.path.startsWith(link.to)) ? 'text-[var(--color-text-1)]' : 'text-[var(--color-text-2)] hover:text-[var(--color-text-1)]'"
        >
          {{ link.label }}
          <span
            v-if="route.path === link.to || (link.to !== '/' && route.path.startsWith(link.to))"
            class="absolute -bottom-1 left-0 right-0 h-0.5 rounded-full"
            style="background: var(--color-primary);"
          />
        </RouterLink>
      </div>
      <div class="flex items-center gap-4">
        <ThemeToggle />
        <div class="w-10 h-10 rounded-full" style="background: var(--color-primary-soft); border: 1px solid var(--color-border);" />
      </div>
    </div>
  </nav>
</template>
