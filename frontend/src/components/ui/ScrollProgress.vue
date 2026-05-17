<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

const progress = ref(0)

function updateProgress() {
  const scrollTop = window.scrollY
  const docHeight = document.documentElement.scrollHeight - window.innerHeight
  progress.value = docHeight > 0 ? (scrollTop / docHeight) * 100 : 0
}

onMounted(() => {
  window.addEventListener('scroll', updateProgress, { passive: true })
  updateProgress()
})

onUnmounted(() => {
  window.removeEventListener('scroll', updateProgress)
})
</script>

<template>
  <div
    class="fixed top-0 left-0 right-0 z-[60] h-[3px]"
    style="background: transparent;"
  >
    <div
      class="h-full transition-all"
      style="background: var(--color-primary); box-shadow: 0 0 8px var(--glow-primary);"
      :style="{ width: `${progress}%` }"
    />
  </div>
</template>
