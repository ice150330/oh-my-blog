<script setup lang="ts">
import { skills, techCategories, stats } from '@/data/techStack'
import SectionTitle from '@/components/ui/SectionTitle.vue'
import SkillBar from '@/components/ui/SkillBar.vue'
import StatItem from '@/components/ui/StatItem.vue'
import IconBadge from '@/components/ui/IconBadge.vue'
</script>

<template>
  <div class="max-w-7xl mx-auto px-6 py-12">
    <!-- Header -->
    <div class="flex flex-col items-center text-center mb-16">
      <SectionTitle en="Tech Stack" zh="技术栈" />
      <p class="text-base mt-3" style="font-family: var(--font-accent); color: var(--color-text-2);">
        以前端为主轴,向数据可视化、AI 工程、算法仿真自然延伸
      </p>
    </div>

    <!-- Radar + Skills -->
    <div class="flex flex-col lg:flex-row items-center gap-12 mb-16">
      <!-- Radar Placeholder -->
      <div class="w-full lg:w-[560px] aspect-square rounded-3xl flex items-center justify-center relative overflow-hidden" style="background: var(--color-bg-elevated); border: 1px solid var(--color-border);">
        <!-- Decorative rings -->
        <div class="absolute w-[360px] h-[360px] rounded-full border" style="border-color: var(--color-border);" />
        <div class="absolute w-[240px] h-[240px] rounded-full border" style="border-color: var(--color-border);" />
        <div class="absolute w-[120px] h-[120px] rounded-full border" style="border-color: var(--color-border);" />
        <!-- Center label -->
        <div class="flex flex-col items-center gap-2 z-10">
          <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" style="color: var(--color-primary);"><use href="/icons/sprite.svg#chart-column" /></svg>
          <span class="text-sm font-medium" style="color: var(--color-text-3); font-family: var(--font-code);">Skill Radar</span>
        </div>
      </div>

      <!-- Skills Column -->
      <div class="flex-1 w-full">
        <h4 class="text-[13px] font-bold tracking-widest mb-5" style="color: var(--color-text-3); font-family: var(--font-display);">
          6 大能力维度
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

    <!-- Category Grid -->
    <div class="mb-16">
      <h2 class="text-4xl font-bold tracking-tight mb-2" style="font-family: var(--font-display); color: var(--color-text-1);">
        按领域分类
      </h2>
      <p class="text-sm mb-8" style="font-family: var(--font-accent); color: var(--color-text-2);">
        按领域细分的技术列表 · 点击标签查看相关项目与文章
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

    <!-- Stats Bar -->
    <div class="grid grid-cols-2 md:grid-cols-4 gap-6">
      <StatItem
        v-for="stat in stats"
        :key="stat.label"
        :value="stat.value"
        :label="stat.label"
      />
    </div>
  </div>
</template>
