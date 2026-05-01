<template>
  <!-- 项目卡片 —— 玻璃拟态 + 技术栈标签 -->
  <div class="project-card card-glass">
    <div class="card-cover">
      <img
        v-if="project.cover"
        :src="project.cover"
        :alt="project.title"
        loading="lazy"
      />
      <div v-else class="cover-placeholder">
        <IconSprite name="code" :size="48" />
      </div>
    </div>
    <div class="card-body">
      <h3 class="card-title">{{ project.title }}</h3>
      <p class="card-description text-truncate-2">{{ project.description }}</p>
      <div class="tech-stack">
        <span v-for="tech in project.techStack" :key="tech" class="tech-tag">
          {{ tech }}
        </span>
      </div>
      <div class="card-actions">
        <NuxtLink
          v-if="project.iframeDemo || project.demoUrl"
          :to="`/projects/${project.slug}`"
          class="btn-glow btn-sm"
        >
          <IconSprite name="eye" :size="14" />
          查看 Demo
        </NuxtLink>
        <a
          v-if="project.repoUrl"
          :href="project.repoUrl"
          target="_blank"
          rel="noopener noreferrer"
          class="btn-secondary btn-sm"
        >
          <IconSprite name="github" :size="14" />
          源码
        </a>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
interface ProjectCardProps {
  project: {
    id: number
    title: string
    slug: string
    description: string
    cover?: string
    tags: string[]
    techStack: string[]
    demoUrl?: string
    repoUrl?: string
    iframeDemo?: string
    featured: boolean
  }
}

defineProps<ProjectCardProps>()
</script>

<style scoped lang="scss">
.project-card {
  overflow: hidden;
  transition: transform var(--duration-normal), box-shadow var(--duration-normal);

  &:hover {
    transform: scale(1.03);
    box-shadow: var(--shadow-lg), var(--glow-primary);
  }
}

.card-cover {
  aspect-ratio: 16 / 9;
  overflow: hidden;
  background: var(--color-surface-elevated);

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform var(--duration-slow);
  }

  .project-card:hover & img {
    transform: scale(1.05);
  }
}

.cover-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--color-muted);
}

.card-body {
  padding: var(--space-lg);
  display: flex;
  flex-direction: column;
  gap: var(--space-sm);
}

.card-title {
  font-size: var(--font-size-title);
  font-weight: 600;
  color: var(--color-foreground);
}

.card-description {
  font-size: var(--font-size-body);
  color: var(--color-muted);
  line-height: 1.6;
}

.tech-stack {
  display: flex;
  flex-wrap: wrap;
  gap: var(--space-xs);
}

.tech-tag {
  padding: var(--space-xs) var(--space-sm);
  border-radius: var(--radius-sm);
  background: var(--color-border);
  color: var(--color-muted);
  font-size: var(--font-size-caption);
  font-family: var(--font-code);
}

.card-actions {
  display: flex;
  gap: var(--space-sm);
  margin-top: var(--space-sm);
}

.btn-sm {
  padding: var(--space-xs) var(--space-md);
  font-size: var(--font-size-caption);
}

.btn-secondary.btn-sm {
  display: inline-flex;
  align-items: center;
  gap: var(--space-xs);
  padding: var(--space-xs) var(--space-md);
  border-radius: var(--radius-md);
  border: 1px solid var(--color-border);
  color: var(--color-foreground);
  font-size: var(--font-size-caption);
  transition: background var(--duration-fast), border-color var(--duration-fast);

  &:hover {
    background: var(--color-surface-elevated);
    border-color: var(--color-primary);
  }
}
</style>
