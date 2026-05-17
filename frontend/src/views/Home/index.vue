<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { gsap } from 'gsap'
import { ScrollTrigger } from 'gsap/ScrollTrigger'
import { projects, featuredProjectIds } from '@/data/projects'
import { articles } from '@/data/articles'
import IconBadge from '@/components/ui/IconBadge.vue'
import SectionTitle from '@/components/ui/SectionTitle.vue'
import ArticleCard from '@/components/ui/ArticleCard.vue'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'
import GhostButton from '@/components/ui/GhostButton.vue'
import TagChip from '@/components/ui/TagChip.vue'
import CategoryBadge from '@/components/ui/CategoryBadge.vue'

const featured = computed(() =>
  featuredProjectIds.map(id => projects.find(p => p.id === id)!).filter(Boolean)
)

const activeIndex = ref(0)
const currentProject = computed(() => featured.value[activeIndex.value])

const latestArticles = articles.slice(0, 3)

// 自动轮播
let autoPlayTimer: ReturnType<typeof setInterval> | null = null
const startAutoPlay = () => {
  if (autoPlayTimer) return
  autoPlayTimer = setInterval(() => {
    activeIndex.value = (activeIndex.value + 1) % featured.value.length
  }, 5000)
}
const stopAutoPlay = () => {
  if (autoPlayTimer) {
    clearInterval(autoPlayTimer)
    autoPlayTimer = null
  }
}

let ctx: gsap.Context | null = null

onMounted(() => {
  startAutoPlay()
  ctx = gsap.context(() => {
    const reduced = window.matchMedia('(prefers-reduced-motion: reduce)').matches
    if (reduced) return

    // Hero stagger entrance
    gsap.fromTo('.hero-badge', { y: 20, opacity: 0 }, { y: 0, opacity: 1, duration: 0.6, delay: 0.1, ease: 'power3.out' })
    gsap.fromTo('.hero-title', { y: 40, opacity: 0 }, { y: 0, opacity: 1, duration: 0.8, delay: 0.2, ease: 'power3.out' })
    gsap.fromTo('.hero-subtitle', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.7, delay: 0.35, ease: 'power3.out' })
    gsap.fromTo('.hero-desc', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.7, delay: 0.45, ease: 'power3.out' })
    gsap.fromTo('.hero-cta', { y: 20, opacity: 0 }, { y: 0, opacity: 1, duration: 0.6, delay: 0.6, ease: 'power3.out' })

    // Brief Intro cards stagger
    ScrollTrigger.create({
      trigger: '.brief-intro',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.brief-intro .intro-card', { y: 40, opacity: 0 }, { y: 0, opacity: 1, duration: 0.7, stagger: 0.12, ease: 'power3.out' })
      }
    })

    // Featured section
    ScrollTrigger.create({
      trigger: '.featured-section',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.featured-section .featured-panel', { y: 50, opacity: 0 }, { y: 0, opacity: 1, duration: 0.8, stagger: 0.15, ease: 'power3.out' })
      }
    })

    // Article cards
    ScrollTrigger.create({
      trigger: '.latest-section',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.latest-section .article-item', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.6, stagger: 0.1, ease: 'power3.out' })
      }
    })

    // CTA Banner
    ScrollTrigger.create({
      trigger: '.cta-banner',
      start: 'top 85%',
      once: true,
      onEnter: () => {
        gsap.fromTo('.cta-banner > *', { y: 30, opacity: 0 }, { y: 0, opacity: 1, duration: 0.7, stagger: 0.1, ease: 'power3.out' })
      }
    })

    // 确保 ScrollTrigger 位置计算正确
    ScrollTrigger.refresh()
  })
})

onUnmounted(() => {
  stopAutoPlay()
  ctx?.revert()
  ctx = null
})
</script>

<template>
  <div>
    <!-- Hero -->
    <section
      class="relative flex flex-col justify-center gap-6 min-h-[85vh] px-6"
      style="background: radial-gradient(circle at 85% 30%, var(--mesh-tint-1), transparent 85%);"
    >
      <div class="max-w-[1400px] mx-auto w-full px-6 lg:px-10">
        <div
          class="hero-badge inline-flex items-center gap-2 px-3 py-1 rounded-full text-xs font-medium"
          style="background: var(--color-primary-soft); color: var(--color-primary);"
        >
          <span class="w-1.5 h-1.5 rounded-full" style="background: var(--color-primary);" />
          你好世界,正在创造中
        </div>
        <h1
          class="hero-title text-7xl md:text-8xl font-bold tracking-tight mt-4"
          style="font-family: var(--font-display); color: var(--color-text-1);"
        >
          你好,我是 Wei。
        </h1>
        <p
          class="hero-subtitle text-2xl md:text-[28px] mt-4"
          style="font-family: var(--font-accent); color: var(--color-text-2);"
        >
          数据工程师 · 全栈开发者
        </p>
        <p
          class="hero-desc text-[15px] leading-relaxed mt-4 max-w-[680px]"
          style="color: var(--color-text-2);"
        >
          我用代码连接数据与产品,在前端、可视化与 AI 工程之间寻找简洁优雅的解法。这里记录我的项目、技术与思考。
        </p>
        <div class="hero-cta flex items-center gap-3 mt-8">
          <PrimaryButton text="查看项目" to="/projects" />
          <GhostButton text="了解更多" to="/about" />
        </div>
      </div>
    </section>

    <!-- Brief Intro -->
    <section class="brief-intro max-w-[1400px] mx-auto px-6 lg:px-10 py-20">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
        <div class="intro-card flex flex-col gap-3 transition-transform duration-300 hover:-translate-y-1">
          <IconBadge icon="chart-column" />
          <h3 class="text-base font-semibold" style="color: var(--color-text-1);">数据可视化</h3>
          <p class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">
            用 ECharts 与 D3 把复杂数据讲清楚
          </p>
        </div>
        <div class="flex flex-col gap-3">
          <IconBadge icon="brain-circuit" />
          <h3 class="text-base font-semibold" style="color: var(--color-text-1);">AI 工程</h3>
          <p class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">
            RAG、Agent 与 LLM 应用落地实践
          </p>
        </div>
        <div class="flex flex-col gap-3">
          <IconBadge icon="code" />
          <h3 class="text-base font-semibold" style="color: var(--color-text-1);">全栈开发</h3>
          <p class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">
            Nuxt 3、Spring Boot 与现代工程化体系
          </p>
        </div>
      </div>
    </section>

    <!-- Featured Projects Carousel -->
    <section class="featured-section max-w-[1400px] mx-auto px-6 lg:px-10 py-20">
      <div class="flex items-end justify-between mb-8">
        <SectionTitle en="Featured" zh="精选项目" />
        <RouterLink
          to="/projects"
          class="text-sm font-medium flex items-center gap-1 transition-opacity hover:opacity-80"
          style="color: var(--color-primary);"
        >
          查看全部
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <use href="/icons/sprite.svg#arrow-right" />
          </svg>
        </RouterLink>
      </div>

      <div
        class="flex flex-col lg:flex-row gap-8 h-auto lg:h-[540px]"
        @mouseenter="stopAutoPlay"
        @mouseleave="startAutoPlay"
      >
        <!-- Left Panel -->
        <div class="featured-panel flex flex-col justify-center gap-5 w-full lg:w-[520px]">
          <span class="text-[13px] font-normal" style="font-family: var(--font-code); color: var(--color-text-3);">
            {{ String(activeIndex + 1).padStart(2, '0') }} / {{ String(featured.length).padStart(2, '0') }}
          </span>
          <h3
            class="text-4xl font-bold tracking-tight"
            style="font-family: var(--font-display); color: var(--color-text-1);"
          >
            {{ currentProject.title }}
          </h3>
          <CategoryBadge :category="currentProject.category" />
          <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            {{ currentProject.description }}
          </p>
          <div class="flex flex-wrap gap-2">
            <TagChip
              v-for="tag in currentProject.tags"
              :key="tag"
              :label="tag"
            />
          </div>
          <div class="flex items-center gap-3">
            <PrimaryButton text="查看详情" :to="`/projects/${currentProject.slug}`" />
            <GhostButton text="源码 →" :href="currentProject.repoUrl || '#'" />
          </div>
          <div class="flex items-center justify-between mt-2">
            <div class="flex items-center gap-2">
              <button
                v-for="(_, idx) in featured"
                :key="idx"
                class="h-1.5 rounded-full transition-all"
                :class="idx === activeIndex ? 'w-6' : 'w-1.5'"
                :style="{ background: idx === activeIndex ? 'var(--color-primary)' : 'var(--color-border)' }"
                @click="activeIndex = idx"
              />
            </div>
            <div class="flex items-center gap-2">
              <button
                class="w-9 h-9 rounded-lg flex items-center justify-center transition-colors"
                style="background: var(--color-bg-elevated); border: 1px solid var(--color-border);"
                @click="activeIndex = (activeIndex - 1 + featured.length) % featured.length"
              >
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="color: var(--color-text-2);">
                  <use href="/icons/sprite.svg#chevron-left" />
                </svg>
              </button>
              <button
                class="w-9 h-9 rounded-lg flex items-center justify-center transition-colors"
                style="background: var(--color-bg-elevated); border: 1px solid var(--color-border);"
                @click="activeIndex = (activeIndex + 1) % featured.length"
              >
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="color: var(--color-text-2);">
                  <use href="/icons/sprite.svg#chevron-right" />
                </svg>
              </button>
            </div>
          </div>
        </div>

        <!-- Right Panel -->
        <div class="featured-panel flex-1 flex flex-col justify-center gap-4 min-h-[400px]">
          <div
            class="flex-1 rounded-2xl flex flex-col items-center justify-center gap-4 min-h-[320px] transition-all"
            style="
              background: radial-gradient(circle at 50% 50%, var(--mesh-tint-1), var(--mesh-tint-2));
              border: 1px solid var(--color-border);
              box-shadow: 0 8px 32px var(--glow-primary);
            "
          >
            <svg width="96" height="96" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" style="color: var(--color-primary);">
              <use href="/icons/sprite.svg#brain-circuit" />
            </svg>
            <span
              class="text-2xl font-bold"
              style="font-family: var(--font-display); color: var(--color-text-1);"
            >
              {{ currentProject.title }}
            </span>
          </div>
          <div class="flex items-center justify-center gap-3">
            <span
              class="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs"
              style="background: var(--color-card); border: 1px solid var(--color-border); color: var(--color-text-2); font-family: var(--font-code);"
            >
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><use href="/icons/sprite.svg#star" /></svg>
              {{ currentProject.stars || 0 }} 星标
            </span>
            <span
              class="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs"
              style="background: var(--color-card); border: 1px solid var(--color-border); color: var(--color-text-2); font-family: var(--font-code);"
            >
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><use href="/icons/sprite.svg#code" /></svg>
              {{ currentProject.techCount || 0 }} 技术栈
            </span>
            <span
              class="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs"
              style="background: var(--color-card); border: 1px solid var(--color-border); color: var(--color-text-2); font-family: var(--font-code);"
            >
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><use href="/icons/sprite.svg#folder" /></svg>
              全栈项目
            </span>
          </div>
        </div>
      </div>
    </section>

    <!-- Latest Articles -->
    <section class="latest-section max-w-[1400px] mx-auto px-6 lg:px-10 py-20">
      <div class="flex items-end justify-between mb-8">
        <SectionTitle en="Latest" zh="最新文章" />
        <RouterLink
          to="/blog"
          class="text-sm font-medium flex items-center gap-1 transition-opacity hover:opacity-80"
          style="color: var(--color-primary);"
        >
          查看全部
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <use href="/icons/sprite.svg#arrow-right" />
          </svg>
        </RouterLink>
      </div>
      <div class="flex flex-col gap-6">
        <ArticleCard
          v-for="article in latestArticles"
          :key="article.slug"
          :article="article"
          class="article-item"
        />
      </div>
    </section>

    <!-- CTA Banner -->
    <section
      class="cta-banner flex flex-col items-center justify-center gap-4 text-center min-h-[280px] px-6"
      style="background: radial-gradient(circle at 50% 50%, var(--mesh-tint-1), var(--mesh-tint-2));"
    >
      <h2
        class="text-4xl md:text-5xl font-bold tracking-tight"
        style="font-family: var(--font-display); color: var(--color-text-1);"
      >
        一起做点有意思的事吧。
      </h2>
      <p class="text-lg" style="font-family: var(--font-accent); color: var(--color-text-2);">
        如果你也喜欢把复杂的事情做得简单,欢迎联系。
      </p>
      <div class="flex items-center gap-3 mt-2">
        <PrimaryButton text="联系我" to="/about" />
        <GhostButton text="查看简历" to="/about" />
      </div>
    </section>
  </div>
</template>
