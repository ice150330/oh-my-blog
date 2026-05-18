<script setup lang="ts">
import { computed, onMounted, onUnmounted } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { gsap } from 'gsap'
import { ScrollTrigger } from 'gsap/ScrollTrigger'
import { useI18n } from 'vue-i18n'
import { projects, categoryLabels } from '@/data/projects'
import CategoryBadge from '@/components/ui/CategoryBadge.vue'
import TagChip from '@/components/ui/TagChip.vue'
import GhostButton from '@/components/ui/GhostButton.vue'
import Breadcrumb from '@/components/ui/Breadcrumb.vue'

const { t, locale } = useI18n()
const route = useRoute()
const slug = computed(() => route.params.slug as string)

const project = computed(() => projects.find(p => p.slug === slug.value))
const projectIndex = computed(() => projects.findIndex(p => p.slug === slug.value))
const prevProject = computed(() => {
  const idx = projectIndex.value
  if (idx <= 0) return null
  return projects[idx - 1]
})
const nextProject = computed(() => {
  const idx = projectIndex.value
  if (idx === -1 || idx >= projects.length - 1) return null
  return projects[idx + 1]
})

const coverStyle = computed(() => {
  if (!project.value) return {}
  const [c1, c2] = project.value.coverGradient
  return { background: `radial-gradient(circle at 50% 50%, ${c1}, ${c2})` }
})

let ctx: gsap.Context | null = null

onMounted(() => {
  ctx = gsap.context(() => {
    const reduced = window.matchMedia('(prefers-reduced-motion: reduce)').matches
    if (reduced) return

    gsap.fromTo('.project-cover > *', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.8, stagger: 0.1, ease: 'power3.out', delay: 0.1 })

    ScrollTrigger.create({
      trigger: '.project-content',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.project-content > *', { y: 40, opacity: 0 }, { y: 0, opacity: 1, duration: 0.7, stagger: 0.12, ease: 'power3.out' })
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
  <div v-if="project" class="flex flex-col">
    <div class="max-w-[1400px] mx-auto px-6 lg:px-10 pt-6">
      <Breadcrumb :items="[
        { label: '首页', to: '/' },
        { label: '项目', to: '/projects' },
        { label: locale === 'zh' ? project.title : (project.titleEn || project.title) }
      ]" />
    </div>
    <section
      class="project-cover relative flex flex-col items-center justify-center gap-4 text-center min-h-[300px] sm:min-h-[380px] md:min-h-[480px] px-6"
      :style="coverStyle"
    >
      <CategoryBadge :category="project.category" />
      <h1 class="text-5xl md:text-6xl font-bold tracking-tight" style="font-family: var(--font-display); color: var(--color-text-1);">
        {{ locale === 'zh' ? project.title : (project.titleEn || project.title) }}
      </h1>
      <p class="text-lg max-w-[640px]" style="color: var(--color-text-2);">
        {{ locale === 'zh' ? project.description : (project.descriptionEn || project.description) }}
      </p>
    </section>

    <div class="max-w-[1400px] mx-auto px-6 lg:px-10 w-full py-6 flex flex-wrap items-center gap-4 border-b" style="border-color: var(--color-border);">
      <span class="text-[13px]" style="color: var(--color-text-3);">
        {{ t('common.category') }}: {{ categoryLabels[project.category] }}
      </span>
      <span class="w-px h-4" style="background: var(--color-border);" />
      <span class="text-[13px]" style="color: var(--color-text-3);">
        {{ project.tags.length }} {{ t('common.techStackCount') }}
      </span>
      <span class="w-px h-4" style="background: var(--color-border);" />
      <div class="flex flex-wrap gap-2">
        <TagChip v-for="tag in project.tags" :key="tag" :label="tag" />
      </div>
      <div class="flex-1" />
      <GhostButton v-if="project.repoUrl" :text="t('common.source') + ' →'" :href="project.repoUrl" />
    </div>

    <div class="max-w-[1400px] mx-auto px-6 lg:px-10 w-full py-4 flex flex-wrap items-center gap-x-6 gap-y-2 border-b" style="border-color: var(--color-border);">
      <span v-if="project.projectType" class="text-[13px]" style="color: var(--color-text-3);">
        {{ t('common.projectType') }}: {{ locale === 'zh' ? project.projectType : (project.projectTypeEn || project.projectType) }}
      </span>
      <span v-if="project.completionDate" class="text-[13px]" style="color: var(--color-text-3);">
        {{ t('common.completionDate') }}: {{ project.completionDate }}
      </span>
      <span v-if="project.codeLines" class="text-[13px]" style="color: var(--color-text-3);">
        {{ t('common.codeLines') }}: {{ project.codeLines.toLocaleString() }}
      </span>
      <span v-if="project.dataScale" class="text-[13px]" style="color: var(--color-text-3);">
        {{ t('common.dataScale') }}: {{ locale === 'zh' ? project.dataScale : (project.dataScaleEn || project.dataScale) }}
      </span>
    </div>

    <div class="project-content max-w-[1400px] mx-auto px-6 lg:px-10 w-full py-12 flex flex-col gap-12">
      <div class="flex flex-col gap-4">
        <h2 class="text-xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">{{ t('common.demoPreview') }}</h2>
        <div
          class="w-full rounded-2xl flex flex-col items-center justify-center gap-4 min-h-[480px]"
          style="background: var(--color-bg-elevated); border: 2px dashed var(--color-border);"
        >
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" style="color: var(--color-text-3);">
            <use href="/icons/sprite.svg#monitor" />
          </svg>
          <span class="text-sm font-medium" style="color: var(--color-text-3);">Demo Preview</span>
          <span class="text-xs" style="color: var(--color-text-3);">{{ t('common.demoPlaceholder') }}</span>
        </div>
      </div>

      <div class="max-w-[800px] mx-auto w-full flex flex-col gap-6">
        <h2 class="text-2xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">{{ t('common.projectIntro') }}</h2>
        <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
          {{ locale === 'zh' ? project.description : (project.descriptionEn || project.description) }}
        </p>

        <h3 class="text-lg font-semibold mt-2" style="color: var(--color-text-1);">{{ t('common.techArch') }}</h3>
        <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
          {{ locale === 'zh'
            ? (project.techArchDetail || `${t('common.techArch')}: ${project.tags.join('、')}`)
            : (project.techArchDetailEn || project.techArchDetail || `${t('common.techArch')}: ${project.tags.join(', ')}`)
          }}
        </p>

        <h3 class="text-lg font-semibold mt-2" style="color: var(--color-text-1);">{{ t('common.coreHighlights') }}</h3>
        <ul class="flex flex-col gap-2 list-disc list-inside text-[15px] leading-relaxed" style="color: var(--color-text-2);">
          <li v-for="(hl, i) in (locale === 'zh'
            ? (project.highlights || ['模块化架构设计,支持功能按需加载与扩展', '数据可视化层采用 ECharts / D3.js,实现交互式图表', '工程化配置完善,包含 ESLint、Prettier、CI/CD 流水线', '响应式布局适配,覆盖桌面端与移动端场景'])
            : (project.highlightsEn || project.highlights || ['Modular architecture supporting on-demand loading and extension', 'Data visualization layer using ECharts / D3.js for interactive charts', 'Complete engineering setup with ESLint, Prettier, and CI/CD pipelines', 'Responsive layout covering desktop and mobile scenarios'])
          )" :key="i">
            {{ hl }}
          </li>
        </ul>

        <h3 class="text-lg font-semibold mt-2" style="color: var(--color-text-1);">{{ t('common.review') }}</h3>
        <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
          {{ locale === 'zh'
            ? (project.review || t('common.review'))
            : (project.reviewEn || project.review || t('common.review'))
          }}
        </p>
      </div>

      <div class="max-w-[800px] mx-auto w-full flex flex-wrap gap-2 pt-6 border-t" style="border-color: var(--color-border);">
        <span class="text-[13px] mr-2" style="color: var(--color-text-3);">{{ t('common.tags') }}:</span>
        <TagChip v-for="tag in project.tags" :key="tag" :label="tag" />
      </div>

      <div class="max-w-[800px] mx-auto w-full flex items-center justify-between pt-8 border-t" style="border-color: var(--color-border);">
        <RouterLink
          v-if="prevProject"
          :to="`/projects/${prevProject.slug}`"
          class="flex flex-col gap-1 transition-opacity hover:opacity-70"
        >
          <span class="text-[11px]" style="color: var(--color-text-3);">← {{ t('common.prev') }}</span>
          <span class="text-sm font-medium" style="color: var(--color-text-1);">{{ locale === 'zh' ? prevProject.title : (prevProject.titleEn || prevProject.title) }}</span>
        </RouterLink>
        <span v-else />
        <RouterLink
          v-if="nextProject"
          :to="`/projects/${nextProject.slug}`"
          class="flex flex-col gap-1 items-end transition-opacity hover:opacity-70"
        >
          <span class="text-[11px]" style="color: var(--color-text-3);">{{ t('common.next') }} →</span>
          <span class="text-sm font-medium" style="color: var(--color-text-1);">{{ locale === 'zh' ? nextProject.title : (nextProject.titleEn || nextProject.title) }}</span>
        </RouterLink>
        <span v-else />
      </div>
    </div>
  </div>

  <div v-else class="max-w-[1400px] mx-auto px-6 py-20 text-center">
    <h1 class="text-4xl font-bold" style="color: var(--color-text-1);">{{ t('common.notFound') }}</h1>
    <RouterLink to="/projects" class="text-sm mt-4 inline-block" style="color: var(--color-primary);">
      {{ t('common.backToList') }}
    </RouterLink>
  </div>
</template>
