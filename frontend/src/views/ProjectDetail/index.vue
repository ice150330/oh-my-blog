<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { projects, categoryLabels } from '@/data/projects'
import CategoryBadge from '@/components/ui/CategoryBadge.vue'
import TagChip from '@/components/ui/TagChip.vue'
import GhostButton from '@/components/ui/GhostButton.vue'

const route = useRoute()
const slug = computed(() => route.params.slug as string)

const project = computed(() =>
  projects.find(p => p.slug === slug.value)
)

const projectIndex = computed(() =>
  projects.findIndex(p => p.slug === slug.value)
)

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
  return {
    background: `radial-gradient(circle at 50% 50%, ${c1}, ${c2})`,
  }
})
</script>

<template>
  <div v-if="project" class="flex flex-col">
    <!-- Cover -->
    <section
      class="relative flex flex-col items-center justify-center gap-4 text-center min-h-[480px] px-6"
      :style="coverStyle"
    >
      <CategoryBadge :category="project.category" />
      <h1
        class="text-5xl md:text-6xl font-bold tracking-tight"
        style="font-family: var(--font-display); color: var(--color-text-1);"
      >
        {{ project.title }}
      </h1>
      <p
        class="text-lg max-w-[640px]"
        style="color: var(--color-text-2);"
      >
        {{ project.description }}
      </p>
    </section>

    <!-- Meta Bar -->
    <div class="max-w-7xl mx-auto px-6 w-full py-6 flex flex-wrap items-center gap-4 border-b" style="border-color: var(--color-border);">
      <span class="text-[13px]" style="color: var(--color-text-3);">
        分类: {{ categoryLabels[project.category] }}
      </span>
      <span class="w-px h-4" style="background: var(--color-border);" />
      <span class="text-[13px]" style="color: var(--color-text-3);">
        {{ project.tags.length }} 项技术
      </span>
      <span class="w-px h-4" style="background: var(--color-border);" />
      <div class="flex flex-wrap gap-2">
        <TagChip v-for="tag in project.tags" :key="tag" :label="tag" />
      </div>
      <div class="flex-1" />
      <GhostButton v-if="project.repoUrl" text="源码 →" :href="project.repoUrl" />
    </div>

    <!-- Content -->
    <div class="max-w-7xl mx-auto px-6 w-full py-12 flex flex-col gap-12">
      <!-- Demo Preview -->
      <div class="flex flex-col gap-4">
        <h2 class="text-xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">Demo 预览</h2>
        <div
          class="w-full rounded-2xl flex flex-col items-center justify-center gap-4 min-h-[480px]"
          style="background: var(--color-bg-elevated); border: 2px dashed var(--color-border);"
        >
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" style="color: var(--color-text-3);">
            <use href="/icons/sprite.svg#monitor" />
          </svg>
          <span class="text-sm font-medium" style="color: var(--color-text-3);">Demo Preview</span>
          <span class="text-xs" style="color: var(--color-text-3);">iframe 嵌入区域 / 或子项目构建产物</span>
        </div>
      </div>

      <!-- Article Body -->
      <div class="max-w-[800px] mx-auto w-full flex flex-col gap-6">
        <h2 class="text-2xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">项目介绍</h2>
        <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
          {{ project.description }} 本项目从需求分析到最终交付,经历了多轮迭代与优化。以下是项目的主要技术要点与实现细节。
        </p>
        <h3 class="text-lg font-semibold mt-2" style="color: var(--color-text-1);">技术架构</h3>
        <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
          项目采用 {{ project.tags.join('、') }} 等核心技术栈构建。前端以 Vue 3 为框架,配合 TypeScript 提供类型安全;后端根据场景选择 Python FastAPI 或 Spring Boot;数据层结合关系型数据库与图数据库,实现复杂数据关系的高效查询。
        </p>
        <h3 class="text-lg font-semibold mt-2" style="color: var(--color-text-1);">核心亮点</h3>
        <ul class="flex flex-col gap-2 list-disc list-inside text-[15px] leading-relaxed" style="color: var(--color-text-2);">
          <li>模块化架构设计,支持功能按需加载与扩展</li>
          <li>数据可视化层采用 ECharts / D3.js,实现交互式图表</li>
          <li>工程化配置完善,包含 ESLint、Prettier、CI/CD 流水线</li>
          <li>响应式布局适配,覆盖桌面端与移动端场景</li>
        </ul>
        <h3 class="text-lg font-semibold mt-2" style="color: var(--color-text-1);">复盘与展望</h3>
        <p class="text-[15px] leading-relaxed" style="color: var(--color-text-2);">
          在开发过程中,最大的挑战是平衡功能复杂度与维护成本。通过持续重构与文档沉淀,最终达到了预期目标。未来计划引入更多自动化测试覆盖,并探索 AI 辅助编码在项目迭代中的应用。
        </p>
      </div>

      <!-- Tags -->
      <div class="max-w-[800px] mx-auto w-full flex flex-wrap gap-2 pt-6 border-t" style="border-color: var(--color-border);">
        <span class="text-[13px] mr-2" style="color: var(--color-text-3);">标签:</span>
        <TagChip v-for="tag in project.tags" :key="tag" :label="tag" />
      </div>

      <!-- Prev / Next -->
      <div class="max-w-[800px] mx-auto w-full flex items-center justify-between pt-8 border-t" style="border-color: var(--color-border);">
        <RouterLink
          v-if="prevProject"
          :to="`/projects/${prevProject.slug}`"
          class="flex flex-col gap-1 transition-opacity hover:opacity-70"
        >
          <span class="text-[11px]" style="color: var(--color-text-3);">← 上一篇</span>
          <span class="text-sm font-medium" style="color: var(--color-text-1);">{{ prevProject.title }}</span>
        </RouterLink>
        <span v-else />
        <RouterLink
          v-if="nextProject"
          :to="`/projects/${nextProject.slug}`"
          class="flex flex-col gap-1 items-end transition-opacity hover:opacity-70"
        >
          <span class="text-[11px]" style="color: var(--color-text-3);">下一篇 →</span>
          <span class="text-sm font-medium" style="color: var(--color-text-1);">{{ nextProject.title }}</span>
        </RouterLink>
        <span v-else />
      </div>
    </div>
  </div>

  <!-- Not Found -->
  <div v-else class="max-w-7xl mx-auto px-6 py-20 text-center">
    <h1 class="text-4xl font-bold" style="color: var(--color-text-1);">项目未找到</h1>
    <RouterLink to="/projects" class="text-sm mt-4 inline-block" style="color: var(--color-primary);">
      返回项目列表 →
    </RouterLink>
  </div>
</template>
