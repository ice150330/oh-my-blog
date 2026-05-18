<script setup lang="ts">
import { categoryLabels } from '@/data/projects'

defineProps<{
  category: 'ai' | 'data' | 'business' | 'algo' | 'research' | 'meta'
}>()

const colorMap: Record<string, string> = {
  ai: '#2563EB',
  data: '#8B5CF6',
  business: '#06B6D4',
  algo: '#F97316',
  research: '#A855F7',
  meta: '#71717A',
}
</script>

<template>
  <span
    class="inline-flex items-center px-2.5 py-[3px] rounded-full text-[11px] font-semibold text-white whitespace-nowrap shadow-sm"
    :style="{
      background: colorMap[category],
      boxShadow: `0 2px 8px ${colorMap[category]}40`,
    }"
  >
    {{ categoryLabels[category] }}
  </span>
</template>
