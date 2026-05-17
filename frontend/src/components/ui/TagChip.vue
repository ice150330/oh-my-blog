<script setup lang="ts">
defineProps<{
  label: string
}>()
</script>

<template>
  <span
    class="inline-flex items-center px-2.5 py-0.5 rounded-full text-[11px] font-normal whitespace-nowrap"
    style="font-family: var(--font-code); color: var(--color-text-2); background: var(--color-bg-elevated); border: 1px solid var(--color-border);"
  >
    {{ label }}
  </span>
</template>
