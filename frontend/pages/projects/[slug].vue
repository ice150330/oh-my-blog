<template>
  <div class="project-demo-page">
    <!-- 返回导航 -->
    <div class="demo-nav">
      <NuxtLink to="/projects" class="back-link">
        <span class="back-icon">←</span>
        返回项目列表
      </NuxtLink>
    </div>

    <!-- 加载状态 -->
    <div v-if="isLoading" class="demo-header loading-header">
      <div class="loading-spinner"></div>
      <p>正在加载项目信息...</p>
    </div>

    <!-- 错误状态 -->
    <div v-else-if="hasError" class="demo-header error-header">
      <div class="error-icon">⚠️</div>
      <p>项目加载失败</p>
      <p class="error-hint">请检查网络连接或刷新页面重试</p>
    </div>

    <template v-else-if="project">
      <!-- 项目信息头部 -->
      <header class="demo-header">
        <div class="demo-info">
          <h1 class="demo-title">{{ project.title }}</h1>
          <p class="demo-description">{{ project.description }}</p>
          <div class="demo-tags">
            <span v-for="tag in project.tags" :key="tag" class="tag">{{ tag }}</span>
          </div>
        </div>
        <div class="demo-actions">
          <span class="demo-badge">项目演示</span>
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
      </header>

      <!-- 演示区域 -->
      <div v-if="project.demoUrl || project.iframeDemo" class="demo-container">
        <div class="demo-wrapper" :class="{ 'is-loading': iframeLoading }">
          <!-- iframe 加载状态 -->
          <div v-if="iframeLoading" class="demo-loading">
            <div class="loading-spinner"></div>
            <p>正在加载演示...</p>
          </div>

          <!-- iframe 展示 -->
          <iframe
            v-show="!iframeLoading"
            ref="iframeRef"
            :src="project.iframeDemo || project.demoUrl"
            class="demo-iframe"
            :sandbox="sandboxOptions"
            loading="lazy"
            @load="onIframeLoad"
            @error="onIframeError"
          />

          <!-- iframe 错误状态 -->
          <div v-if="iframeError" class="demo-error">
            <div class="error-icon">⚠️</div>
            <p>演示加载失败</p>
            <p class="error-hint">请检查网络连接或刷新页面重试</p>
          </div>
        </div>
      </div>

      <!-- 项目技术栈 -->
      <footer class="demo-footer">
        <div class="tech-stack">
          <span class="tech-label">技术栈：</span>
          <span v-for="tech in project.techStack" :key="tech" class="tech-item">{{ tech }}</span>
        </div>
      </footer>
    </template>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'

definePageMeta({
  layout: 'default'
})

const route = useRoute()
const slug = computed(() => route.params.slug as string)

// API 调用
const { getBySlug } = useProjectApi()
const project = ref<any>(null)
const isLoading = ref(true)
const hasError = ref(false)

// iframe 状态
const iframeLoading = ref(true)
const iframeError = ref(false)
const iframeRef = ref<HTMLIFrameElement | null>(null)

// iframe 沙箱配置
const sandboxOptions = 'allow-scripts allow-same-origin allow-forms allow-modals allow-pointer-lock allow-top-navigation-by-user-activation'

// 加载项目数据
async function loadProject() {
  isLoading.value = true
  hasError.value = false
  try {
    project.value = await getBySlug(slug.value)
    iframeLoading.value = true
    iframeError.value = false
  } catch (err) {
    console.error('加载项目失败:', err)
    hasError.value = true
  } finally {
    isLoading.value = false
  }
}

// 事件处理
function onIframeLoad() {
  iframeLoading.value = false
  iframeError.value = false
  adjustIframeHeight()
}

function onIframeError() {
  iframeLoading.value = false
  iframeError.value = true
}

// 高度自适应
function adjustIframeHeight() {
  const iframe = iframeRef.value
  if (!iframe?.contentDocument?.body) return

  const body = iframe.contentDocument.body
  const html = iframe.contentDocument.documentElement

  setTimeout(() => {
    if (iframe) {
      const height = Math.min(
        Math.max(body.scrollHeight, html.scrollHeight) + 20,
        800 // 最大高度限制
      )
      iframe.style.height = `${height}px`
    }
  }, 500)
}

// 移动端适配
function handleResize() {
  if (window.innerWidth < 768) {
    if (iframeRef.value) {
      iframeRef.value.style.height = '60vh'
    }
  } else {
    adjustIframeHeight()
  }
}

onMounted(() => {
  loadProject()
  window.addEventListener('resize', handleResize)
  handleResize()
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
})

// SEO
useHead({
  title: computed(() => project.value ? `${project.value.title} — Oh My Blog` : '项目演示')
})
</script>

<style scoped lang="scss">
.project-demo-page {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--color-background);
}

// 返回导航
.demo-nav {
  padding: var(--space-md) var(--space-xl);
  border-bottom: 1px solid var(--color-border);
  background: var(--color-surface);
}

.back-link {
  display: inline-flex;
  align-items: center;
  gap: var(--space-sm);
  color: var(--color-muted);
  text-decoration: none;
  font-size: var(--font-size-caption);
  transition: color 0.2s ease;

  &:hover {
    color: var(--color-primary);
  }

  .back-icon {
    font-size: 1.2em;
  }
}

// 头部信息
.demo-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: var(--space-xl) var(--space-xl);
  border-bottom: 1px solid var(--color-border);
  background: var(--color-surface);
}

.demo-info {
  flex: 1;
}

.demo-title {
  font-size: var(--font-size-headline);
  font-weight: 700;
  color: var(--color-foreground);
  margin: 0 0 var(--space-sm);
}

.demo-description {
  font-size: var(--font-size-body);
  color: var(--color-muted);
  margin: 0 0 var(--space-md);
  max-width: 600px;
  line-height: 1.6;
}

.demo-tags {
  display: flex;
  flex-wrap: wrap;
  gap: var(--space-sm);

  .tag {
    padding: var(--space-xs) var(--space-sm);
    background: var(--color-primary-light);
    color: var(--color-primary);
    border-radius: var(--radius-sm);
    font-size: var(--font-size-caption);
  }
}

.demo-actions {
  flex-shrink: 0;
  margin-left: var(--space-xl);
  display: flex;
  align-items: center;
  gap: var(--space-md);
}

.demo-badge {
  display: inline-block;
  padding: var(--space-xs) var(--space-md);
  background: linear-gradient(135deg, var(--color-primary) 0%, var(--color-primary-dark) 100%);
  color: white;
  border-radius: var(--radius-full);
  font-size: var(--font-size-caption);
  font-weight: 500;
}

// 演示区域
.demo-container {
  flex: 1;
  padding: var(--space-xl);
  display: flex;
  flex-direction: column;
}

.demo-wrapper {
  flex: 1;
  position: relative;
  background: var(--color-surface);
  border-radius: var(--radius-xl);
  box-shadow:
    0 4px 6px -1px rgba(0, 0, 0, 0.05),
    0 10px 15px -3px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  min-height: 500px;

  &.is-loading {
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

.demo-iframe {
  width: 100%;
  height: 700px;
  border: none;
  display: block;
  background: white;
}

// 加载状态
.demo-loading,
.loading-header {
  position: absolute;
  inset: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: var(--color-surface);
  z-index: 10;

  p {
    margin-top: var(--space-md);
    color: var(--color-muted);
    font-size: var(--font-size-caption);
  }
}

.loading-header {
  position: static;
  padding: var(--space-3xl);
  border: none;
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

// 错误状态
.demo-error,
.error-header {
  position: absolute;
  inset: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: var(--color-surface);

  .error-icon {
    font-size: 48px;
    margin-bottom: var(--space-md);
  }

  p {
    margin: 0;
    color: var(--color-foreground);
    font-size: var(--font-size-body);
  }

  .error-hint {
    margin-top: var(--space-sm);
    color: var(--color-muted);
    font-size: var(--font-size-caption);
  }
}

.error-header {
  position: static;
  padding: var(--space-3xl);
  border: none;
}

// 页脚
.demo-footer {
  padding: var(--space-lg) var(--space-xl);
  border-top: 1px solid var(--color-border);
  background: var(--color-surface);
}

.tech-stack {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: var(--space-sm);

  .tech-label {
    color: var(--color-muted);
    font-size: var(--font-size-caption);
  }

  .tech-item {
    padding: var(--space-xs) var(--space-sm);
    background: var(--color-surface-elevated);
    border: 1px solid var(--color-border);
    border-radius: var(--radius-sm);
    font-size: var(--font-size-caption);
    color: var(--color-foreground);
  }
}

// 移动端适配
@media (max-width: 768px) {
  .demo-header {
    flex-direction: column;
    padding: var(--space-lg);
  }

  .demo-actions {
    margin-left: 0;
    margin-top: var(--space-md);
  }

  .demo-container {
    padding: var(--space-md);
  }

  .demo-wrapper {
    min-height: 400px;
  }

  .demo-iframe {
    height: 60vh;
  }
}

// 暗色模式
.dark {
  .demo-wrapper {
    background: var(--color-surface);
    box-shadow:
      0 4px 6px -1px rgba(0, 0, 0, 0.3),
      0 10px 15px -3px rgba(0, 0, 0, 0.2);
  }

  .demo-iframe {
    background: #1a1a2e;
  }

  .demo-loading,
  .loading-header {
    background: var(--color-surface);
  }
}
</style>
