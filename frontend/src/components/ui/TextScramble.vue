<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'

const props = defineProps<{
  text: string
  duration?: number
}>()

const displayText = ref('')
const chars = '!<>-_\\/[]{}—=+*^?#________'

function scramble(target: string, duration = 1200) {
  const length = target.length
  const steps = 20
  const interval = duration / steps
  let frame = 0

  const timer = setInterval(() => {
    frame++
    let output = ''
    const progress = frame / steps

    for (let i = 0; i < length; i++) {
      if (target[i] === ' ') {
        output += ' '
      } else if (i / length < progress) {
        output += target[i]
      } else {
        output += chars[Math.floor(Math.random() * chars.length)]
      }
    }

    displayText.value = output

    if (frame >= steps) {
      displayText.value = target
      clearInterval(timer)
    }
  }, interval)

  return timer
}

let timer: ReturnType<typeof setInterval> | null = null

function trigger() {
  if (timer) clearInterval(timer)
  displayText.value = ''
  timer = scramble(props.text, props.duration)
}

watch(() => props.text, trigger, { immediate: true })

onMounted(() => {
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          trigger()
          observer.disconnect()
        }
      })
    },
    { threshold: 0.5 }
  )
  const el = document.querySelector('[data-scramble]')
  if (el) observer.observe(el)
})
</script>

<template>
  <span data-scramble style="font-family: var(--font-code);">
    {{ displayText }}
  </span>
</template>
