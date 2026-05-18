<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { gsap } from 'gsap'
import { ScrollTrigger } from 'gsap/ScrollTrigger'
import { useI18n } from 'vue-i18n'
import { articles } from '@/data/articles'
import TagChip from '@/components/ui/TagChip.vue'
import TocItem from '@/components/ui/TocItem.vue'
import Breadcrumb from '@/components/ui/Breadcrumb.vue'

const { t, locale } = useI18n()
const route = useRoute()
const slug = computed(() => route.params.slug as string)

const article = computed(() => articles.find(a => a.slug === slug.value))
const articleIndex = computed(() => articles.findIndex(a => a.slug === slug.value))
const prevArticle = computed(() => {
  const idx = articleIndex.value
  if (idx <= 0) return null
  return articles[idx - 1]
})
const nextArticle = computed(() => {
  const idx = articleIndex.value
  if (idx === -1 || idx >= articles.length - 1) return null
  return articles[idx + 1]
})

const sectionIds = ['section-intro', 'section-background', 'section-tech', 'section-implementation', 'section-performance', 'section-summary']
const sectionLabels = ['引言', '背景与动机', '技术方案', '实现细节', '性能优化', '复盘总结']
const activeIndex = ref(0)

const tocItems = computed(() =>
  sectionLabels.map((label, i) => ({ label, active: activeIndex.value === i }))
)

function scrollToSection(index: number) {
  const id = sectionIds[index]
  const el = document.getElementById(id)
  if (!el) return
  const navHeight = 80
  const top = el.getBoundingClientRect().top + window.scrollY - navHeight
  window.scrollTo({ top, behavior: 'smooth' })
}

let observer: IntersectionObserver | null = null

let ctx: gsap.Context | null = null

onMounted(() => {
  ctx = gsap.context(() => {
    const reduced = window.matchMedia('(prefers-reduced-motion: reduce)').matches
    if (reduced) return

    gsap.fromTo('.article-header > *', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.7, stagger: 0.08, ease: 'power3.out', delay: 0.1 })

    ScrollTrigger.create({
      trigger: '.article-body',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.article-body > *', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.6, stagger: 0.08, ease: 'power3.out' })
      }
    })

    ScrollTrigger.refresh()
  })

  // IntersectionObserver 实时高亮当前阅读位置
  const sections = sectionIds.map(id => document.getElementById(id)).filter(Boolean) as HTMLElement[]
  if (sections.length) {
    observer = new IntersectionObserver(
      (entries) => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            const idx = sectionIds.indexOf(entry.target.id)
            if (idx !== -1) activeIndex.value = idx
          }
        })
      },
      { rootMargin: '-80px 0px -60% 0px', threshold: 0 }
    )
    sections.forEach(el => observer!.observe(el))
  }
})

onUnmounted(() => {
  ctx?.revert()
  ctx = null
  observer?.disconnect()
  observer = null
})
</script>

<template>
  <div v-if="article" class="max-w-[1400px] mx-auto px-6 lg:px-10 py-20">
    <div class="flex flex-col lg:flex-row gap-12">
      <div class="flex-1 min-w-0">
        <Breadcrumb :items="[
          { label: '首页', to: '/' },
          { label: '博客', to: '/blog' },
          { label: locale === 'zh' ? article.title : (article.titleEn || article.title) }
        ]" class="mb-4" />
        <div class="article-header flex flex-col gap-4 mb-8">
          <span
            class="inline-flex items-center px-2.5 py-[3px] rounded-full text-[11px] font-semibold text-white w-fit"
            style="background: var(--color-primary);"
          >
            {{ locale === 'zh' ? article.category : (article.categoryEn || article.category) }}
          </span>
          <h1 class="text-4xl md:text-5xl font-bold tracking-tight" style="font-family: var(--font-display); color: var(--color-text-1);">
            {{ locale === 'zh' ? article.title : (article.titleEn || article.title) }}
          </h1>
          <div class="flex items-center gap-4 text-[13px]" style="color: var(--color-text-3);">
            <span class="flex items-center gap-1">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><use href="/icons/sprite.svg#user" /></svg>
              Wei
            </span>
            <span class="flex items-center gap-1">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><use href="/icons/sprite.svg#calendar" /></svg>
              {{ article.date }}
            </span>
            <span class="flex items-center gap-1">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><use href="/icons/sprite.svg#clock" /></svg>
              {{ article.readingTime }} {{ t('common.readingTime') }}
            </span>
          </div>
        </div>

        <div class="article-body flex flex-col gap-6 max-w-[800px]">
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            {{ locale === 'zh' ? article.summary : (article.summaryEn || article.summary) }}
          </p>

          <h2 id="section-intro" class="text-2xl font-bold mt-4 scroll-mt-24" style="font-family: var(--font-display); color: var(--color-text-1);">引言</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            在技术快速迭代的今天,每一个项目背后都有值得记录的经验与教训。本文将从实际场景出发,分享在 {{ article.category }} 方向的实践心得,希望能为遇到类似问题的开发者提供参考。
          </p>

          <h2 id="section-background" class="text-2xl font-bold mt-4 scroll-mt-24" style="font-family: var(--font-display); color: var(--color-text-1);">背景与动机</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            在项目启动之初,团队面临的核心挑战是如何在有限资源下交付高质量的产品。经过多轮技术选型与原型验证,最终确定了以 {{ article.tags.join('、') }} 为核心的技术栈。
          </p>

          <h2 id="section-tech" class="text-2xl font-bold mt-4 scroll-mt-24" style="font-family: var(--font-display); color: var(--color-text-1);">技术方案</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            整体架构遵循"关注点分离"原则,将业务逻辑、数据层与表现层解耦。前端采用组件化开发模式,后端通过 RESTful API 提供数据服务。关键设计决策包括:
          </p>
          <ul class="flex flex-col gap-2 list-disc list-inside text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            <li>模块化边界清晰,降低耦合度</li>
            <li>类型安全贯穿全栈,减少运行时错误</li>
            <li>自动化测试覆盖核心业务流程</li>
          </ul>

          <h2 id="section-implementation" class="text-2xl font-bold mt-4 scroll-mt-24" style="font-family: var(--font-display); color: var(--color-text-1);">实现细节</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            在具体实现过程中,我们遇到了一些预料之外的难题。例如,性能瓶颈的排查需要结合浏览器 DevTools 与后端监控数据;跨组件状态管理在复杂场景下的选择也经历了从简单到复杂的演进。
          </p>

          <h2 id="section-performance" class="text-2xl font-bold mt-4 scroll-mt-24" style="font-family: var(--font-display); color: var(--color-text-1);">性能优化</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            性能优化是一个持续的过程。从首屏加载到交互响应,每个环节都有可优化的空间。常见的优化手段包括代码分割、懒加载、缓存策略以及资源压缩。
          </p>

          <h2 id="section-summary" class="text-2xl font-bold mt-4 scroll-mt-24" style="font-family: var(--font-display); color: var(--color-text-1);">复盘总结</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            回顾整个项目周期,最大的收获不是技术栈的掌握,而是问题解决能力的提升。技术会过时,但分析问题的思维方式会持续受益。
          </p>
        </div>

        <div class="flex flex-wrap gap-2 mt-10 pt-6 border-t" style="border-color: var(--color-border);">
          <span class="text-[13px] mr-2" style="color: var(--color-text-3);">{{ t('common.tags') }}:</span>
          <TagChip v-for="tag in article.tags" :key="tag" :label="tag" />
        </div>

        <div class="flex items-center justify-between mt-10 pt-8 border-t" style="border-color: var(--color-border);">
          <RouterLink
            v-if="prevArticle"
            :to="`/blog/${prevArticle.slug}`"
            class="flex flex-col gap-1 transition-opacity hover:opacity-70"
          >
            <span class="text-[11px]" style="color: var(--color-text-3);">← {{ t('common.prev') }}</span>
            <span class="text-sm font-medium" style="color: var(--color-text-1);">{{ locale === 'zh' ? prevArticle.title : (prevArticle.titleEn || prevArticle.title) }}</span>
          </RouterLink>
          <span v-else />
          <RouterLink
            v-if="nextArticle"
            :to="`/blog/${nextArticle.slug}`"
            class="flex flex-col gap-1 items-end transition-opacity hover:opacity-70"
          >
            <span class="text-[11px]" style="color: var(--color-text-3);">{{ t('common.next') }} →</span>
            <span class="text-sm font-medium" style="color: var(--color-text-1);">{{ locale === 'zh' ? nextArticle.title : (nextArticle.titleEn || nextArticle.title) }}</span>
          </RouterLink>
          <span v-else />
        </div>
      </div>

      <div class="w-full lg:w-[280px] flex-shrink-0">
        <div
          class="rounded-xl p-5 flex flex-col gap-3 sticky top-24"
          style="background: var(--color-card); border: 1px solid var(--color-border);"
        >
          <h4 class="text-sm font-semibold" style="color: var(--color-text-1);">目录</h4>
          <div class="flex flex-col gap-1">
            <TocItem
              v-for="(item, idx) in tocItems"
              :key="item.label"
              :label="item.label"
              :active="item.active"
              @click="scrollToSection(idx)"
            />
          </div>
        </div>
      </div>
    </div>
  </div>

  <div v-else class="max-w-[1400px] mx-auto px-6 py-20 text-center">
    <h1 class="text-4xl font-bold" style="color: var(--color-text-1);">{{ t('common.notFound') }}</h1>
    <RouterLink to="/blog" class="text-sm mt-4 inline-block" style="color: var(--color-primary);">
      {{ t('common.backToList') }}
    </RouterLink>
  </div>
</template>
