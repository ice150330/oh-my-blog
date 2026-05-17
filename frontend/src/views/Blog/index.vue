<script setup lang="ts">
import { ref, computed } from 'vue'
import { articles } from '@/data/articles'
import ArticleCard from '@/components/ui/ArticleCard.vue'
import TagChip from '@/components/ui/TagChip.vue'

const filters = ['全部', '前端', 'AI 数据', '算法', '思考']
const activeFilter = ref('全部')

const filteredArticles = computed(() => {
  if (activeFilter.value === '全部') return articles
  const map: Record<string, string[]> = {
    '前端': ['前端工程化'],
    'AI 数据': ['AI 智能体', '数据可视化'],
    '算法': ['算法 / 仿真'],
    '思考': ['思考 / 复盘'],
  }
  const cats = map[activeFilter.value] || []
  return articles.filter(a => cats.some(c => a.category.includes(c)))
})

const allTags = Array.from(new Set(articles.flatMap(a => a.tags)))
</script>

<template>
  <div class="max-w-7xl mx-auto px-6 py-12">
    <!-- Header -->
    <div class="mb-10">
      <h1 class="text-5xl md:text-6xl font-bold tracking-tight" style="font-family: var(--font-display); color: var(--color-text-1);">
        博客笔记
      </h1>
      <p class="text-lg mt-2" style="font-family: var(--font-accent); color: var(--color-text-2);">
        博客 · 技术随笔 · 项目复盘
      </p>
    </div>

    <div class="flex flex-col lg:flex-row gap-12">
      <!-- Article List -->
      <div class="flex-1 min-w-0">
        <!-- Filter Bar -->
        <div class="flex flex-wrap gap-2 mb-8">
          <button
            v-for="f in filters"
            :key="f"
            class="px-4 py-2 rounded-lg text-[13px] font-medium transition-all cursor-pointer"
            :class="activeFilter === f
              ? 'text-white'
              : 'border text-[var(--color-text-2)]'"
            :style="activeFilter === f
              ? { background: 'var(--color-primary)' }
              : { borderColor: 'var(--color-border)' }"
            @click="activeFilter = f"
          >
            {{ f }}
          </button>
        </div>

        <div class="flex flex-col gap-6">
          <ArticleCard
            v-for="article in filteredArticles"
            :key="article.slug"
            :article="article"
          />
        </div>

        <!-- Pagination -->
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

      <!-- Sidebar -->
      <div class="w-full lg:w-[376px] flex flex-col gap-6">
        <!-- Search -->
        <div class="rounded-xl p-5 flex flex-col gap-3" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <h4 class="text-sm font-semibold" style="color: var(--color-text-1);">搜索文章</h4>
          <div class="flex items-center gap-2 px-3 py-2 rounded-lg" style="background: var(--color-bg-elevated); border: 1px solid var(--color-border);">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="color: var(--color-text-3);"><use href="/icons/sprite.svg#search" /></svg>
            <input type="text" placeholder="搜索…" class="bg-transparent text-sm outline-none w-full" style="color: var(--color-text-1);" />
          </div>
        </div>

        <!-- Tags -->
        <div class="rounded-xl p-5 flex flex-col gap-3" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <h4 class="text-sm font-semibold" style="color: var(--color-text-1);">热门标签</h4>
          <div class="flex flex-wrap gap-2">
            <TagChip v-for="tag in allTags.slice(0, 8)" :key="tag" :label="tag" />
          </div>
        </div>

        <!-- Archive -->
        <div class="rounded-xl p-5 flex flex-col gap-3" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <h4 class="text-sm font-semibold" style="color: var(--color-text-1);">文章归档</h4>
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
