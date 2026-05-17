<script setup lang="ts">
defineProps<{
  year: string
  place: string
  title: string
  description: string
  last?: boolean
}>()
</script>

<template>
  <div class="flex gap-4">
    <div class="flex flex-col items-center pt-1">
      <span class="w-3 h-3 rounded-full border-2" style="background: var(--color-primary); border-color: var(--color-bg-elevated);" />
      <span v-if="!last" class="w-0.5 flex-1 mt-1 min-h-[60px]" style="background: var(--color-border);" />
    </div>
    <div class="flex flex-col gap-1 pb-6">
      <span class="text-[11px] font-normal" style="font-family: var(--font-code); color: var(--color-primary);">
        {{ year }} · {{ place }}
      </span>
      <span class="text-base font-semibold" style="color: var(--color-text-1);">
        {{ title }}
      </span>
      <span class="text-[13px] font-normal" style="color: var(--color-text-2);">
        {{ description }}
      </span>
    </div>
  </div>
</template>
