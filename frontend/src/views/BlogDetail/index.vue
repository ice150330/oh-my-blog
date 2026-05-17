<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { articles } from '@/data/articles'
import TagChip from '@/components/ui/TagChip.vue'
import TocItem from '@/components/ui/TocItem.vue'

const route = useRoute()
const slug = computed(() => route.params.slug as string)

const article = computed(() =>
  articles.find(a => a.slug === slug.value)
)

const articleIndex = computed(() =>
  articles.findIndex(a => a.slug === slug.value)
)

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

const tocItems = [
  { label: '引言', active: false },
  { label: '背景与动机', active: false },
  { label: '技术方案', active: false },
  { label: '实现细节', active: true },
  { label: '性能优化', active: false },
  { label: '复盘总结', active: false },
]
</script>

<template>
  <div v-if="article" class="max-w-7xl mx-auto px-6 py-12">
    <div class="flex flex-col lg:flex-row gap-12">
      <!-- Main Content -->
      <div class="flex-1 min-w-0">
        <!-- Header -->
        <div class="flex flex-col gap-4 mb-8">
          <span
            class="inline-flex items-center px-2.5 py-[3px] rounded-full text-[11px] font-semibold text-white w-fit"
            style="background: var(--color-primary);"
          >
            {{ article.category }}
          </span>
          <h1
            class="text-4xl md:text-5xl font-bold tracking-tight"
            style="font-family: var(--font-display); color: var(--color-text-1);"
          >
            {{ article.title }}
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
              {{ article.readingTime }} 分钟阅读
            </span>
          </div>
        </div>

        <!-- Body -->
        <div class="flex flex-col gap-6 max-w-[800px]">
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            {{ article.summary }}
          </p>

          <h2 class="text-2xl font-bold mt-4" style="font-family: var(--font-display); color: var(--color-text-1);">引言</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            在技术快速迭代的今天,每一个项目背后都有值得记录的经验与教训。本文将从实际场景出发,分享在 {{ article.category }} 方向的实践心得,希望能为遇到类似问题的开发者提供参考。
          </p>

          <h2 class="text-2xl font-bold mt-4" style="font-family: var(--font-display); color: var(--color-text-1);">背景与动机</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            在项目启动之初,团队面临的核心挑战是如何在有限资源下交付高质量的产品。经过多轮技术选型与原型验证,最终确定了以 {{ article.tags.join('、') }} 为核心的技术栈。
          </p>

          <h2 class="text-2xl font-bold mt-4" style="font-family: var(--font-display); color: var(--color-text-1);">技术方案</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            整体架构遵循"关注点分离"原则,将业务逻辑、数据层与表现层解耦。前端采用组件化开发模式,后端通过 RESTful API 提供数据服务。关键设计决策包括:
          </p>
          <ul class="flex flex-col gap-2 list-disc list-inside text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            <li>模块化边界清晰,降低耦合度</li>
            <li>类型安全贯穿全栈,减少运行时错误</li>
            <li>自动化测试覆盖核心业务流程</li>
          </ul>

          <h2 class="text-2xl font-bold mt-4" style="font-family: var(--font-display); color: var(--color-text-1);">实现细节</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            在具体实现过程中,我们遇到了一些预料之外的难题。例如,性能瓶颈的排查需要结合浏览器 DevTools 与后端监控数据;跨组件状态管理在复杂场景下的选择也经历了从简单到复杂的演进。
          </p>

          <h2 class="text-2xl font-bold mt-4" style="font-family: var(--font-display); color: var(--color-text-1);">性能优化</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            性能优化是一个持续的过程。从首屏加载到交互响应,每个环节都有可优化的空间。常见的优化手段包括代码分割、懒加载、缓存策略以及资源压缩。
          </p>

          <h2 class="text-2xl font-bold mt-4" style="font-family: var(--font-display); color: var(--color-text-1);">复盘总结</h2>
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            回顾整个项目周期,最大的收获不是技术栈的掌握,而是问题解决能力的提升。技术会过时,但分析问题的思维方式会持续受益。
          </p>
        </div>

        <!-- Tags -->
        <div class="flex flex-wrap gap-2 mt-10 pt-6 border-t" style="border-color: var(--color-border);">
          <span class="text-[13px] mr-2" style="color: var(--color-text-3);">标签:</span>
          <TagChip v-for="tag in article.tags" :key="tag" :label="tag" />
        </div>

        <!-- Prev / Next -->
        <div class="flex items-center justify-between mt-10 pt-8 border-t" style="border-color: var(--color-border);">
          <RouterLink
            v-if="prevArticle"
            :to="`/blog/${prevArticle.slug}`"
            class="flex flex-col gap-1 transition-opacity hover:opacity-70"
          >
            <span class="text-[11px]" style="color: var(--color-text-3);">← 上一篇</span>
            <span class="text-sm font-medium" style="color: var(--color-text-1);">{{ prevArticle.title }}</span>
          </RouterLink>
          <span v-else />
          <RouterLink
            v-if="nextArticle"
            :to="`/blog/${nextArticle.slug}`"
            class="flex flex-col gap-1 items-end transition-opacity hover:opacity-70"
          >
            <span class="text-[11px]" style="color: var(--color-text-3);">下一篇 →</span>
            <span class="text-sm font-medium" style="color: var(--color-text-1);">{{ nextArticle.title }}</span>
          </RouterLink>
          <span v-else />
        </div>
      </div>

      <!-- Sidebar TOC -->
      <div class="w-full lg:w-[280px] flex-shrink-0">
        <div
          class="rounded-xl p-5 flex flex-col gap-3 sticky top-24"
          style="background: var(--color-card); border: 1px solid var(--color-border);"
        >
          <h4 class="text-sm font-semibold" style="color: var(--color-text-1);">目录</h4>
          <div class="flex flex-col gap-1">
            <TocItem
              v-for="item in tocItems"
              :key="item.label"
              :label="item.label"
              :active="item.active"
            />
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Not Found -->
  <div v-else class="max-w-7xl mx-auto px-6 py-20 text-center">
    <h1 class="text-4xl font-bold" style="color: var(--color-text-1);">文章未找到</h1>
    <RouterLink to="/blog" class="text-sm mt-4 inline-block" style="color: var(--color-primary);">
      返回博客列表 →
    </RouterLink>
  </div>
</template>
