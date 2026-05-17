<script setup lang="ts">
import { useThemeStore } from '@/stores/theme'
import { useRoute } from 'vue-router'
import NavBar from './NavBar.vue'
import AppFooter from './AppFooter.vue'
import ScrollProgress from '@/components/ui/ScrollProgress.vue'
import BackToTop from '@/components/ui/BackToTop.vue'

const theme = useThemeStore()
const route = useRoute()
</script>

<template>
  <div :data-theme="theme.mode" class="starfield-bg min-h-screen flex flex-col transition-colors duration-300">
    <div class="relative z-10 flex flex-col flex-1">
      <ScrollProgress />
      <NavBar />
      <main class="flex-1">
        <Transition name="page" mode="out-in">
          <RouterView :key="route.path" />
        </Transition>
      </main>
      <AppFooter />
      <BackToTop />
    </div>
  </div>
</template>

<style scoped>
.page-enter-active,
.page-leave-active {
  transition: opacity 0.2s ease, transform 0.25s ease;
}

.page-enter-from {
  opacity: 0;
  transform: translateY(8px);
}

.page-leave-to {
  opacity: 0;
  transform: translateY(-6px);
}
</style>
