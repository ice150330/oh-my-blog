<script setup lang="ts">
defineProps<{
  label: string
  active?: boolean
}>()

const emit = defineEmits<{
  click: []
}>()
</script>

<template>
  <div
    class="flex items-center gap-3 py-1 cursor-pointer hover:opacity-80 transition-opacity"
    @click="emit('click')"
  >
    <span
      class="w-0.5 h-4 rounded-full transition-colors"
      :style="{ background: active ? 'var(--color-primary)' : 'transparent' }"
    />
    <span
      class="text-[13px] font-normal"
      :style="{ color: active ? 'var(--color-text-1)' : 'var(--color-text-2)' }"
    >
      {{ label }}
    </span>
  </div>
</template>
