import { onMounted, onUnmounted } from 'vue'
import { gsap } from 'gsap'

export function useCursorGlow(containerRef: { value: HTMLElement | null }) {
  let glowEl: HTMLDivElement | null = null
  let quickSetX: gsap.QuickToFunc | null = null
  let quickSetY: gsap.QuickToFunc | null = null

  onMounted(() => {
    const container = containerRef.value
    if (!container) return

    // 创建光晕元素
    glowEl = document.createElement('div')
    glowEl.style.cssText = `
      position: absolute;
      width: 400px;
      height: 400px;
      border-radius: 50%;
      pointer-events: none;
      opacity: 0.06;
      background: radial-gradient(circle, var(--color-primary) 0%, transparent 70%);
      transform: translate(-50%, -50%);
      z-index: 0;
      transition: opacity 0.3s ease;
    `
    container.style.position = 'relative'
    container.style.overflow = 'hidden'
    container.appendChild(glowEl)

    quickSetX = gsap.quickTo(glowEl, 'x', { duration: 0.4, ease: 'power2.out' })
    quickSetY = gsap.quickTo(glowEl, 'y', { duration: 0.4, ease: 'power2.out' })

    const onMove = (e: MouseEvent) => {
      const rect = container.getBoundingClientRect()
      quickSetX?.(e.clientX - rect.left)
      quickSetY?.(e.clientY - rect.top)
    }

    const onEnter = () => { if (glowEl) glowEl.style.opacity = '0.06' }
    const onLeave = () => { if (glowEl) glowEl.style.opacity = '0' }

    container.addEventListener('mousemove', onMove)
    container.addEventListener('mouseenter', onEnter)
    container.addEventListener('mouseleave', onLeave)

    onUnmounted(() => {
      container.removeEventListener('mousemove', onMove)
      container.removeEventListener('mouseenter', onEnter)
      container.removeEventListener('mouseleave', onLeave)
      glowEl?.remove()
    })
  })
}
