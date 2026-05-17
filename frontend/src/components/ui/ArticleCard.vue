<script setup lang="ts">
import type { Article } from '@/types'

defineProps<{
  article: Article
}>()
</script>

<template>
  <RouterLink
    :to="`/blog/${article.slug}`"
    class="group flex rounded-xl overflow-hidden transition-all duration-500 cursor-pointer"
    style="background: var(--color-card); border: 1px solid var(--color-border);"
  >
    <div
      class="w-[160px] h-[160px] shrink-0 relative overflow-hidden"
      style="background: radial-gradient(circle at 30% 40%, var(--mesh-tint-2), var(--color-bg-elevated));"
    >
      <div
        class="absolute top-3 left-3 px-2 py-0.5 rounded text-[10px] font-medium"
        style="background: var(--color-primary-soft); color: var(--color-primary); font-family: var(--font-code);"
      >
        {{ article.date.split('.')[1] }}/{{ article.date.split('.')[2] }}
      </div>
    </div>
    <div class="flex flex-col justify-center gap-2 p-5 flex-1 min-w-0">
      <h3
        class="text-base font-semibold truncate transition-colors duration-300 group-hover:text-[var(--color-primary)]"
        style="color: var(--color-text-1);"
      >
        {{ article.title }}
      </h3>
      <p
        class="text-[13px] leading-relaxed line-clamp-2"
        style="color: var(--color-text-2);"
      >
        {{ article.summary }}
      </p>
      <div class="flex items-center gap-3 mt-1">
        <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" style="color: var(--color-text-3);">
          <use href="/icons/sprite.svg#calendar" />
        </svg>
        <span class="text-[11px]" style="font-family: var(--font-code); color: var(--color-text-3);">
          {{ article.date }}
        </span>
        <span class="w-1 h-1 rounded-full" style="background: var(--color-text-3);" />
        <span class="text-[11px]" style="color: var(--color-text-3);">
          {{ article.category }}
        </span>
      </div>
    </div>
  </RouterLink>
</template>
