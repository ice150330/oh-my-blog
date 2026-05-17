<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { gsap } from 'gsap'
import { ScrollTrigger } from 'gsap/ScrollTrigger'
import { useI18n } from 'vue-i18n'
import { projects, categoryLabels, categoryLabelsEn } from '@/data/projects'
import SectionTitle from '@/components/ui/SectionTitle.vue'
import ProjectCard from '@/components/ui/ProjectCard.vue'
import CategoryBadge from '@/components/ui/CategoryBadge.vue'
import GhostButton from '@/components/ui/GhostButton.vue'

const { t, locale } = useI18n()

const categories = computed(() => {
  const labels = locale.value === 'zh' ? categoryLabels : categoryLabelsEn
  return [
    { key: 'all', label: t('projects.category.all'), count: projects.length },
    { key: 'ai', label: labels.ai, count: projects.filter(p => p.category === 'ai').length },
    { key: 'data', label: labels.data, count: projects.filter(p => p.category === 'data').length },
    { key: 'business', label: labels.business, count: projects.filter(p => p.category === 'business').length },
    { key: 'algo', label: labels.algo, count: projects.filter(p => p.category === 'algo').length },
    { key: 'meta', label: labels.meta, count: projects.filter(p => p.category === 'meta').length },
  ]
})

const activeCategory = ref('all')

const filteredProjects = computed(() => {
  if (activeCategory.value === 'all') return projects.filter(p => p.category !== 'meta')
  return projects.filter(p => p.category === activeCategory.value)
})

const metaProjects = computed(() => projects.filter(p => p.category === 'meta'))

let ctx: gsap.Context | null = null

onMounted(() => {
  ctx = gsap.context(() => {
    const reduced = window.matchMedia('(prefers-reduced-motion: reduce)').matches
    if (reduced) return

    gsap.fromTo('.projects-header', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.7, ease: 'power3.out' })
    gsap.fromTo('.category-tab', { y: 20, opacity: 0 }, { y: 0, opacity: 1, duration: 0.5, stagger: 0.06, ease: 'power3.out', delay: 0.2 })

    ScrollTrigger.create({
      trigger: '.project-grid',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.project-grid .project-card', { y: 40, opacity: 0, scale: 0.98 }, { y: 0, opacity: 1, scale: 1, duration: 0.7, stagger: 0.1, ease: 'power3.out' })
      }
    })

    ScrollTrigger.create({
      trigger: '.meta-section',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.meta-section .meta-card', { y: 50, opacity: 0 }, { y: 0, opacity: 1, duration: 0.8, stagger: 0.15, ease: 'power3.out' })
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
    <div class="projects-header mb-8">
      <SectionTitle en="Projects" :zh="t('projects.title')" />
      <p class="text-[15px] leading-relaxed mt-3 max-w-[760px]" style="color: var(--color-text-2);">
        {{ projects.length }}{{ t('projects.subtitle') }}
      </p>
    </div>

    <div class="flex flex-wrap gap-3 mb-8">
      <button
        v-for="cat in categories"
        :key="cat.key"
        class="category-tab px-4 py-1.5 rounded-full text-[13px] font-medium transition-all cursor-pointer"
        :class="activeCategory === cat.key ? 'text-white' : 'text-[var(--color-text-2)] border'"
        :style="activeCategory === cat.key ? { background: 'var(--color-primary)' } : { borderColor: 'var(--color-border)' }"
        @click="activeCategory = cat.key"
      >
        {{ cat.label }} · {{ cat.count }}
      </button>
    </div>

    <div class="project-grid grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <ProjectCard
        v-for="project in filteredProjects"
        :key="project.id"
        :project="project"
        class="project-card"
      />
    </div>

    <div class="meta-section flex flex-col gap-6 mt-12">
      <div
        v-for="mp in metaProjects"
        :key="mp.id"
        class="meta-card flex flex-col md:flex-row items-center gap-8 md:gap-12 rounded-xl p-8 md:p-10"
        style="background: var(--color-card); border: 1px solid var(--color-border); box-shadow: 0 4px 16px var(--glow-primary);"
      >
        <div class="flex-1 flex flex-col gap-3">
          <CategoryBadge :category="mp.category" />
          <h3 class="text-2xl font-bold" style="font-family: var(--font-accent); color: var(--color-text-1);">
            {{ locale === 'zh' ? mp.title : (mp.titleEn || mp.title) }}
          </h3>
          <p class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">
            {{ locale === 'zh' ? mp.description : (mp.descriptionEn || mp.description) }}
          </p>
          <div class="flex flex-wrap gap-2 mt-1">
            <span
              v-for="tag in mp.tags"
              :key="tag"
              class="px-2.5 py-0.5 rounded-full text-[11px]"
              style="font-family: var(--font-code); color: var(--color-text-2); background: var(--color-bg-elevated); border: 1px solid var(--color-border);"
            >
              {{ tag }}
            </span>
          </div>
        </div>
        <div class="flex flex-col items-end gap-3 min-w-[160px]">
          <span class="text-7xl font-bold" style="font-family: var(--font-display); color: var(--color-text-3);">
            {{ mp.title.includes('v3') ? 'v3.0' : 'v3.1' }}
          </span>
          <GhostButton :text="t('common.detail') + ' →'" :to="`/projects/${mp.slug}`" />
        </div>
      </div>
    </div>
  </div>
</template>
