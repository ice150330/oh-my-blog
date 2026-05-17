<script setup lang="ts">
import { useThemeStore } from '@/stores/theme'

const theme = useThemeStore()
</script>

<template>
  <button
    class="relative w-11 h-6 rounded-full flex items-center cursor-pointer"
    :style="{
      backgroundColor: theme.mode === 'dark' ? 'var(--color-primary)' : 'var(--color-border)',
      padding: '2px',
    }"
    @click="theme.toggle()"
    aria-label="切换主题"
  >
    <span
      class="w-5 h-5 rounded-full bg-white shadow-md transition-transform duration-300 flex items-center justify-center"
      :style="{ transform: theme.mode === 'dark' ? 'translateX(20px)' : 'translateX(0)' }"
    >
      <svg
        v-if="theme.mode === 'dark'"
        width="12"
        height="12"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2.5"
        stroke-linecap="round"
        stroke-linejoin="round"
        class="text-slate-700"
      >
        <use href="/icons/sprite.svg#moon" />
      </svg>
      <svg
        v-else
        width="12"
        height="12"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2.5"
        stroke-linecap="round"
        stroke-linejoin="round"
        class="text-amber-500"
      >
        <use href="/icons/sprite.svg#sun" />
      </svg>
    </span>
  </button>
</template>
