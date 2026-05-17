<script setup lang="ts">
import { useThemeStore } from '@/stores/theme'
import NavBar from './NavBar.vue'
import AppFooter from './AppFooter.vue'
import ScrollProgress from '@/components/ui/ScrollProgress.vue'
import BackToTop from '@/components/ui/BackToTop.vue'

const theme = useThemeStore()
</script>

<template>
  <div :data-theme="theme.mode" class="starfield-bg min-h-screen flex flex-col transition-colors duration-300">
    <div class="relative z-10 flex flex-col flex-1">
      <ScrollProgress />
      <NavBar />
      <main class="flex-1">
        <RouterView />
      </main>
      <AppFooter />
      <BackToTop />
    </div>
  </div>
</template>
