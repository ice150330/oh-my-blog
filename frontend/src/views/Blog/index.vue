<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { gsap } from 'gsap'
import { ScrollTrigger } from 'gsap/ScrollTrigger'
import { useI18n } from 'vue-i18n'
import { articles } from '@/data/articles'
import ArticleCard from '@/components/ui/ArticleCard.vue'
import TagChip from '@/components/ui/TagChip.vue'

const { t } = useI18n()

const filters = computed(() => [
  t('blog.filterAll'),
  t('blog.filterFrontend'),
  t('blog.filterAiData'),
  t('blog.filterAlgo'),
  t('blog.filterThought'),
])
const activeFilter = ref(t('blog.filterAll'))

const filteredArticles = computed(() => {
  if (activeFilter.value === t('blog.filterAll')) return articles
  const map: Record<string, string[]> = {
    [t('blog.filterFrontend')]: ['前端工程化'],
    [t('blog.filterAiData')]: ['AI 智能体', '数据可视化'],
    [t('blog.filterAlgo')]: ['算法 / 仿真'],
    [t('blog.filterThought')]: ['思考 / 复盘'],
  }
  const cats = map[activeFilter.value] || []
  return articles.filter(a => cats.some(c => a.category.includes(c)))
})

const allTags = Array.from(new Set(articles.flatMap(a => a.tags)))

let ctx: gsap.Context | null = null

onMounted(() => {
  ctx = gsap.context(() => {
    const reduced = window.matchMedia('(prefers-reduced-motion: reduce)').matches
    if (reduced) return

    gsap.fromTo('.blog-header', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.7, ease: 'power3.out' })
    gsap.fromTo('.filter-tab', { y: 20, opacity: 0 }, { y: 0, opacity: 1, duration: 0.5, stagger: 0.06, ease: 'power3.out', delay: 0.2 })

    ScrollTrigger.create({
      trigger: '.article-list',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.article-list .article-card', { y: 40, opacity: 0 }, { y: 0, opacity: 1, duration: 0.7, stagger: 0.1, ease: 'power3.out' })
      }
    })

    ScrollTrigger.create({
      trigger: '.blog-sidebar',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.blog-sidebar > div', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.6, stagger: 0.12, ease: 'power3.out' })
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
    <div class="blog-header mb-10">
      <h1 class="text-5xl md:text-6xl font-bold tracking-tight" style="font-family: var(--font-display); color: var(--color-text-1);">
        {{ t('blog.title') }}
      </h1>
      <p class="text-lg mt-2" style="font-family: var(--font-accent); color: var(--color-text-2);">
        {{ t('blog.subtitle') }}
      </p>
    </div>

    <div class="flex flex-col lg:flex-row gap-12">
      <div class="flex-1 min-w-0">
        <div class="flex flex-wrap gap-2 mb-8">
          <button
            v-for="f in filters"
            :key="f"
            class="filter-tab px-4 py-2 rounded-lg text-[13px] font-medium transition-all cursor-pointer"
            :class="activeFilter === f ? 'text-white' : 'border text-[var(--color-text-2)]'"
            :style="activeFilter === f ? { background: 'var(--color-primary)' } : { borderColor: 'var(--color-border)' }"
            @click="activeFilter = f"
          >
            {{ f }}
          </button>
        </div>

        <div class="article-list flex flex-col gap-6">
          <ArticleCard
            v-for="article in filteredArticles"
            :key="article.slug"
            :article="article"
            class="article-card"
          />
        </div>

        <div class="flex items-center justify-center gap-2 mt-8">
          <button class="w-10 h-10 rounded-lg flex items-center justify-center border" style="border-color: var(--color-border);">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="color: var(--color-text-2);"><use href="/icons/sprite.svg#chevron-left" /></svg>
          </button>
          <button class="w-10 h-10 rounded-lg flex items-center justify-center text-white text-sm font-semibold" style="background: var(--color-primary);">1</button>
          <button class="w-10 h-10 rounded-lg flex items-center justify-center border text-sm font-medium" style="border-color: var(--color-border); color: var(--color-text-2);">2</button>
          <button class="w-10 h-10 rounded-lg flex items-center justify-center border text-sm font-medium" style="border-color: var(--color-border); color: var(--color-text-2);">3</button>
          <span class="w-10 h-10 flex items-center justify-center text-sm" style="color: var(--color-text-3);">…</span>
          <button class="w-10 h-10 rounded-lg flex items-center justify-center border" style="border-color: var(--color-border);">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="color: var(--color-text-2);"><use href="/icons/sprite.svg#chevron-right" /></svg>
          </button>
        </div>
      </div>

      <div class="blog-sidebar w-full lg:w-[376px] flex flex-col gap-6">
        <div class="rounded-xl p-5 flex flex-col gap-3" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <h4 class="text-sm font-semibold" style="color: var(--color-text-1);">{{ t('blog.search') }}</h4>
          <div class="flex items-center gap-2 px-3 py-2 rounded-lg" style="background: var(--color-bg-elevated); border: 1px solid var(--color-border);">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="color: var(--color-text-3);"><use href="/icons/sprite.svg#search" /></svg>
            <input type="text" :placeholder="t('blog.searchPlaceholder')" class="bg-transparent text-sm outline-none w-full" style="color: var(--color-text-1);" />
          </div>
        </div>

        <div class="rounded-xl p-5 flex flex-col gap-3" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <h4 class="text-sm font-semibold" style="color: var(--color-text-1);">{{ t('blog.hotTags') }}</h4>
          <div class="flex flex-wrap gap-2">
            <TagChip v-for="tag in allTags.slice(0, 8)" :key="tag" :label="tag" />
          </div>
        </div>

        <div class="rounded-xl p-5 flex flex-col gap-3" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <h4 class="text-sm font-semibold" style="color: var(--color-text-1);">{{ t('blog.archive') }}</h4>
          <div class="flex flex-col gap-2">
            <span v-for="m in ['2026-05', '2026-04', '2026-03', '2026-02']" :key="m" class="text-sm cursor-pointer hover:text-[var(--color-primary)] transition-colors" style="color: var(--color-text-2);">
              {{ m }} ({{ articles.filter(a => a.date.startsWith(m)).length }})
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
