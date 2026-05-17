<script setup lang="ts">
defineProps<{
  text: string
  to?: string
  href?: string
}>()
</script>

<template>
  <component
    :is="to ? 'RouterLink' : href ? 'a' : 'button'"
    :to="to"
    :href="href"
    target="_blank"
    class="group relative inline-flex items-center gap-2 px-6 py-2.5 rounded-lg text-sm font-medium transition-all overflow-hidden cursor-pointer"
    style="color: var(--color-text-1); border: 1px solid var(--color-border-strong);"
  >
    <span class="relative z-10">{{ text }}</span>
    <span
      class="absolute inset-0 opacity-0 group-hover:opacity-100 transition-opacity duration-300"
      style="background: var(--color-primary-soft);"
    />
  </component>
</template>
