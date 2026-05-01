<template>
  <!-- 首页 —— Hero 区域 + 最新内容 -->
  <div class="home-page">
    <!-- Hero 区域 -->
    <section class="hero">
      <div class="container hero-inner">
        <div class="hero-content">
          <h1 class="hero-title">
            <span class="text-gradient">探索技术</span>
            <br />
            <span class="font-display">记录成长</span>
          </h1>
          <p class="hero-subtitle">
            一个基于光影设计语言的个人博客，分享技术文章、项目实践与编程心得。
          </p>
          <div class="hero-actions">
            <NuxtLink to="/blog" class="btn-glow">
              <IconSprite name="article" :size="18" />
              浏览文章
            </NuxtLink>
            <NuxtLink to="/projects" class="btn-secondary">
              <IconSprite name="project" :size="18" />
              查看项目
            </NuxtLink>
          </div>
        </div>

        <!-- 统计数据展示 -->
        <div class="hero-stats">
          <div class="stat-item card-glass">
            <IconSprite name="article" :size="28" class-name="stat-icon" />
            <span class="stat-value">{{ stats.articleCount }}</span>
            <span class="stat-label">文章</span>
          </div>
          <div class="stat-item card-glass">
            <IconSprite name="project" :size="28" class-name="stat-icon" />
            <span class="stat-value">{{ stats.projectCount }}</span>
            <span class="stat-label">项目</span>
          </div>
          <div class="stat-item card-glass">
            <IconSprite name="eye" :size="28" class-name="stat-icon" />
            <span class="stat-value">{{ formatNumber(stats.viewCount) }}</span>
            <span class="stat-label">浏览</span>
          </div>
          <div class="stat-item card-glass">
            <IconSprite name="calendar" :size="28" class-name="stat-icon" />
            <span class="stat-value">{{ stats.runDays }}</span>
            <span class="stat-label">运行天数</span>
          </div>
        </div>
      </div>
    </section>

    <!-- 最新文章 -->
    <section class="latest-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">最新文章</h2>
          <NuxtLink to="/blog" class="section-link">
            查看全部
            <IconSprite name="arrow-right" :size="16" />
          </NuxtLink>
        </div>

        <!-- 加载状态 -->
        <div v-if="articlesLoading" class="loading-grid">
          <div v-for="i in 3" :key="i" class="loading-card card-glass">
            <div class="loading-spinner"></div>
          </div>
        </div>

        <div v-else class="article-grid">
          <ArticleCard
            v-for="article in latestArticles"
            :key="article.id"
            :article="article"
          />
        </div>
      </div>
    </section>

    <!-- 精选项目 -->
    <section class="featured-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">精选项目</h2>
          <NuxtLink to="/projects" class="section-link">
            查看全部
            <IconSprite name="arrow-right" :size="16" />
          </NuxtLink>
        </div>

        <!-- 加载状态 -->
        <div v-if="projectsLoading" class="loading-grid">
          <div v-for="i in 2" :key="i" class="loading-card card-glass">
            <div class="loading-spinner"></div>
          </div>
        </div>

        <div v-else class="project-grid">
          <ProjectCard
            v-for="project in featuredProjects"
            :key="project.id"
            :project="project"
          />
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
// SEO 元数据
useHead({
  title: 'Oh My Blog — 个人技术博客',
  meta: [
    { name: 'description', content: '基于光影设计语言的个人博客系统，分享技术文章与项目实践。' },
  ],
})

const { getList: getArticleList } = useArticleApi()
const { getList: getProjectList } = useProjectApi()
const { getTotal } = useVisitorApi()

// 统计数据
const stats = reactive({
  articleCount: 0,
  projectCount: 0,
  viewCount: 0,
  runDays: 128, // TODO: 从后端获取或计算
})

// 最新文章
const latestArticles = ref<any[]>([])
const articlesLoading = ref(true)

// 精选项目
const featuredProjects = ref<any[]>([])
const projectsLoading = ref(true)

// 加载首页数据
async function loadHomeData() {
  // 并行加载
  const articlePromise = getArticleList(1, 3).catch((err) => {
    console.error('加载文章失败:', err)
    return { records: [] }
  })
  const projectPromise = getProjectList(1, 100).catch((err) => {
    console.error('加载项目失败:', err)
    return { records: [] }
  })
  const statsPromise = getTotal().catch((err) => {
    console.error('加载统计失败:', err)
    return null
  })

  const [articleResult, projectResult, totalStats] = await Promise.all([
    articlePromise,
    projectPromise,
    statsPromise as Promise<{ totalPv?: number; totalUv?: number } | null>,
  ])

  latestArticles.value = articleResult?.records || []
  articlesLoading.value = false

  const allProjects = projectResult?.records || []
  // 优先取 featured=true 的项目，如果没有则取前 2 个
  featuredProjects.value = allProjects.filter((p: any) => p.featured).slice(0, 2)
  if (featuredProjects.value.length === 0) {
    featuredProjects.value = allProjects.slice(0, 2)
  }
  projectsLoading.value = false

  // 更新统计数据
  stats.articleCount = articleResult?.total || latestArticles.value.length
  stats.projectCount = projectResult?.total || allProjects.length
  if (totalStats) {
    stats.viewCount = Number(totalStats.totalPv || 0)
  }
}

// 数字格式化
function formatNumber(num: number) {
  if (num >= 10000) {
    return (num / 10000).toFixed(1) + 'w'
  }
  if (num >= 1000) {
    return (num / 1000).toFixed(1) + 'k'
  }
  return num.toString()
}

onMounted(() => {
  loadHomeData()
})
</script>

<style scoped lang="scss">
.hero {
  min-height: calc(100vh - 64px);
  display: flex;
  align-items: center;
  padding: var(--space-3xl) 0;
  background: var(--gradient-hero);
}

.hero-inner {
  display: flex;
  flex-direction: column;
  gap: var(--space-3xl);

  @media (min-width: 1024px) {
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
  }
}

.hero-content {
  max-width: 600px;
}

.hero-title {
  font-size: var(--font-size-display-xl);
  font-weight: 700;
  line-height: 1.1;
  letter-spacing: -0.02em;
  margin-bottom: var(--space-lg);

  .font-display {
    font-family: var(--font-display);
    color: var(--color-primary);
  }
}

.hero-subtitle {
  font-size: var(--font-size-body-lg);
  color: var(--color-muted);
  line-height: 1.7;
  margin-bottom: var(--space-xl);
}

.hero-actions {
  display: flex;
  gap: var(--space-md);
  flex-wrap: wrap;
}

.btn-secondary {
  display: inline-flex;
  align-items: center;
  gap: var(--space-sm);
  padding: var(--space-sm) var(--space-lg);
  border-radius: var(--radius-md);
  border: 1px solid var(--color-border);
  color: var(--color-foreground);
  font-weight: 500;
  transition: background var(--duration-fast), border-color var(--duration-fast);

  &:hover {
    background: var(--color-surface-elevated);
    border-color: var(--color-primary);
  }
}

.hero-stats {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: var(--space-md);

  @media (min-width: 640px) {
    grid-template-columns: repeat(4, 1fr);
  }
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: var(--space-lg);
  text-align: center;
  gap: var(--space-xs);

  :deep(.card-glass) {
    padding: var(--space-lg);
  }
}

.stat-icon {
  color: var(--color-primary);
  margin-bottom: var(--space-xs);
}

.stat-value {
  font-size: var(--font-size-headline);
  font-weight: 700;
  color: var(--color-foreground);
}

.stat-label {
  font-size: var(--font-size-caption);
  color: var(--color-muted);
}

// 最新内容区域
.latest-section,
.featured-section {
  padding: var(--space-3xl) 0;
}

.featured-section {
  background: var(--color-surface);
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: var(--space-xl);
}

.section-title {
  font-size: var(--font-size-headline);
  font-weight: 700;
  color: var(--color-foreground);
}

.section-link {
  display: flex;
  align-items: center;
  gap: var(--space-xs);
  font-size: var(--font-size-body);
  color: var(--color-primary);
  transition: gap var(--duration-fast);

  &:hover {
    gap: var(--space-sm);
  }
}

.article-grid {
  display: grid;
  gap: var(--space-lg);

  @media (min-width: 768px) {
    grid-template-columns: repeat(2, 1fr);
  }

  @media (min-width: 1024px) {
    grid-template-columns: repeat(3, 1fr);
  }
}

.project-grid {
  display: grid;
  gap: var(--space-lg);

  @media (min-width: 768px) {
    grid-template-columns: repeat(2, 1fr);
  }
}

.loading-grid {
  display: grid;
  gap: var(--space-lg);

  @media (min-width: 768px) {
    grid-template-columns: repeat(2, 1fr);
  }

  @media (min-width: 1024px) {
    grid-template-columns: repeat(3, 1fr);
  }
}

.loading-card {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--space-3xl);
  min-height: 200px;
}

.loading-spinner {
  width: 32px;
  height: 32px;
  border: 3px solid var(--color-border);
  border-top-color: var(--color-primary);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}
</style>
