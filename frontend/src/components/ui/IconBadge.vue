<script setup lang="ts">
defineProps<{
  icon: string
}>()
</script>

<template>
  <div
    class="w-10 h-10 rounded-full flex items-center justify-center"
    style="background: var(--color-bg-elevated); border: 1px solid var(--color-border);"
  >
    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="color: var(--color-primary);">
      <use :href="`/icons/sprite.svg#${icon}`" />
    </svg>
  </div>
</template>
