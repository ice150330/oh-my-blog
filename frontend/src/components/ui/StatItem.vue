<script setup lang="ts">
defineProps<{
  value: string
  label: string
}>()
</script>

<template>
  <div class="flex flex-col items-center gap-1">
    <span class="text-4xl font-bold" style="font-family: var(--font-display); color: var(--color-primary);">
      {{ value }}
    </span>
    <span class="text-[13px]" style="color: var(--color-text-2);">
      {{ label }}
    </span>
  </div>
</template>
