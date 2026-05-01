<template>
  <!-- SVG 图标组件 —— 引用 sprite.svg 中的 symbol -->
  <svg
    :class="iconClass"
    :width="size"
    :height="size"
    :aria-label="ariaLabel"
    role="img"
  >
    <use :href="`/icon/sprite.svg#${name}`" />
  </svg>
</template>

<script setup lang="ts">
// 图标名称类型（基于 42 个预设图标）
export type IconName =
  | 'home' | 'about' | 'article' | 'project' | 'bookmark'
  | 'search' | 'menu' | 'close' | 'moon' | 'sun'
  | 'system-theme' | 'github' | 'mail' | 'link'
  | 'external-link' | 'arrow-left' | 'arrow-right'
  | 'arrow-up' | 'arrow-down' | 'chevron-left'
  | 'chevron-right' | 'calendar' | 'clock' | 'tag'
  | 'folder' | 'eye' | 'heart' | 'share' | 'copy'
  | 'check' | 'warning' | 'error' | 'refresh'
  | 'code' | 'terminal' | 'database' | 'cloud'
  | 'stack' | 'filter' | 'sort' | 'sparkles'
  | 'rss'

interface Props {
  name: IconName
  size?: number | string
  className?: string
  ariaLabel?: string
}

const props = withDefaults(defineProps<Props>(), {
  size: 24,
  className: '',
  ariaLabel: '',
})

const iconClass = computed(() => {
  return ['icon-sprite', props.className].filter(Boolean).join(' ')
})
</script>

<style scoped>
.icon-sprite {
  display: inline-block;
  vertical-align: middle;
  flex-shrink: 0;
  fill: none;
  stroke: currentColor;
  stroke-width: 2;
  stroke-linecap: round;
  stroke-linejoin: round;
}
</style>
