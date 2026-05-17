import { ref, onMounted, onUnmounted } from 'vue'

function getElement(refValue: unknown): HTMLElement | null {
  if (!refValue) return null
  if (refValue instanceof HTMLElement) return refValue
  const component = refValue as { $el?: unknown }
  if (component.$el instanceof HTMLElement) return component.$el
  return null
}

export function useTilt(elRef: { value: unknown }, max = 8) {
  const style = ref({ transform: '', transition: '' })

  const handleMove = (e: MouseEvent) => {
    const el = getElement(elRef.value)
    if (!el) return
    const rect = el.getBoundingClientRect()
    const x = e.clientX - rect.left
    const y = e.clientY - rect.top
    const centerX = rect.width / 2
    const centerY = rect.height / 2
    const rotateX = ((y - centerY) / centerY) * -max
    const rotateY = ((x - centerX) / centerX) * max
    style.value = {
      transform: `perspective(800px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) scale3d(1.02, 1.02, 1.02)`,
      transition: 'transform 0.1s ease-out',
    }
  }

  const handleLeave = () => {
    style.value = {
      transform: 'perspective(800px) rotateX(0) rotateY(0) scale3d(1, 1, 1)',
      transition: 'transform 0.4s ease-out',
    }
  }

  onMounted(() => {
    const el = getElement(elRef.value)
    if (!el) return
    el.addEventListener('mousemove', handleMove)
    el.addEventListener('mouseleave', handleLeave)
  })

  onUnmounted(() => {
    const el = getElement(elRef.value)
    if (!el) return
    el.removeEventListener('mousemove', handleMove)
    el.removeEventListener('mouseleave', handleLeave)
  })

  return style
}
