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
    class="inline-flex items-center gap-2 px-5 py-2.5 rounded-md text-sm font-medium text-white transition-all hover:opacity-90 hover:shadow-lg cursor-pointer"
    style="background: var(--color-primary);"
  >
    {{ text }}
  </component>
</template>
