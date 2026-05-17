import { gsap } from 'gsap'
import { ScrollTrigger } from 'gsap/ScrollTrigger'

// ScrollTrigger 已在 main.ts 中全局注册一次，此处直接使用即可

// 检查用户是否偏好减少动画
export const prefersReducedMotion = (): boolean => {
  if (typeof window === 'undefined') return false
  return window.matchMedia('(prefers-reduced-motion: reduce)').matches
}

// 通用动画预设
export const fadeInUp = (
  targets: string | Element | Element[],
  options: gsap.TweenVars = {}
): gsap.core.Tween | undefined => {
  if (prefersReducedMotion()) return undefined
  return gsap.from(targets, {
    y: 40,
    opacity: 0,
    duration: 0.8,
    ease: 'power3.out',
    ...options,
  })
}

export const fadeInLeft = (
  targets: string | Element | Element[],
  options: gsap.TweenVars = {}
): gsap.core.Tween | undefined => {
  if (prefersReducedMotion()) return undefined
  return gsap.from(targets, {
    x: -60,
    opacity: 0,
    duration: 0.8,
    ease: 'power3.out',
    ...options,
  })
}

export const fadeInRight = (
  targets: string | Element | Element[],
  options: gsap.TweenVars = {}
): gsap.core.Tween | undefined => {
  if (prefersReducedMotion()) return undefined
  return gsap.from(targets, {
    x: 60,
    opacity: 0,
    duration: 0.8,
    ease: 'power3.out',
    ...options,
  })
}

export const scaleIn = (
  targets: string | Element | Element[],
  options: gsap.TweenVars = {}
): gsap.core.Tween | undefined => {
  if (prefersReducedMotion()) return undefined
  return gsap.from(targets, {
    scale: 0.95,
    opacity: 0,
    duration: 0.7,
    ease: 'power2.out',
    ...options,
  })
}

export const staggerFadeInUp = (
  targets: string | Element | Element[],
  stagger = 0.1,
  options: gsap.TweenVars = {}
): gsap.core.Tween | undefined => {
  if (prefersReducedMotion()) return undefined
  return gsap.from(targets, {
    y: 30,
    opacity: 0,
    duration: 0.6,
    stagger,
    ease: 'power3.out',
    ...options,
  })
}

// ScrollTrigger 封装
export const scrollFadeInUp = (
  targets: string | Element | Element[],
  options: gsap.TweenVars = {}
): ScrollTrigger[] => {
  if (prefersReducedMotion()) return []
  const elements = gsap.utils.toArray(targets)
  return elements.map((el) =>
    ScrollTrigger.create({
      trigger: el as Element,
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.from(el as Element, {
          y: 40,
          opacity: 0,
          duration: 0.8,
          ease: 'power3.out',
          ...options,
        })
      },
    })
  )
}

export const scrollStaggerFadeInUp = (
  targets: string | Element | Element[],
  stagger = 0.1,
  options: gsap.TweenVars = {}
): ScrollTrigger[] => {
  if (prefersReducedMotion()) return []
  const elements = gsap.utils.toArray(targets)
  if (elements.length === 0) return []

  // 使用第一个元素作为 trigger
  return [
    ScrollTrigger.create({
      trigger: elements[0] as Element,
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.from(elements, {
          y: 30,
          opacity: 0,
          duration: 0.6,
          stagger,
          ease: 'power3.out',
          ...options,
        })
      },
    }),
  ]
}

// 数字计数动画
export const countUp = (
  el: Element,
  target: number,
  duration = 1.5,
  suffix = ''
): gsap.core.Tween | undefined => {
  if (prefersReducedMotion()) {
    el.textContent = target + suffix
    return undefined
  }
  const obj = { val: 0 }
  return gsap.to(obj, {
    val: target,
    duration,
    ease: 'power2.out',
    onUpdate: () => {
      el.textContent = Math.round(obj.val) + suffix
    },
  })
}

// 进度条填充动画
export const animateProgress = (
  el: Element,
  targetPercent: number,
  duration = 1.2
): gsap.core.Tween | undefined => {
  if (prefersReducedMotion()) {
    ;(el as HTMLElement).style.setProperty('--skill-level', `${targetPercent}%`)
    return undefined
  }
  const obj = { val: 0 }
  return gsap.to(obj, {
    val: targetPercent,
    duration,
    ease: 'power2.out',
    onUpdate: () => {
      ;(el as HTMLElement).style.setProperty('--skill-level', `${obj.val.toFixed(1)}%`)
    },
  })
}

// 清理所有 ScrollTrigger
export const killAllScrollTriggers = (): void => {
  ScrollTrigger.getAll().forEach((st) => st.kill())
}
