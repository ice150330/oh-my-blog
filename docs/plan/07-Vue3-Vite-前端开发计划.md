# oh-my-blog Vue 3 + Vite 前端开发计划

> 本计划基于 `pencil/mylogo.pen` 设计稿，将技术栈从 Nuxt 3 调整为 **Vue 3 + Vite** 纯前端架构。

## 一、技术栈确认

| 层级 | 技术选型 | 备注 |
|------|---------|------|
| 框架 | Vue 3.5 + Composition API | 响应式、TSX 支持 |
| 构建 | Vite 8 | 极速 HMR、ESM 原生 |
| 路由 | Vue Router 4 | History 模式，7 个页面 |
| 状态 | Pinia 3 | 主题、全局数据缓存 |
| 样式 | SCSS + Tailwind CSS v4 | 设计 Token 变量化 |
| 图标 | Lucide Vue + 自定义 SVG Sprite | 与 .pen 设计一致 |
| 字体 | 本地 woff2 子集 + font-display: swap | material/font/ 下已有 |
| 动画 | CSS Transitions + GSAP (按需) | 优先 CSS，复杂动效用 GSAP |
| 内容 | Markdown 文件 + JSON 数据 | 无后端，纯静态 |
| 部署 | GitHub Pages / Vercel | `npm run build` 输出 dist/ |

## 二、设计系统映射

将 `pencil/mylogo.pen` 中的变量映射为 CSS 自定义属性与 SCSS 变量。

### 2.1 色彩 Token（双主题）

```scss
// src/styles/variables.scss
:root {
  --color-bg: #FAFAFA;
  --color-bg-elevated: #FFFFFF;
  --color-card: #FFFFFF;
  --color-text-1: #18181B;
  --color-text-2: #52525B;
  --color-text-3: #A1A1AA;
  --color-border: #E4E4E7;
  --color-primary: #2563EB;
  // ...
}

[data-theme="dark"] {
  --color-bg: #0F172A;
  // ...
}
```

### 2.2 分类徽标色（5 种）

| 分类 | Token | Light / Dark |
|------|-------|-------------|
| AI 智能体 | `$category-ai` | `#2563EB` / `#3B82F6` |
| 数据分析 | `$category-data` | `#8B5CF6` / `#A78BFA` |
| 业务系统 | `$category-business` | `#06B6D4` / `#22D3EE` |
| 算法仿真 | `$category-algo` | `#F97316` / `#FB923C` |
| 元项目 | `$category-meta` | `#71717A` / `#A1A1AA` |

### 2.3 字体体系

| 角色 | 字体 | 用途 |
|------|------|------|
| Display | Library 3 am | 英文大标题、数字 |
| Body | Source Han Sans SC | 正文、UI 文本 |
| Code | JetBrains Mono | 代码块、标签 |
| Accent | DongManErCiYuan | 中文副标题、引用 |
| Pixel | PixelTi | Logo、像素装饰 |

## 三、组件架构

按 `.pen` 中的 `reusable: true` 组件，一一映射为 Vue 组件。

### 3.1 布局组件（Layout）

| 组件 | 文件 | 复杂度 |
|------|------|--------|
| AppLayout | `components/layout/AppLayout.vue` | 低 |
| NavBar | `components/layout/NavBar.vue` | 中（含主题切换、移动端菜单） |
| AppFooter | `components/layout/AppFooter.vue` | 低 |

### 3.2 原子组件（UI）

| 组件 | 文件 | 说明 |
|------|------|------|
| Logo | `components/ui/Logo.vue` | 像素体 + 色块 |
| ThemeToggle | `components/ui/ThemeToggle.vue` | Pill 开关，切换 data-theme |
| PrimaryButton | `components/ui/PrimaryButton.vue` | Props: text, href/to |
| GhostButton | `components/ui/GhostButton.vue` | Props: text, href/to |
| TagChip | `components/ui/TagChip.vue` | Props: label |
| CategoryBadge | `components/ui/CategoryBadge.vue` | Props: category (ai/data/business/algo/meta) |
| SectionTitle | `components/ui/SectionTitle.vue` | 英文 display + 中文 accent + 装饰线 |
| IconBadge | `components/ui/IconBadge.vue` | 40px 圆形 + icon |
| StatItem | `components/ui/StatItem.vue` | 数字 + 标签 |
| SkillBar | `components/ui/SkillBar.vue` | 标签 + 百分比进度条 |
| TocItem | `components/ui/TocItem.vue` | 左侧色条 + 文字 |
| TimelineNode | `components/ui/TimelineNode.vue` | 圆点 + 竖线 + 卡片 |

### 3.3 复合组件

| 组件 | 文件 | 说明 |
|------|------|------|
| ProjectCard | `components/ui/ProjectCard.vue` | 封面渐变 + 徽标 + 标题 + 描述 + 标签行 |
| ArticleCard | `components/ui/ArticleCard.vue` | 左封面 160px + 右标题/摘要/日期 |
| SidebarCard | `components/ui/SidebarCard.vue` | 通用侧边栏容器 |
| FeaturedCarousel | `components/home/FeaturedCarousel.vue` | 首页精选项目轮播 |

### 3.4 页面级组件

每个页面对应 `views/` 下的一个目录，内部可拆分子组件：

```
views/
├── Home/
│   ├── index.vue          # 页面入口
│   ├── HeroSection.vue    # Hero 720px
│   ├── BriefIntro.vue     # 3 列 IconBadge
│   ├── FeaturedCarousel.vue
│   ├── LatestArticles.vue
│   └── CTABanner.vue
├── Projects/
│   ├── index.vue
│   ├── PageHeader.vue
│   ├── CategoryTabs.vue
│   └── ProjectGrid.vue
├── ProjectDetail/
│   ├── index.vue
│   ├── CoverSection.vue
│   ├── MetaBar.vue
│   ├── DemoPreview.vue
│   └── ArticleContent.vue
├── Blog/
│   ├── index.vue
│   ├── FilterBar.vue
│   ├── ArticleList.vue
│   └── BlogSidebar.vue
├── BlogDetail/
│   ├── index.vue
│   ├── ArticleHero.vue
│   ├── ArticleBody.vue
│   ├── TOCColumn.vue
│   └── PrevNextNav.vue
├── TechStack/
│   ├── index.vue
│   ├── RadarChart.vue     # ECharts 雷达图占位
│   ├── CategoryGrid.vue
│   └── StatsBar.vue
└── About/
    ├── index.vue
    ├── ProfileColumn.vue
    ├── IntroSection.vue
    ├── TimelineSection.vue
    ├── SkillsSection.vue
    └── ValuesSection.vue
```

## 四、页面开发顺序与里程碑

### Phase 1 — 基础设施（1 天）

- [ ] 设计 Token 全面落地（SCSS 变量 + CSS 自定义属性）
- [ ] 双主题切换系统（Pinia store + data-theme + localStorage 持久化）
- [ ] 字体加载策略（@font-face + font-display: swap）
- [ ] 全局样式重置（main.scss: scrollbar, selection, base）
- [ ] 响应式断点定义（sm/md/lg/xl）
- [ ] 图标系统（Lucide 按需导入 + sprite.svg 合并）

### Phase 2 — 原子组件库（2 天）

- [ ] 完成 12 个原子 UI 组件（Button, Tag, Badge, SectionTitle, IconBadge, StatItem, SkillBar, TocItem, TimelineNode）
- [ ] 每个组件支持 `mode: light | dark` 自动响应
- [ ] 组件 Story/Demo 页（临时用于视觉验证）

### Phase 3 — 布局 + HomePage（2 天）

- [ ] NavBar（滚动隐藏/显示、移动端汉堡菜单）
- [ ] AppFooter
- [ ] HomePage 完整实现：
  - HeroSection（打字机效果或逐字动画）
  - BriefIntro（3 列 IconBadge）
  - FeaturedCarousel（左侧选择 + 右侧展示卡）
  - LatestArticles（3 张 ArticleCard）
  - CTABanner

### Phase 4 — 项目展示体系（2 天）

- [ ] ProjectsPage（9 卡网格 + CategoryTabs 过滤）
- [ ] PriorWorkBanner（portfolio-blog-v3）
- [ ] SkillsBanner（opencode Skills v3.1）
- [ ] ProjectDetailPage（封面、MetaBar、DemoPreview iframe、正文、上下篇导航）

### Phase 5 — 博客体系（2 天）

- [ ] BlogListPage（文章列表 + 侧边栏：搜索/标签云/归档）
- [ ] BlogDetailPage（Markdown 渲染、代码高亮、TOC 锚点滚动、分享按钮）
- [ ] 文章内容采用 Markdown 文件存储（`src/content/blog/*.md`）

### Phase 6 — TechStack + About（2 天）

- [ ] TechStackPage（ECharts 雷达图、4 分类标签矩阵、Stats Bar）
- [ ] AboutPage（ProfileCard、Timeline、Skills、Values）

### Phase 7 — 动画与性能优化（1 天）

- [ ] 页面进入动画（fade-in-up + stagger）
- [ ] ScrollTrigger 滚动触发动画（GSAP）
- [ ] `prefers-reduced-motion` 支持
- [ ] Lighthouse 性能审计（目标：Performance ≥ 90）
- [ ] 图片/字体懒加载与预加载策略

### Phase 8 — 部署上线（0.5 天）

- [ ] GitHub Actions 自动构建部署到 GitHub Pages
- [ ] 或 Vercel 自动部署
- [ ] 自定义域名配置（如有）

## 五、数据层方案（纯前端）

无后端，所有数据静态化：

### 5.1 项目数据

```ts
// src/data/projects.ts
export interface Project {
  id: string
  title: string
  category: 'ai' | 'data' | 'business' | 'algo' | 'meta'
  tags: string[]
  description: string
  coverGradient: [string, string]  // mesh-tint 色值
  demoUrl?: string
  repoUrl?: string
  slug: string
}

export const projects: Project[] = [
  { id: '1', title: 'AI 模拟面试官', category: 'ai', tags: ['Vue3','FastAPI','Neo4j','Monaco'], description: '...', slug: 'ai-mock-interviewer', coverGradient: ['#DBEAFE','#F3E8FF'] },
  // ... 共 11 个
]
```

### 5.2 文章数据

文章元数据用 JSON，正文用 Markdown：

```ts
// src/data/articles.ts
export interface Article {
  slug: string
  title: string
  category: string
  summary: string
  date: string
  readingTime: number
  tags: string[]
}
```

Markdown 文件放在 `src/content/blog/{slug}.md`，通过 `vite-plugin-md` 或动态 import 加载。

### 5.3 技术栈数据

```ts
// src/data/techStack.ts
export const skills = [
  { name: 'Vue 3 / Nuxt 3', level: 95 },
  { name: 'React / Next.js', level: 80 },
  { name: 'TypeScript', level: 90 },
  { name: 'ECharts / D3.js', level: 85 },
  { name: 'Python / FastAPI', level: 75 },
  { name: 'AI / LLM 工程', level: 70 },
]
```

## 六、关键实现决策

### 6.1 主题切换

```vue
<!-- AppLayout.vue -->
<script setup>
import { useThemeStore } from '@/stores/theme'
const theme = useThemeStore()
</script>

<template>
  <div :data-theme="theme.mode">
    <NavBar />
    <RouterView />
    <AppFooter />
  </div>
</template>
```

Pinia store 读取 `localStorage`，切换时更新 `data-theme`，所有组件通过 CSS 变量自动响应。

### 6.2 项目轮播（FeaturedCarousel）

状态管理：

```ts
const activeIndex = ref(0)
const project = computed(() => featuredProjects[activeIndex.value])
```

左侧：进度指示器 + 标题 + 描述 + 标签 + 按钮 + 导航点  
右侧：大卡片展示当前项目的图标、名称、浮动标签。

### 6.3 Markdown 渲染

使用 `markdown-it` + `highlight.js`：

```ts
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'

const md = new MarkdownIt({
  highlight: (code, lang) => {
    if (lang && hljs.getLanguage(lang)) {
      return hljs.highlight(code, { language: lang }).value
    }
    return code
  }
})
```

### 6.4 ECharts 雷达图

TechStackPage 使用 `vue-echarts` 按需导入：

```ts
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { RadarChart } from 'echarts/charts'
import { TitleComponent, LegendComponent } from 'echarts/components'

use([CanvasRenderer, RadarChart, TitleComponent, LegendComponent])
```

## 七、文件目录结构（目标态）

```
frontend/
├── public/
│   └── favicon.svg
├── src/
│   ├── assets/
│   │   └── material/           # 字体 + 图标（保留）
│   ├── components/
│   │   ├── layout/             # AppLayout, NavBar, AppFooter
│   │   ├── ui/                 # 原子组件
│   │   ├── home/               # HomePage 专属复合组件
│   │   ├── project/            # ProjectsPage 专属
│   │   ├── blog/               # BlogPage 专属
│   │   └── charts/             # ECharts 封装
│   ├── composables/            # useTheme, useScrollAnimation 等
│   ├── content/
│   │   └── blog/               # Markdown 文章源文件
│   ├── data/
│   │   ├── projects.ts         # 11 个项目数据
│   │   ├── articles.ts         # 文章元数据
│   │   └── techStack.ts        # 技术栈数据
│   ├── router/
│   │   └── index.ts            # 7 条路由
│   ├── stores/
│   │   └── theme.ts            # Pinia 主题 store
│   ├── styles/
│   │   ├── variables.scss      # 设计 Token
│   │   ├── main.scss           # 全局样式入口
│   │   ├── mixins.scss         # 工具 mixin
│   │   └── animations.scss     # 动画 keyframes
│   ├── types/
│   │   └── index.ts            # 全局 TS 类型
│   ├── views/                  # 7 个页面
│   │   ├── Home/
│   │   ├── Projects/
│   │   ├── ProjectDetail/
│   │   ├── Blog/
│   │   ├── BlogDetail/
│   │   ├── TechStack/
│   │   └── About/
│   ├── App.vue
│   └── main.ts
├── index.html
├── package.json
├── vite.config.ts
├── tsconfig.app.json
├── tailwind.config.js
└── postcss.config.js
```

## 八、预计总工期

| 阶段 | 内容 | 预估时间 |
|------|------|---------|
| Phase 1 | 基础设施 | 1 天 |
| Phase 2 | 原子组件库 | 2 天 |
| Phase 3 | 布局 + HomePage | 2 天 |
| Phase 4 | 项目展示体系 | 2 天 |
| Phase 5 | 博客体系 | 2 天 |
| Phase 6 | TechStack + About | 2 天 |
| Phase 7 | 动画与性能优化 | 1 天 |
| Phase 8 | 部署上线 | 0.5 天 |
| **总计** | | **约 12–14 天** |

## 九、下一步行动

1. **你是否确认按此计划执行？** 确认后从 Phase 1 开始逐一实现。
2. **是否需要调整工期优先级？** 例如先完成 HomePage + ProjectsPage 作为 MVP 上线，再补齐博客和 About。
3. **内容数据来源：** 11 个项目数据与 8 篇文章的 Markdown 内容需要我帮你一并写入，还是你后续自行补充？
