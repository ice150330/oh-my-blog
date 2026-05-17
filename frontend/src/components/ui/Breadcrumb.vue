<script setup lang="ts">
defineProps<{
  items: { label: string; to?: string }[]
}>()
</script>

<template>
  <nav class="flex items-center gap-2 text-[13px]" style="color: var(--color-text-3);">
    <span
      v-for="(item, idx) in items"
      :key="idx"
      class="flex items-center gap-2"
    >
      <RouterLink
        v-if="item.to && idx !== items.length - 1"
        :to="item.to"
        class="hover:text-[var(--color-primary)] transition-colors"
      >
        {{ item.label }}
      </RouterLink>
      <span v-else style="color: var(--color-text-2);">{{ item.label }}</span>
      <span v-if="idx !== items.length - 1">/</span>
    </span>
  </nav>
</template>
