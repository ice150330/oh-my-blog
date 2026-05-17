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
    class="group relative inline-flex items-center gap-2 px-6 py-2.5 rounded-lg text-sm font-medium text-white transition-all hover:shadow-lg overflow-hidden cursor-pointer"
    style="background: var(--color-primary);"
  >
    <span class="relative z-10">{{ text }}</span>
    <span
      class="absolute inset-0 opacity-0 group-hover:opacity-100 transition-opacity duration-500"
      style="background: radial-gradient(circle at center, var(--color-primary-30), transparent 70%);"
    />
  </component>
</template>
