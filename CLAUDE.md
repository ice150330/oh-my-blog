# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

> **详细版本请参阅 [AGENTS.md](./AGENTS.md)**，该文件包含更全面的 AI 代理工作规范。
> **注意**：AGENTS.md 描述的是计划中的全栈架构（Nuxt 3 + Spring Boot），但当前仓库**实际只有前端**存在，后端尚未初始化。

## Project Overview

`oh-my-blog` —— 个人作品集与博客站点（Portfolio V3.0）。
**当前状态**：纯静态前端 SPA，所有数据内置于源码中，无后端服务。

## Tech Stack (Actual)

| 技术 | 版本 | 说明 |
| ---- | ---- | ---- |
| Vue | ^3.5 | 组合式 API，单文件组件 |
| Vite | ^8.0 | 构建工具 |
| TypeScript | ~6.0 | 严格模式 |
| Vue Router | ^4.6 | `createWebHistory()` |
| Pinia | ^3.0 | 主题与语言状态 |
| Vue I18n | ^9.14 | 双语切换（zh/en） |
| Tailwind CSS | ^3.4 | 原子化样式 |
| Sass | ^1.99 | 设计 Token 与全局样式 |
| GSAP | ^3.15 | 动画 + ScrollTrigger |

包管理器为 **npm**（`package-lock.json` 已存在）。

## Repository Structure (Actual)

```text
oh-my-blog/
├── frontend/                    # Vue 3 + Vite 前端（唯一存在的应用代码）
│   ├── src/
│   │   ├── views/               # 页面级组件（Home / Projects / Blog / About / TechStack / ProjectDetail / BlogDetail）
│   │   ├── components/
│   │   │   ├── layout/          # AppLayout, NavBar, AppFooter
│   │   │   └── ui/              # 可复用 UI 组件（ProjectCard, ArticleCard, TagChip, etc.）
│   │   ├── data/                # 静态数据源（projects.ts, articles.ts, techStack.ts）
│   │   ├── composables/         # useTilt, useGsapAnimation, useCursorGlow
│   │   ├── stores/              # Pinia — theme.ts, lang.ts
│   │   ├── locales/             # Vue I18n 消息文件（zh.json, en.json）
│   │   ├── router/              # Vue Router 配置
│   │   ├── types/               # TypeScript 类型定义
│   │   └── styles/              # SCSS 全局样式
│   │       ├── variables.scss   # 设计 Token（颜色、字体、阴影、间距）
│   │       ├── _animations.scss # 关键帧动画 + prefers-reduced-motion
│   │       └── main.scss        # 入口（导入所有模块）
│   ├── index.html
│   ├── vite.config.ts           # @alias → src/, SCSS additionalData 注入 variables.scss
│   └── package.json
├── AGENTS.md                    # AI 代理详细规范（含计划中的后端架构）
├── docs/plan/                   # 7 份中文规划文档
└── .gitignore
```

> **不存在 `backend/` 目录**。后端 Spring Boot 代码仅在 AGENTS.md 和计划文档中描述。

## Key Architecture Decisions

### 1. 纯静态数据层

所有项目、文章、技术栈数据直接写在 `src/data/*.ts` 中，运行时无 API 请求。

- `projects.ts` — 12 个真实项目，含中英文字段
- `articles.ts` — 技术文章
- `techStack.ts` — 技能、技术分类、时间线、统计数据

### 2. 双层国际化

- **UI 文本**：Vue I18n 的 `zh.json` / `en.json`，通过 `t()` 在模板中使用
- **内容数据**：数据文件中的 `*En` 字段（如 `titleEn`, `descriptionEn`），组件中按 `locale === 'zh'` 条件渲染

### 3. 主题系统

- CSS 变量定义在 `variables.scss` 的 `:root` 和 `[data-theme='dark']` 中
- `useThemeStore` 切换时设置 `document.documentElement.setAttribute('data-theme', val)` 并持久化到 `localStorage`
- `lang.ts` 中的 `useSyncLocale()` 需在组件中调用以同步 Pinia locale 与 Vue I18n locale

### 4. 动画生命周期管理

- `main.ts` 中全局注册 `gsap.registerPlugin(ScrollTrigger)` 一次
- `router.beforeEach` 中调用 `gsap.killTweensOf('*')` 和 `ScrollTrigger.getAll().forEach(st => st.kill())`，防止路由切换后旧页面动画残留
- `router.afterEach` 中通过 `requestAnimationFrame(() => ScrollTrigger.refresh())` 重新计算触发位置
- 所有动画相关 composable 和工具函数检查 `prefers-reduced-motion: reduce`

### 5. `useTilt` 支持两种 ref

`useTilt(elRef, max)` 的 `elRef.value` 既可以是 `HTMLElement`，也可以是 Vue 组件实例（通过 `$el` 解包）。这是通过 `getElement()` 辅助函数实现的，修复了早期版本中仅支持原生 ref 导致的 `addEventListener` 报错。

### 6. SCSS 全局注入

`vite.config.ts` 中 `css.preprocessorOptions.scss.additionalData` 自动向每个 `.scss` 文件注入 `@use "@/styles/variables.scss" as *;`，因此组件 `<style scoped lang="scss">` 中可直接使用变量而无需手动导入。

### 7. 路由模式

使用 `createWebHistory()`（非 hash 模式）。部署到静态服务器时需要配置 SPA fallback（所有路由指向 `index.html`）。

## Build Commands

所有命令在 `frontend/` 目录下执行：

```bash
npm install
npm run dev        # → http://localhost:5173
npm run build      # vue-tsc + vite build → dist/
npm run preview    # 预览生产构建
```

> 没有 lint、test、format 脚本。项目中不存在 `.eslintrc`、`.prettierrc` 或测试文件。

## Design System

### 样式架构

双层：全局 SCSS Token + Tailwind 工具类 + 组件级 `<style scoped>`（仅布局）。

`variables.scss` 中的核心 Token：

- `--color-bg`, `--color-bg-elevated`, `--color-card` — 背景层级
- `--color-text-1`, `--color-text-2`, `--color-text-3` — 文字层级
- `--color-border`, `--color-primary`, `--color-primary-soft` — 强调色与边框
- `--glow-primary`, `--mesh-tint-1/2` — 光影效果
- `--glass-bg`, `--glass-border` — 玻璃拟态
- `--font-body`, `--font-code`, `--font-display`, `--font-accent` — 字体栈

### 分类色值

项目分类对应 CSS 变量：

- `ai` → `--category-ai` (#2563EB)
- `data` → `--category-data` (#8B5CF6)
- `business` → `--category-business` (#06B6D4)
- `algo` → `--category-algo` (#F97316)
- `research` → 使用 `#A855F7`
- `meta` → `--category-meta` (#71717A)

## Content Strategy

项目详情页、关于页的数据全部来自 `data/` 目录的静态导出。
时间线、技能评分、统计数据同样在 `techStack.ts` 中维护。
任何内容更新（新增项目、文章、时间线事件）都需修改对应数据文件并重新构建。

## Deployment Notes

- 构建产物在 `frontend/dist/`
- 使用 `createWebHistory()`，部署时需要服务器配置 SPA fallback
- `public/` 中的字体和图标直接复制到 `dist/`，不经过构建处理
