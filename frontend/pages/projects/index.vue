<template>
  <!-- 项目展示页 -->
  <div class="projects-page">
    <div class="container">
      <header class="page-header">
        <h1 class="page-title">项目展示</h1>
        <p class="page-subtitle">探索我的开源项目与技术实践</p>
      </header>

      <!-- 加载状态 -->
      <div v-if="isLoading" class="loading-state">
        <div class="loading-spinner"></div>
        <p>正在加载项目...</p>
      </div>

      <!-- 错误状态 -->
      <div v-else-if="hasError" class="empty-state">
        <IconSprite name="project" :size="48" class-name="empty-icon" />
        <p>加载失败，请刷新页面重试</p>
      </div>

      <!-- 项目列表 -->
      <div v-else class="project-list">
        <ProjectCard
          v-for="project in projects"
          :key="project.id"
          :project="project"
        />
      </div>

      <!-- 空状态 -->
      <div v-if="!isLoading && projects.length === 0" class="empty-state">
        <IconSprite name="project" :size="48" class-name="empty-icon" />
        <p>暂无项目</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
useHead({
  title: '项目 — Oh My Blog',
})

const { getList } = useProjectApi()

const projects = ref<any[]>([])
const isLoading = ref(true)
const hasError = ref(false)

// 加载项目列表
async function loadProjects() {
  isLoading.value = true
  hasError.value = false
  try {
    const result = await getList(1, 100)
    projects.value = result?.records || []
  } catch (err) {
    console.error('加载项目失败:', err)
    hasError.value = true
  } finally {
    isLoading.value = false
  }
}

onMounted(() => {
  loadProjects()
})
</script>

<style scoped lang="scss">
.projects-page {
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

.project-list {
  display: grid;
  gap: var(--space-lg);

  @media (min-width: 768px) {
    grid-template-columns: repeat(2, 1fr);
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
