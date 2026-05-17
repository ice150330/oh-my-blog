<script setup lang="ts">
import { skills, timeline, stats } from '@/data/techStack'
import SectionTitle from '@/components/ui/SectionTitle.vue'
import SkillBar from '@/components/ui/SkillBar.vue'
import StatItem from '@/components/ui/StatItem.vue'
import IconBadge from '@/components/ui/IconBadge.vue'
import TimelineNode from '@/components/ui/TimelineNode.vue'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'
</script>

<template>
  <div class="max-w-7xl mx-auto px-6 py-12">
    <div class="flex flex-col lg:flex-row gap-12">
      <!-- Left Column -->
      <div class="w-full lg:w-[480px] flex flex-col gap-8">
        <!-- Avatar Card -->
        <div class="flex flex-col items-center gap-5 rounded-2xl p-8" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <div
            class="w-[200px] h-[200px] rounded-full flex items-center justify-center text-5xl font-bold"
            style="background: radial-gradient(circle at 40% 40%, var(--mesh-tint-1), var(--mesh-tint-2)); font-family: var(--font-display); color: var(--color-primary);"
          >
            W
          </div>
          <div class="flex flex-col items-center gap-1">
            <h1 class="text-2xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">Wei</h1>
            <p class="text-sm" style="color: var(--color-text-2);">数据工程师 · 全栈开发者</p>
            <p class="text-xs flex items-center gap-1 mt-1" style="color: var(--color-text-3);">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><use href="/icons/sprite.svg#map-pin" /></svg>
              中国 · 河南
            </p>
          </div>
          <div class="flex items-center gap-3">
            <a href="https://github.com" target="_blank" class="transition-opacity hover:opacity-70">
              <IconBadge icon="github" />
            </a>
            <a href="mailto:wei@example.com" class="transition-opacity hover:opacity-70">
              <IconBadge icon="mail" />
            </a>
            <a href="#" target="_blank" class="transition-opacity hover:opacity-70">
              <IconBadge icon="twitter" />
            </a>
            <a href="#" target="_blank" class="transition-opacity hover:opacity-70">
              <IconBadge icon="rss" />
            </a>
          </div>
        </div>

        <!-- Mini Stats -->
        <div class="grid grid-cols-2 gap-4 rounded-2xl p-6" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <StatItem v-for="stat in stats" :key="stat.label" :value="stat.value" :label="stat.label" />
        </div>

        <!-- CTA Card -->
        <div class="flex flex-col gap-4 rounded-2xl p-6" style="background: var(--color-card); border: 1px solid var(--color-border);">
          <h3 class="text-base font-semibold" style="color: var(--color-text-1);">一起合作?</h3>
          <p class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">
            如果你有任何有趣的项目想法,或者只是想聊聊技术,欢迎随时联系。
          </p>
          <PrimaryButton text="发送邮件" href="mailto:wei@example.com" />
        </div>
      </div>

      <!-- Right Column -->
      <div class="flex-1 flex flex-col gap-12">
        <!-- Intro -->
        <div class="flex flex-col gap-4">
          <SectionTitle en="About Me" zh="关于我" />
          <div class="flex flex-col gap-3 text-[15px] leading-relaxed" style="color: var(--color-text-2);">
            <p>
              你好,我是 Wei,一名热爱数据与前端工程的全栈开发者。目前就读于商丘师范学院数据科学与大数据技术专业,主攻数据可视化与 AI 工程方向。
            </p>
            <p>
              我相信好的技术应该像水一样——透明、流动、无处不在。从 Python 数据分析到 Vue 3 全栈开发,从 ECharts 可视化到 LLM 智能体编排,我在数据与界面的交汇处寻找最优雅的解法。
            </p>
            <p>
              业余时间,我喜欢写博客复盘项目、给开源库提 PR、尝试各种新技术栈。这个博客是我所有项目的集中展示,也是我与世界对话的窗口。
            </p>
          </div>
        </div>

        <!-- Timeline -->
        <div class="flex flex-col gap-5">
          <h3 class="text-xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">经历时间线</h3>
          <div class="flex flex-col">
            <TimelineNode
              v-for="(event, idx) in timeline"
              :key="event.year"
              :year="event.year"
              :place="event.place"
              :title="event.title"
              :description="event.description"
              :last="idx === timeline.length - 1"
            />
          </div>
        </div>

        <!-- Skills -->
        <div class="flex flex-col gap-5">
          <h3 class="text-xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">技能评分</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-5">
            <SkillBar
              v-for="skill in skills"
              :key="skill.name"
              :name="skill.name"
              :level="skill.level"
            />
          </div>
        </div>

        <!-- Values -->
        <div class="flex flex-col gap-5">
          <h3 class="text-xl font-bold" style="font-family: var(--font-display); color: var(--color-text-1);">价值观</h3>
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div class="flex flex-col gap-2 rounded-xl p-5" style="background: var(--color-card); border: 1px solid var(--color-border);">
              <IconBadge icon="zap" />
              <span class="text-sm font-semibold" style="color: var(--color-text-1);">简洁至上</span>
              <span class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">删除一切不必要的复杂度,直到无法再删。</span>
            </div>
            <div class="flex flex-col gap-2 rounded-xl p-5" style="background: var(--color-card); border: 1px solid var(--color-border);">
              <IconBadge icon="eye" />
              <span class="text-sm font-semibold" style="color: var(--color-text-1);">数据驱动</span>
              <span class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">用数据说话,用可视化让数据自己会说话。</span>
            </div>
            <div class="flex flex-col gap-2 rounded-xl p-5" style="background: var(--color-card); border: 1px solid var(--color-border);">
              <IconBadge icon="rocket" />
              <span class="text-sm font-semibold" style="color: var(--color-text-1);">持续迭代</span>
              <span class="text-[13px] leading-relaxed" style="color: var(--color-text-2);">快速构建、快速验证、快速优化。</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
