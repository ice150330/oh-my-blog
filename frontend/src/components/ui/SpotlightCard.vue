<script setup lang="ts">
import { ref } from 'vue'

const cardRef = ref<HTMLDivElement | null>(null)
const spotlight = ref({ x: 50, y: 50, opacity: 0 })

function onMouseMove(e: MouseEvent) {
  if (!cardRef.value) return
  const rect = cardRef.value.getBoundingClientRect()
  spotlight.value = {
    x: ((e.clientX - rect.left) / rect.width) * 100,
    y: ((e.clientY - rect.top) / rect.height) * 100,
    opacity: 1,
  }
}

function onMouseLeave() {
  spotlight.value.opacity = 0
}
</script>

<template>
  <div
    ref="cardRef"
    class="relative overflow-hidden rounded-2xl transition-transform duration-300 hover:-translate-y-1"
    style="background: var(--color-card); border: 1px solid var(--color-border);"
    @mousemove="onMouseMove"
    @mouseleave="onMouseLeave"
  >
    <div
      class="pointer-events-none absolute inset-0 transition-opacity duration-500"
      :style="{
        opacity: spotlight.opacity,
        background: `radial-gradient(600px circle at ${spotlight.x}% ${spotlight.y}%, var(--color-primary-10), transparent 40%)`,
      }"
    />
    <div class="relative z-10">
      <slot />
    </div>
  </div>
</template>
