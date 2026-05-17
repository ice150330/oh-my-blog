<script setup lang="ts">
import { ref, computed } from 'vue'
import { projects } from '@/data/projects'
import SectionTitle from '@/components/ui/SectionTitle.vue'
import ProjectCard from '@/components/ui/ProjectCard.vue'
import CategoryBadge from '@/components/ui/CategoryBadge.vue'
import GhostButton from '@/components/ui/GhostButton.vue'

const categories = [
  { key: 'all', label: '全部', count: projects.length },
  { key: 'ai', label: 'AI 智能体', count: projects.filter(p => p.category === 'ai').length },
  { key: 'data', label: '数据分析', count: projects.filter(p => p.category === 'data').length },
  { key: 'business', label: '业务系统', count: projects.filter(p => p.category === 'business').length },
  { key: 'algo', label: '算法仿真', count: projects.filter(p => p.category === 'algo').length },
  { key: 'meta', label: '元项目', count: projects.filter(p => p.category === 'meta').length },
]

const activeCategory = ref('all')

const filteredProjects = computed(() => {
  if (activeCategory.value === 'all') return projects.filter(p => p.category !== 'meta')
  return projects.filter(p => p.category === activeCategory.value)
})

const metaProjects = computed(() => projects.filter(p => p.category === 'meta'))
</script>

<template>
  <div class="max-w-7xl mx-auto px-6 py-12">
    <!-- Header -->
    <div class="mb-8">
      <SectionTitle en="Projects" zh="项目集" />
      <p class="text-[15px] leading-relaxed mt-3 max-w-[760px]" style="color: var(--color-text-2);">
        {{ projects.length }} 个真实项目,从 AI 智能体、数据分析、业务系统到三维仿真,覆盖个人作品全景。
      </p>
    </div>

    <!-- Category Tabs -->
    <div class="flex flex-wrap gap-3 mb-8">
      <button
        v-for="cat in categories"
        :key="cat.key"
        class="px-4 py-1.5 rounded-full text-[13px] font-medium transition-all cursor-pointer"
        :class="activeCategory === cat.key
          ? 'text-white'
          : 'text-[var(--color-text-2)] border'"
        :style="activeCategory === cat.key
          ? { background: 'var(--color-primary)' }
          : { borderColor: 'var(--color-border)' }"
        @click="activeCategory = cat.key"
      >
        {{ cat.label }} · {{ cat.count }}
      </button>
    </div>

    <!-- Project Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <ProjectCard
        v-for="project in filteredProjects"
        :key="project.id"
        :project="project"
      />
    </div>

    <!-- Meta Projects Banners -->
    <div class="flex flex-col gap-6 mt-12">
      <div
        v-for="mp in metaProjects"
        :key="mp.id"
        class="flex flex-col md:flex-row items-center gap-8 md:gap-12 rounded-xl p-8 md:p-10"
        style="background: var(--color-card); border: 1px solid var(--color-border); box-shadow: 0 4px 16px var(--glow-primary);"
      >
        <div class="flex-1 flex flex-col gap-3">
          <CategoryBadge :category="mp.category" />
          <h3 class="text-2xl font-bold" style="font-family: var(--font-accent); color: var(--color-text-1);">
            {{ mp.title }}
          </h3>
          <p class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">
            {{ mp.description }}
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
          <GhostButton text="查看详情 →" :to="`/projects/${mp.slug}`" />
        </div>
      </div>
    </div>
  </div>
</template>