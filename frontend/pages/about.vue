<template>
  <!-- 关于页 —— 个人介绍 + 技术栈展示 -->
  <div class="about-page">
    <div class="container">
      <!-- 个人简介 -->
      <section class="profile-section">
        <div class="profile-card card-glass">
          <div class="profile-avatar">
            <div class="avatar-placeholder">
              <IconSprite name="about" :size="64" />
            </div>
          </div>
          <div class="profile-info">
            <h1 class="profile-name">开发者</h1>
            <p class="profile-title text-gradient">全栈工程师 / 技术博主</p>
            <p class="profile-bio">
              热爱技术，专注于前端工程化与用户体验设计。
              喜欢探索新技术，将复杂概念用简洁的方式呈现。
              相信好的代码就像好的文章 —— 清晰、优雅、有说服力。
            </p>
            <div class="profile-social">
              <a
                href="https://github.com"
                target="_blank"
                rel="noopener noreferrer"
                class="social-link"
                aria-label="GitHub"
              >
                <IconSprite name="github" :size="20" />
              </a>
              <a
                href="mailto:hello@example.com"
                class="social-link"
                aria-label="邮箱"
              >
                <IconSprite name="mail" :size="20" />
              </a>
            </div>
          </div>
        </div>
      </section>

      <!-- 技术栈 -->
      <section class="tech-section">
        <h2 class="section-title">技术栈</h2>

        <!-- 加载状态 -->
        <div v-if="isLoading" class="loading-grid">
          <div v-for="i in 4" :key="i" class="loading-card card-glass">
            <div class="loading-spinner"></div>
          </div>
        </div>

        <!-- 错误状态 -->
        <div v-else-if="hasError" class="empty-state">
          <p>技术栈加载失败</p>
        </div>

        <div v-else class="tech-grid">
          <div v-for="category in techCategories" :key="category.name" class="tech-category card-glass">
            <h3 class="category-name">
              <IconSprite :name="category.icon" :size="20" />
              {{ category.name }}
            </h3>
            <div class="tech-list">
              <span v-for="tech in category.techs" :key="tech" class="tech-item">
                {{ tech }}
              </span>
            </div>
          </div>
        </div>
      </section>

      <!-- 时间线 -->
      <section class="timeline-section">
        <h2 class="section-title">历程</h2>
        <div class="timeline">
          <div v-for="event in timelineEvents" :key="event.year" class="timeline-item">
            <div class="timeline-dot" />
            <div class="timeline-content card-glass">
              <span class="timeline-year">{{ event.year }}</span>
              <h4 class="timeline-title">{{ event.title }}</h4>
              <p class="timeline-desc">{{ event.description }}</p>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
useHead({
  title: '关于我 — Oh My Blog',
})

const { getGrouped } = useTechStackApi()

const techCategories = ref<any[]>([])
const isLoading = ref(true)
const hasError = ref(false)

// 技术栈图标映射（英文分类 → 图标）
const iconMap: Record<string, string> = {
  'language': 'code',
  'framework': 'code',
  'tool': 'terminal',
  'database': 'database',
  'cloud': 'cloud',
  'other': 'cloud',
}

// 分类名称中文化
const categoryNameMap: Record<string, string> = {
  'language': '编程语言',
  'framework': '开发框架',
  'tool': '开发工具',
  'database': '数据库',
  'cloud': '云服务',
  'other': '其他技术',
}

// 加载技术栈
async function loadTechStack() {
  isLoading.value = true
  hasError.value = false
  try {
    const grouped = await getGrouped()
    // 将后端分组数据转换为前端所需格式
    techCategories.value = Object.entries(grouped || {}).map(([name, techs]: [string, any]) => ({
      name: categoryNameMap[name] || name,
      icon: iconMap[name] || 'code',
      techs: Array.isArray(techs) ? techs.map((t: any) => t.name || t) : [],
    }))
  } catch (err) {
    console.error('加载技术栈失败:', err)
    hasError.value = true
    // 降级为默认数据
    techCategories.value = [
      { name: '前端', icon: 'code', techs: ['Vue 3', 'Nuxt 3', 'TypeScript', 'Tailwind CSS', 'Pinia'] },
      { name: '后端', icon: 'database', techs: ['Spring Boot', 'Java', 'MySQL', 'Redis', 'MyBatis Plus'] },
      { name: '工具', icon: 'terminal', techs: ['Git', 'Docker', 'Maven', 'VS Code', 'Figma'] },
      { name: '其他', icon: 'cloud', techs: ['Linux', 'Nginx', 'CI/CD', 'RESTful API', 'Markdown'] },
    ]
  } finally {
    isLoading.value = false
  }
}

const timelineEvents = [
  {
    year: '2024',
    title: '开始全栈开发之旅',
    description: '深入学习 Spring Boot 和 Vue 3，构建第一个全栈项目。',
  },
  {
    year: '2025',
    title: '探索设计系统',
    description: '研究光影设计语言，尝试将玻璃拟态和霓虹效果融入 Web 开发。',
  },
  {
    year: '2026',
    title: '打造个人博客',
    description: '启动 Oh My Blog 项目，整合所学技术，建立个人技术品牌。',
  },
]

onMounted(() => {
  loadTechStack()
})
</script>

<style scoped lang="scss">
.about-page {
  padding: calc(64px + var(--space-3xl)) 0 var(--space-3xl);
}

.profile-section {
  margin-bottom: var(--space-3xl);
}

.profile-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--space-xl);
  padding: var(--space-3xl);
  text-align: center;

  @media (min-width: 768px) {
    flex-direction: row;
    text-align: left;
  }
}

.profile-avatar {
  flex-shrink: 0;
}

.avatar-placeholder {
  width: 120px;
  height: 120px;
  border-radius: var(--radius-full);
  background: var(--gradient-hero);
  border: 2px solid var(--color-border);
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--color-primary);
}

.profile-info {
  display: flex;
  flex-direction: column;
  gap: var(--space-sm);
}

.profile-name {
  font-size: var(--font-size-display);
  font-weight: 700;
  color: var(--color-foreground);
}

.profile-title {
  font-size: var(--font-size-title);
  font-weight: 500;
}

.profile-bio {
  font-size: var(--font-size-body-lg);
  color: var(--color-muted);
  line-height: 1.7;
  max-width: 600px;
}

.profile-social {
  display: flex;
  gap: var(--space-md);
  margin-top: var(--space-sm);
}

.social-link {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: var(--radius-md);
  color: var(--color-muted);
  border: 1px solid var(--color-border);
  transition: color var(--duration-fast), border-color var(--duration-fast), box-shadow var(--duration-fast);

  &:hover {
    color: var(--color-primary);
    border-color: var(--color-primary);
    box-shadow: var(--glow-primary);
  }
}

// 技术栈
.tech-section {
  margin-bottom: var(--space-3xl);
}

.section-title {
  font-size: var(--font-size-headline);
  font-weight: 700;
  color: var(--color-foreground);
  margin-bottom: var(--space-xl);
  text-align: center;
}

.tech-grid {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: var(--space-lg);

  @media (min-width: 640px) {
    grid-template-columns: repeat(2, 1fr);
  }
}

.tech-category {
  padding: var(--space-lg);

  .category-name {
    display: flex;
    align-items: center;
    gap: var(--space-sm);
    font-size: var(--font-size-title);
    font-weight: 600;
    color: var(--color-foreground);
    margin-bottom: var(--space-md);
  }
}

.tech-list {
  display: flex;
  flex-wrap: wrap;
  gap: var(--space-xs);
}

.tech-item {
  padding: var(--space-xs) var(--space-md);
  border-radius: var(--radius-sm);
  background: rgba(59, 130, 246, 0.08);
  color: var(--color-primary);
  font-size: var(--font-size-body);
  font-family: var(--font-code);
  transition: background var(--duration-fast), transform var(--duration-fast);

  &:hover {
    background: rgba(59, 130, 246, 0.15);
    transform: translateY(-1px);
  }
}

.loading-grid {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: var(--space-lg);

  @media (min-width: 640px) {
    grid-template-columns: repeat(2, 1fr);
  }
}

.loading-card {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--space-3xl);
  min-height: 150px;
}

.loading-spinner {
  width: 32px;
  height: 32px;
  border: 3px solid var(--color-border);
  border-top-color: var(--color-primary);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

.empty-state {
  text-align: center;
  padding: var(--space-2xl);
  color: var(--color-muted);
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

// 时间线
.timeline-section {
  margin-bottom: var(--space-3xl);
}

.timeline {
  position: relative;
  display: flex;
  flex-direction: column;
  gap: var(--space-lg);

  &::before {
    content: '';
    position: absolute;
    left: 15px;
    top: 0;
    bottom: 0;
    width: 2px;
    background: var(--color-border);

    @media (min-width: 768px) {
      left: 50%;
      transform: translateX(-50%);
    }
  }
}

.timeline-item {
  position: relative;
  display: flex;
  align-items: flex-start;
  gap: var(--space-lg);
  padding-left: var(--space-xl);

  @media (min-width: 768px) {
    padding-left: 0;
    justify-content: center;
  }
}

.timeline-dot {
  position: absolute;
  left: 8px;
  width: 16px;
  height: 16px;
  border-radius: var(--radius-full);
  background: var(--color-primary);
  border: 3px solid var(--color-background);
  box-shadow: var(--glow-primary);
  flex-shrink: 0;

  @media (min-width: 768px) {
    left: 50%;
    transform: translateX(-50%);
  }
}

.timeline-content {
  padding: var(--space-lg);
  max-width: 400px;

  @media (min-width: 768px) {
    width: calc(50% - var(--space-xl));

    .timeline-item:nth-child(odd) & {
      margin-right: auto;
      text-align: right;
    }

    .timeline-item:nth-child(even) & {
      margin-left: auto;
    }
  }
}

.timeline-year {
  font-size: var(--font-size-caption);
  font-weight: 600;
  color: var(--color-primary);
  font-family: var(--font-code);
}

.timeline-title {
  font-size: var(--font-size-title);
  font-weight: 600;
  color: var(--color-foreground);
  margin-top: var(--space-xs);
}

.timeline-desc {
  font-size: var(--font-size-body);
  color: var(--color-muted);
  margin-top: var(--space-xs);
  line-height: 1.6;
}
</style>
