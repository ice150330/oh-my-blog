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
    class="inline-flex items-center gap-2 px-5 py-2.5 rounded-md text-sm font-medium transition-all hover:bg-black/5 cursor-pointer"
    style="color: var(--color-text-1); border: 1px solid var(--color-border-strong);"
  >
    {{ text }}
  </component>
</template>
