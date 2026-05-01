<template>
  <!-- 博客详情页 -->
  <div class="article-page">
    <article class="container article-container">
      <!-- 加载状态 -->
      <div v-if="isLoading" class="loading-state">
        <div class="loading-spinner"></div>
        <p>正在加载文章...</p>
      </div>

      <!-- 错误状态 -->
      <div v-else-if="hasError" class="empty-state">
        <IconSprite name="article" :size="48" class-name="empty-icon" />
        <p>文章加载失败，请刷新页面重试</p>
      </div>

      <template v-else-if="article">
        <!-- 文章头部 -->
        <header class="article-header">
          <div class="article-meta">
            <span class="meta-item">
              <IconSprite name="calendar" :size="14" />
              {{ formatDate(article.createdAt) }}
            </span>
            <span class="meta-item">
              <IconSprite name="clock" :size="14" />
              {{ article.readTime }} 分钟阅读
            </span>
            <span class="meta-item">
              <IconSprite name="eye" :size="14" />
              {{ article.viewCount }} 次浏览
            </span>
          </div>
          <h1 class="article-title">{{ article.title }}</h1>
          <div class="article-tags">
            <span v-for="tag in article.tags" :key="tag" class="tag">
              <IconSprite name="tag" :size="12" />
              {{ tag }}
            </span>
          </div>
        </header>

        <!-- 文章内容 -->
        <div class="article-content prose" v-html="renderedContent" />

        <!-- 文章底部 -->
        <footer class="article-footer">
          <div class="footer-actions">
            <button class="action-btn" @click="handleLike">
              <IconSprite name="heart" :size="18" />
              喜欢
            </button>
            <button class="action-btn" @click="handleShare">
              <IconSprite name="share" :size="18" />
              分享
            </button>
          </div>
        </footer>
      </template>
    </article>
  </div>
</template>

<script setup lang="ts">
const route = useRoute()
const slug = route.params.slug as string

const { getBySlug } = useArticleApi()
const article = ref<any>(null)
const isLoading = ref(true)
const hasError = ref(false)

// 加载文章详情
async function loadArticle() {
  isLoading.value = true
  hasError.value = false
  try {
    article.value = await getBySlug(slug)
  } catch (err) {
    console.error('加载文章详情失败:', err)
    hasError.value = true
  } finally {
    isLoading.value = false
  }
}

// SEO
useHead({
  title: computed(() => article.value ? `${article.value.title} — Oh My Blog` : '文章详情'),
})

function formatDate(dateStr: string) {
  const date = new Date(dateStr)
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
}

// 简易 Markdown 渲染
const renderedContent = computed(() => {
  if (!article.value?.content) return ''
  let md = article.value.content
  // 转义 HTML
  md = md.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
  // 代码块
  md = md.replace(/```([\s\S]*?)```/g, '<pre><code>$1</code></pre>')
  // 行内代码
  md = md.replace(/`([^`]+)`/g, '<code>$1</code>')
  // 标题
  md = md.replace(/^### (.*$)/gim, '<h3>$1</h3>')
  md = md.replace(/^## (.*$)/gim, '<h2>$1</h2>')
  md = md.replace(/^# (.*$)/gim, '<h1>$1</h1>')
  // 粗体
  md = md.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
  // 斜体
  md = md.replace(/\*(.*?)\*/g, '<em>$1</em>')
  // 列表项
  md = md.replace(/^- (.*$)/gim, '<li>$1</li>')
  // 段落（简单的换行处理）
  md = md.replace(/\n\n/g, '</p><p>')
  md = md.replace(/\n/g, '<br>')
  // 包裹段落
  if (!md.startsWith('<')) {
    md = '<p>' + md + '</p>'
  }
  return md
})

function handleLike() {
  // TODO: 对接后端点赞 API
  alert('感谢你的喜欢！')
}

function handleShare() {
  if (navigator.share) {
    navigator.share({
      title: article.value?.title,
      url: window.location.href,
    })
  } else {
    navigator.clipboard.writeText(window.location.href)
    alert('链接已复制到剪贴板')
  }
}

onMounted(() => {
  loadArticle()
})
</script>

<style scoped lang="scss">
.article-page {
  padding: calc(64px + var(--space-2xl)) 0 var(--space-3xl);
}

.article-container {
  max-width: var(--container-narrow);
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

.article-header {
  margin-bottom: var(--space-2xl);
  text-align: center;
}

.article-meta {
  display: flex;
  justify-content: center;
  gap: var(--space-md);
  margin-bottom: var(--space-lg);
  font-size: var(--font-size-caption);
  color: var(--color-muted);

  .meta-item {
    display: flex;
    align-items: center;
    gap: var(--space-xs);
  }
}

.article-title {
  font-size: var(--font-size-display);
  font-weight: 700;
  color: var(--color-foreground);
  line-height: 1.2;
  margin-bottom: var(--space-md);
}

.article-tags {
  display: flex;
  justify-content: center;
  gap: var(--space-xs);
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

// 文章内容样式
.article-content {
  font-size: var(--font-size-body-lg);
  line-height: 1.8;
  color: var(--color-foreground);

  h1, h2, h3 {
    font-weight: 700;
    margin: var(--space-xl) 0 var(--space-md);
    color: var(--color-foreground);
  }

  h1 {
    font-size: var(--font-size-headline);
  }

  h2 {
    font-size: calc(var(--font-size-headline) * 0.9);
  }

  h3 {
    font-size: calc(var(--font-size-headline) * 0.8);
  }

  p {
    margin-bottom: var(--space-md);
    color: var(--color-muted);
  }

  ul, ol {
    margin-bottom: var(--space-md);
    padding-left: var(--space-lg);
  }

  li {
    margin-bottom: var(--space-xs);
    color: var(--color-muted);
  }

  hr {
    border: none;
    border-top: 1px solid var(--color-border);
    margin: var(--space-xl) 0;
  }

  code {
    font-family: var(--font-code);
    background: var(--color-surface-elevated);
    padding: var(--space-xs) var(--space-sm);
    border-radius: var(--radius-sm);
    font-size: 0.9em;
  }

  pre {
    background: var(--color-surface-elevated);
    padding: var(--space-lg);
    border-radius: var(--radius-lg);
    overflow-x: auto;
    margin-bottom: var(--space-md);

    code {
      background: none;
      padding: 0;
    }
  }

  strong {
    color: var(--color-foreground);
    font-weight: 600;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: var(--space-md);

    th, td {
      border: 1px solid var(--color-border);
      padding: var(--space-sm);
      text-align: left;
    }

    th {
      background: var(--color-surface-elevated);
      font-weight: 600;
    }
  }
}

// 文章底部
.article-footer {
  margin-top: var(--space-3xl);
  padding-top: var(--space-xl);
  border-top: 1px solid var(--color-border);
}

.footer-actions {
  display: flex;
  justify-content: center;
  gap: var(--space-md);
  margin-bottom: var(--space-2xl);
}

.action-btn {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  padding: var(--space-sm) var(--space-lg);
  border-radius: var(--radius-md);
  border: 1px solid var(--color-border);
  color: var(--color-muted);
  background: var(--color-surface);
  transition: all var(--duration-fast);

  &:hover {
    color: var(--color-primary);
    border-color: var(--color-primary);
    box-shadow: var(--glow-primary);
  }
}
</style>
