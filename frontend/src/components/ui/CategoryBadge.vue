<script setup lang="ts">
import { categoryLabels } from '@/data/projects'

defineProps<{
  category: 'ai' | 'data' | 'business' | 'algo' | 'meta'
}>()

const colorMap: Record<string, string> = {
  ai: '#2563EB',
  data: '#8B5CF6',
  business: '#06B6D4',
  algo: '#F97316',
  meta: '#71717A',
}
</script>

<template>
  <span
    class="inline-flex items-center px-2.5 py-[3px] rounded-full text-[11px] font-semibold text-white whitespace-nowrap"
    :style="{ background: colorMap[category] }"
  >
    {{ categoryLabels[category] }}
  </span>
</template>
