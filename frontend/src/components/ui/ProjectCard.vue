<script setup lang="ts">
import { ref } from 'vue'
import type { Project } from '@/types'
import CategoryBadge from './CategoryBadge.vue'
import TagChip from './TagChip.vue'
import { useTilt } from '@/composables/useTilt'

defineProps<{
  project: Project
}>()

const cardRef = ref<HTMLElement | null>(null)
const tiltStyle = useTilt(cardRef, 6)
</script>

<template>
  <RouterLink
    ref="cardRef"
    :to="`/projects/${project.slug}`"
    class="group flex flex-col rounded-xl overflow-hidden cursor-pointer"
    :style="[{ background: 'var(--color-card)', border: '1px solid var(--color-border)' }, tiltStyle]"
  >
    <div
      class="h-[200px] w-full relative flex items-start justify-start p-4 transition-transform duration-700 group-hover:scale-105"
      :style="{
        background: `radial-gradient(circle at 30% 40%, ${project.coverGradient[0]}, ${project.coverGradient[1]})`,
      }"
    >
      <div
        class="absolute inset-0 opacity-0 group-hover:opacity-100 transition-opacity duration-500"
        style="background: linear-gradient(to bottom, transparent 0%, var(--color-card) 100%);"
      />
      <CategoryBadge :category="project.category" />
    </div>
    <div class="flex flex-col gap-3 p-5 relative">
      <h3
        class="text-xl font-bold truncate transition-colors duration-300 group-hover:text-[var(--color-primary)]"
        style="font-family: var(--font-accent); color: var(--color-text-1);"
      >
        {{ project.title }}
      </h3>
      <p
        class="text-[13px] leading-relaxed line-clamp-2"
        style="color: var(--color-text-2);"
      >
        {{ project.description }}
      </p>
      <div class="flex flex-wrap gap-2 mt-1">
        <TagChip
          v-for="tag in project.tags.slice(0, 3)"
          :key="tag"
          :label="tag"
        />
      </div>
    </div>
    <div
      class="absolute inset-0 opacity-0 group-hover:opacity-100 transition-opacity duration-500 pointer-events-none rounded-xl"
      style="box-shadow: var(--shadow-glow);"
    />
  </RouterLink>
</template>
