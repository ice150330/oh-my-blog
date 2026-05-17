<script setup lang="ts">
defineProps<{
  en: string
  zh: string
}>()
</script>

<template>
  <div class="flex flex-col gap-1">
    <h2 class="text-[40px] font-bold leading-tight tracking-tight" style="font-family: var(--font-display); color: var(--color-text-1);">
      {{ en }}
    </h2>
    <p class="text-[22px] font-normal" style="font-family: var(--font-accent); color: var(--color-text-2);">
      {{ zh }}
    </p>
    <span class="w-8 h-0.5 mt-1 rounded-full" style="background: var(--color-primary);"></span>
  </div>
</template>
