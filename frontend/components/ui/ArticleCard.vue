<template>
  <!-- 文章卡片 —— 玻璃拟态设计 -->
  <NuxtLink :to="`/blog/${article.slug}`" class="article-card card-glass">
    <div class="card-body">
      <div class="card-meta">
        <span class="meta-date">
          <IconSprite name="calendar" :size="14" />
          {{ formatDate(article.createdAt) }}
        </span>
        <span class="meta-readtime">
          <IconSprite name="clock" :size="14" />
          {{ article.readTime }} 分钟
        </span>
      </div>
      <h3 class="card-title text-truncate-2">{{ article.title }}</h3>
      <p class="card-summary text-truncate-3">{{ article.summary }}</p>
      <div class="card-tags">
        <span v-for="tag in article.tags" :key="tag" class="tag">
          <IconSprite name="tag" :size="12" />
          {{ tag }}
        </span>
      </div>
    </div>
  </NuxtLink>
</template>

<script setup lang="ts">
interface ArticleCardProps {
  article: {
    id: number
    title: string
    slug: string
    summary: string
    tags: string[]
    readTime: number
    createdAt: string
  }
}

const props = defineProps<ArticleCardProps>()

function formatDate(dateStr: string) {
  const date = new Date(dateStr)
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
}
</script>

<style scoped lang="scss">
.article-card {
  display: block;
  text-decoration: none;
  color: inherit;
  padding: var(--space-lg);
  transition: transform var(--duration-normal), box-shadow var(--duration-normal);

  &:hover {
    transform: scale(1.03);
    box-shadow: var(--shadow-lg), var(--glow-primary);
  }
}

.card-body {
  display: flex;
  flex-direction: column;
  gap: var(--space-sm);
}

.card-meta {
  display: flex;
  gap: var(--space-md);
  font-size: var(--font-size-caption);
  color: var(--color-muted);

  span {
    display: flex;
    align-items: center;
    gap: var(--space-xs);
  }
}

.card-title {
  font-size: var(--font-size-title);
  font-weight: 600;
  color: var(--color-foreground);
  line-height: 1.3;
  transition: color var(--duration-fast);

  .article-card:hover & {
    color: var(--color-primary);
  }
}

.card-summary {
  font-size: var(--font-size-body);
  color: var(--color-muted);
  line-height: 1.6;
}

.card-tags {
  display: flex;
  flex-wrap: wrap;
  gap: var(--space-xs);
  margin-top: var(--space-xs);
}

.tag {
  display: inline-flex;
  align-items: center;
  gap: var(--space-xs);
  padding: var(--space-xs) var(--space-sm);
  border-radius: var(--radius-sm);
  background: rgba(59, 130, 246, 0.08);
  color: var(--color-primary);
  font-size: var(--font-size-caption);
}
</style>
