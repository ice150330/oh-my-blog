<script setup lang="ts">
defineProps<{
  name: string
  level: number
}>()
</script>

<template>
  <div class="flex flex-col gap-2 w-full">
    <div class="flex justify-between items-center">
      <span class="text-[13px] font-medium" style="color: var(--color-text-1);">{{ name }}</span>
      <span class="text-[11px] font-normal" style="font-family: var(--font-code); color: var(--color-text-2);">{{ level }}%</span>
    </div>
    <div class="w-full h-1 rounded-full overflow-hidden" style="background: var(--color-border);">
      <div
        class="h-full rounded-full transition-all duration-1000"
        style="background: var(--color-primary); width: var(--skill-level);"
        :style="{ '--skill-level': `${level}%` }"
      />
    </div>
  </div>
</template>
