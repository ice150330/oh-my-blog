<script setup lang="ts">
import { useThemeStore } from '@/stores/theme'
import NavBar from './NavBar.vue'
import AppFooter from './AppFooter.vue'

const theme = useThemeStore()
</script>

<template>
  <div :data-theme="theme.mode" class="min-h-screen flex flex-col transition-colors duration-300" style="background: var(--color-bg);">
    <NavBar />
    <main class="flex-1">
      <RouterView />
    </main>
    <AppFooter />
  </div>
</template>
