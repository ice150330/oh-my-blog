<script setup lang="ts">
import { RouterLink } from 'vue-router'

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
      <RouterLink to="/" class="flex items-center gap-2 text-lg font-bold" style="color: var(--color-text-1);">
        <span class="w-6 h-6 rounded-sm" style="background: var(--color-primary);"></span>
        oh-my-blog
      </RouterLink>
      <div class="flex items-center gap-8">
        <RouterLink
          v-for="link in links"
          :key="link.to"
          :to="link.to"
          class="text-sm font-medium transition-colors"
          style="color: var(--color-text-2);"
          active-class="!text-[var(--color-text-1)]"
        >
          {{ link.label }}
        </RouterLink>
      </div>
    </div>
  </nav>
</template>
