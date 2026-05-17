<script setup lang="ts">
import { useThemeStore } from '@/stores/theme'

const theme = useThemeStore()
</script>

<template>
  <button
    class="relative w-11 h-6 rounded-full flex items-center transition-colors cursor-pointer"
    :style="{
      backgroundColor: theme.mode === 'dark' ? 'var(--color-primary)' : 'var(--color-border)',
      padding: '2px',
    }"
    @click="theme.toggle()"
    aria-label="切换主题"
  >
    <span
      class="w-5 h-5 rounded-full bg-white shadow-md transition-transform duration-300 flex items-center justify-center text-[10px]"
      :style="{ transform: theme.mode === 'dark' ? 'translateX(20px)' : 'translateX(0)' }"
    >
      {{ theme.mode === 'dark' ? '🌙' : '☀️' }}
    </span>
  </button>
</template>
