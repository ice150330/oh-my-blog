<template>
  <!-- 博客列表页 -->
  <div class="blog-page">
    <div class="container">
      <header class="page-header">
        <h1 class="page-title">博客文章</h1>
        <p class="page-subtitle">分享技术心得与编程实践</p>
      </header>

      <!-- 标签筛选 -->
      <div v-if="!tagsLoading" class="tag-filter">
        <button
          class="filter-btn"
          :class="{ active: selectedTag === null }"
          @click="selectedTag = null"
        >
          全部
        </button>
        <button
          v-for="tag in allTags"
          :key="tag"
          class="filter-btn"
          :class="{ active: selectedTag === tag }"
          @click="selectedTag = tag"
        >
          {{ tag }}
        </button>
      </div>

      <!-- 加载状态 -->
      <div v-if="isLoading" class="loading-state">
        <div class="loading-spinner"></div>
        <p>正在加载文章...</p>
      </div>

      <!-- 错误状态 -->
      <div v-else-if="hasError" class="empty-state">
        <IconSprite name="article" :size="48" class-name="empty-icon" />
        <p>加载失败，请刷新页面重试</p>
      </div>

      <!-- 文章列表 -->
      <div v-else class="article-list">
        <ArticleCard
          v-for="article in filteredArticles"
          :key="article.id"
          :article="article"
        />
      </div>

      <!-- 空状态 -->
      <div v-if="!isLoading && filteredArticles.length === 0" class="empty-state">
        <IconSprite name="article" :size="48" class-name="empty-icon" />
        <p>暂无相关文章</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
useHead({
  title: '博客 — Oh My Blog',
})

const { getList, getTags } = useArticleApi()

const articles = ref<any[]>([])
const allTags = ref<string[]>([])
const isLoading = ref(true)
const tagsLoading = ref(true)
const hasError = ref(false)
const selectedTag = ref<string | null>(null)

// 加载文章列表
async function loadArticles() {
  isLoading.value = true
  hasError.value = false
  try {
    const result = await getList(1, 100)
    articles.value = result?.records || []
  } catch (err) {
    console.error('加载文章失败:', err)
    hasError.value = true
  } finally {
    isLoading.value = false
  }
}

// 加载标签
async function loadTags() {
  tagsLoading.value = true
  try {
    allTags.value = await getTags()
  } catch (err) {
    console.error('加载标签失败:', err)
  } finally {
    tagsLoading.value = false
  }
}

// 标签筛选
const filteredArticles = computed(() => {
  if (!selectedTag.value) return articles.value
  return articles.value.filter((a: any) => a.tags?.includes(selectedTag.value))
})

onMounted(() => {
  loadArticles()
  loadTags()
})
</script>

<style scoped lang="scss">
.blog-page {
  padding: calc(64px + var(--space-3xl)) 0 var(--space-3xl);
}

.page-header {
  text-align: center;
  margin-bottom: var(--space-2xl);
}

.page-title {
  font-size: var(--font-size-display);
  font-weight: 700;
  color: var(--color-foreground);
}

.page-subtitle {
  font-size: var(--font-size-body-lg);
  color: var(--color-muted);
  margin-top: var(--space-sm);
}

.tag-filter {
  display: flex;
  flex-wrap: wrap;
  gap: var(--space-sm);
  justify-content: center;
  margin-bottom: var(--space-xl);
}

.filter-btn {
  padding: var(--space-xs) var(--space-md);
  border-radius: var(--radius-full);
  border: 1px solid var(--color-border);
  background: var(--color-surface);
  color: var(--color-muted);
  font-size: var(--font-size-body);
  transition: all var(--duration-fast);
  cursor: pointer;

  &:hover {
    border-color: var(--color-primary);
    color: var(--color-primary);
  }

  &.active {
    background: var(--color-primary);
    color: white;
    border-color: var(--color-primary);
    box-shadow: var(--glow-primary);
  }
}

.article-list {
  display: grid;
  gap: var(--space-lg);

  @media (min-width: 768px) {
    grid-template-columns: repeat(2, 1fr);
  }

  @media (min-width: 1024px) {
    grid-template-columns: repeat(3, 1fr);
  }
}

.loading-state,
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--space-md);
  padding: var(--space-3xl);
  color: var(--color-muted);
}

.empty-icon {
  opacity: 0.5;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--color-border);
  border-top-color: var(--color-primary);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}
</style>
