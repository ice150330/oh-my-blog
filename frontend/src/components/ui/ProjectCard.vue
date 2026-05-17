<script setup lang="ts">
import type { Project } from '@/types'
import CategoryBadge from './CategoryBadge.vue'
import TagChip from './TagChip.vue'

defineProps<{
  project: Project
}>()
</script>

<template>
  <RouterLink
    :to="`/projects/${project.slug}`"
    class="group flex flex-col rounded-xl overflow-hidden transition-all hover:-translate-y-1 cursor-pointer"
    style="background: var(--color-card); border: 1px solid var(--color-border);"
  >
    <div
      class="h-[200px] w-full relative flex items-start justify-start p-4"
      :style="{
        background: `radial-gradient(circle at 30% 40%, ${project.coverGradient[0]}, ${project.coverGradient[1]})`,
      }"
    >
      <CategoryBadge :category="project.category" />
    </div>
    <div class="flex flex-col gap-3 p-4">
      <h3
        class="text-xl font-bold truncate"
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
  </RouterLink>
</template>
