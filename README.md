# Oh My Blog

> 基于光影设计语言的个人作品集与博客站点 —— 展示全栈技术能力、项目经历与专业文章。

## 项目介绍

`oh-my-blog` 是一个现代化的个人博客与作品集站点，采用**光影设计语言**（Light & Shadow Design System），融合玻璃拟态、霓虹光晕与细腻阴影，打造沉浸式的阅读与浏览体验。

**当前版本**：V3.0（Vue 3 + Vite 纯静态前端）

**核心特性**：

- 光影设计系统 —— 玻璃拟态 UI + 暗色/亮色主题切换
- 12 个真实项目展示 —— 技术栈标签 + 详细项目复盘
- 8 篇技术文章 —— 涵盖 AI 工程、数据可视化、算法仿真等方向
- 双语切换 —— 中文/英文完整国际化
- 细腻动画 —— GSAP ScrollTrigger 滚动触发动画 + 页面过渡动画
- 响应式布局 —— 覆盖桌面端与移动端场景

## 技术栈

| 技术 | 版本 | 说明 |
| ---- | ---- | ---- |
| Vue | ^3.5 | 组合式 API，单文件组件 |
| Vite | ^8.0 | 构建工具 |
| TypeScript | ~6.0 | 全项目类型安全 |
| Vue Router | ^4.6 | 前端路由 |
| Pinia | ^3.0 | 状态管理（主题/语言持久化） |
| Vue I18n | ^9.14 | 双语切换 |
| Tailwind CSS | ^3.4 | 原子化样式 |
| Sass | ^1.99 | 全局样式层 |
| GSAP | ^3.15 | 动画与滚动触发 |

## 快速开始

### 环境要求

- Node.js 18+
- npm

### 启动开发服务器

```bash
cd frontend
npm install
npm run dev
```

开发服务器：<http://localhost:5173>

### 生产构建

```bash
cd frontend
npm run build
```

构建产物输出至 `frontend/dist/`。

> 部署到静态服务器时需要配置 SPA fallback（所有路由指向 `index.html`），因为项目使用 `createWebHistory()` 路由模式。

## 项目结构

```text
oh-my-blog/
├── frontend/              # Vue 3 + Vite 前端
│   ├── src/
│   │   ├── views/         # 页面级组件
│   │   ├── components/    # 可复用组件（layout/ui）
│   │   ├── data/          # 静态数据（projects.ts / articles.ts / techStack.ts）
│   │   ├── composables/   # 组合式函数
│   │   ├── stores/        # Pinia 状态管理
│   │   ├── locales/       # 国际化文案
│   │   ├── router/        # 路由配置
│   │   ├── types/         # TypeScript 类型定义
│   │   └── styles/        # SCSS 全局样式
│   ├── index.html
│   ├── vite.config.ts
│   └── package.json
├── docs/plan/             # 阶段规划文档
├── AGENTS.md              # AI 编码代理指南
├── CLAUDE.md              # Claude Code 工作指南
└── README.md
```

## 内容来源

所有项目、文章、技术栈数据均来自 `frontend/src/data/` 目录的静态 TypeScript 文件：

- `projects.ts` — 12 个真实项目数据（含中英双语字段）
- `articles.ts` — 8 篇技术文章
- `techStack.ts` — 技能评分、技术分类、时间线、统计数据

更新内容时直接修改对应数据文件并重新构建即可。

## 设计系统

- **设计 Token**：`frontend/src/styles/variables.scss`
- **主题切换**：CSS 变量 + `data-theme` 属性（light / dark）
- **动画策略**：GSAP ScrollTrigger 用于滚动触发，CSS transition 用于页面切换
- **无障碍**：支持 `prefers-reduced-motion: reduce`

## 许可证

[MIT](LICENSE)
