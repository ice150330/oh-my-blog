<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue'
import { gsap } from 'gsap'
import { ScrollTrigger } from 'gsap/ScrollTrigger'
import { useI18n } from 'vue-i18n'
import { skills, techCategories, stats } from '@/data/techStack'
import SectionTitle from '@/components/ui/SectionTitle.vue'
import SkillBar from '@/components/ui/SkillBar.vue'
import StatItem from '@/components/ui/StatItem.vue'
import IconBadge from '@/components/ui/IconBadge.vue'

const { t } = useI18n()

let ctx: gsap.Context | null = null

onMounted(() => {
  ctx = gsap.context(() => {
    const reduced = window.matchMedia('(prefers-reduced-motion: reduce)').matches
    if (reduced) return

    gsap.fromTo('.tech-header', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.7, ease: 'power3.out' })

    ScrollTrigger.create({
      trigger: '.radar-section',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.radar-section > *', { y: 40, opacity: 0 }, { y: 0, opacity: 1, duration: 0.8, stagger: 0.15, ease: 'power3.out' })
      }
    })

    ScrollTrigger.create({
      trigger: '.category-grid',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.category-grid > div', { y: 40, opacity: 0, scale: 0.98 }, { y: 0, opacity: 1, scale: 1, duration: 0.7, stagger: 0.1, ease: 'power3.out' })
      }
    })

    ScrollTrigger.create({
      trigger: '.stats-bar',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.stats-bar > div', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.6, stagger: 0.08, ease: 'power3.out' })
      }
    })

    // 确保 ScrollTrigger 位置计算正确
    ScrollTrigger.refresh()
  })
})

onUnmounted(() => {
  ctx?.revert()
  ctx = null
})
</script>

<template>
  <div class="max-w-[1400px] mx-auto px-6 lg:px-10 py-20">
    <div class="tech-header flex flex-col items-center text-center mb-16">
      <SectionTitle en="Tech Stack" :zh="t('techStack.title')" />
      <p class="text-base mt-3" style="font-family: var(--font-accent); color: var(--color-text-2);">
        {{ t('techStack.subtitle') }}
      </p>
    </div>

    <div class="radar-section flex flex-col lg:flex-row items-center gap-12 mb-16">
      <div class="w-full lg:w-[560px] aspect-square rounded-3xl flex items-center justify-center relative overflow-hidden" style="background: var(--color-bg-elevated); border: 1px solid var(--color-border);">
        <div class="absolute w-[360px] h-[360px] rounded-full border" style="border-color: var(--color-border);" />
        <div class="absolute w-[240px] h-[240px] rounded-full border" style="border-color: var(--color-border);" />
        <div class="absolute w-[120px] h-[120px] rounded-full border" style="border-color: var(--color-border);" />
        <div class="flex flex-col items-center gap-2 z-10">
          <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" style="color: var(--color-primary);"><use href="/icons/sprite.svg#chart-column" /></svg>
          <span class="text-sm font-medium" style="color: var(--color-text-3); font-family: var(--font-code);">Skill Radar</span>
        </div>
      </div>

      <div class="flex-1 w-full">
        <h4 class="text-[13px] font-bold tracking-widest mb-5" style="color: var(--color-text-3); font-family: var(--font-display);">
          6 {{ t('techStack.dimensions') }}
        </h4>
        <div class="flex flex-col gap-5">
          <SkillBar
            v-for="skill in skills.slice(0, 6)"
            :key="skill.name"
            :name="skill.name"
            :level="skill.level"
          />
        </div>
      </div>
    </div>

    <div class="category-grid mb-16">
      <h2 class="text-4xl font-bold tracking-tight mb-2" style="font-family: var(--font-display); color: var(--color-text-1);">
        {{ t('techStack.byCategory') }}
      </h2>
      <p class="text-sm mb-8" style="font-family: var(--font-accent); color: var(--color-text-2);">
        {{ t('techStack.categorySub') }}
      </p>
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div
          v-for="cat in techCategories"
          :key="cat.name"
          class="rounded-2xl p-6 flex flex-col gap-5"
          style="background: var(--color-card); border: 1px solid var(--color-border);"
        >
          <div class="flex items-center gap-3">
            <IconBadge :icon="cat.icon" />
            <div class="flex flex-col">
              <span class="text-lg font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">{{ cat.name }}</span>
              <span class="text-xs" style="color: var(--color-text-3); font-family: var(--font-accent);">{{ cat.sub }}</span>
            </div>
          </div>
          <div class="flex flex-wrap gap-2">
            <span
              v-for="tag in cat.tags"
              :key="tag"
              class="px-2.5 py-1 rounded-full text-xs"
              style="font-family: var(--font-code); color: var(--color-text-2); background: var(--color-bg-elevated); border: 1px solid var(--color-border);"
            >
              {{ tag }}
            </span>
          </div>
        </div>
      </div>
    </div>

    <div class="stats-bar grid grid-cols-2 md:grid-cols-4 gap-6">
      <StatItem
        v-for="stat in stats"
        :key="stat.label"
        :value="stat.value"
        :label="stat.label"
      />
    </div>
  </div>
</template>
