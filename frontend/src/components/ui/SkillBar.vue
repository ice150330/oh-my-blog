<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { animateProgress } from '@/composables/useGsapAnimation'

const props = defineProps<{
  name: string
  level: number
}>()

const barRef = ref<HTMLDivElement | null>(null)

onMounted(() => {
  if (!barRef.value) return
  animateProgress(barRef.value, 0)

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting && barRef.value) {
          animateProgress(barRef.value, 0)
          setTimeout(() => {
            if (barRef.value) {
              animateProgress(barRef.value, props.level)
            }
          }, 100)
          observer.disconnect()
        }
      })
    },
    { threshold: 0.5 }
  )
  if (barRef.value.parentElement) {
    observer.observe(barRef.value.parentElement)
  }
})
</script>

<template>
  <div class="flex flex-col gap-2 w-full">
    <div class="flex justify-between items-center">
      <span class="text-[13px] font-medium" style="color: var(--color-text-1);">{{ name }}</span>
      <span class="text-[11px] font-normal" style="font-family: var(--font-code); color: var(--color-text-2);">{{ level }}%</span>
    </div>
    <div class="w-full h-1.5 rounded-full overflow-hidden" style="background: var(--color-border);">
      <div
        ref="barRef"
        class="h-full rounded-full"
        style="background: linear-gradient(90deg, var(--color-primary) 0%, var(--color-primary-50) 100%); width: var(--skill-level);"
        :style="{ '--skill-level': `0%` }"
      />
    </div>
  </div>
</template>
