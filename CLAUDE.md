# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

> **详细版本请参阅 [AGENTS.md](./AGENTS.md)**，该文件包含更全面的 AI 代理工作规范。

## Project Overview

Personal portfolio/blog website ("oh-my-blog" / Portfolio V3.0) — full-stack data intelligence engineering showcase.
**Current status**: planning stage, no application scaffolds exist yet. Only `docs/plan/` and `frontend/material/` are real.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend | Nuxt 3 (SSG), Vue 3, TypeScript, pnpm, SCSS + Tailwind + Element Plus, GSAP + ECharts, Pinia |
| Backend | Java 17, Spring Boot 3.2, MyBatis Plus 3.5, Spring Security 6.2 + JWT, Maven 3.9 |
| Middleware | MySQL 8.0, Redis 7 (Memurai on Windows dev), Nginx 1.25 |
| CI/CD | GitHub Actions |
| Deploy | Docker Compose (production only) + Ubuntu 22.04 |

## Repository Structure (Actual)

```
oh-my-blog/
├── AGENTS.md                    # Comprehensive AI agent guide — read this for detailed specs
├── docs/plan/                   # 6-phase plan in Chinese
│   ├── 01-项目基础与技术架构.md
│   ├── 02-设计系统与前端框架.md
│   ├── 03-核心页面开发.md
│   ├── 04-项目展示与内嵌系统.md
│   ├── 05-后端服务与数据层.md
│   └── 06-部署与运维方案.md
├── frontend/                    # Nuxt 3 project root (not yet initialized)
│   └── material/                # Design assets only
│       ├── font/                # 6 subsetted fonts (woff2) + _fonts.scss
│       └── icon/                # 42 SVG icons + sprite.svg
├── backend/                     # Spring Boot project root (not yet initialized)
└── .gitignore                   # Generic Java template — needs updating
```

## Architecture

```
oh-my-blog/
├── frontend/                    # Nuxt 3 SSG — directly at repo root level
│   ├── content/                 # Markdown content (V1 via Nuxt Content)
│   ├── components/{layout,ui,charts,animation,home,project}/
│   ├── pages/{index,tech-stack,projects,about,timeline,articles}/  # Kebab-case
│   ├── composables/             # useTheme, useDevice, etc.
│   ├── stores/                  # Pinia
│   ├── styles/                  # Global SCSS: _variables, _mixins, _animations, etc.
│   ├── material/                # Font + icon assets (already exists)
│   ├── public/demos/            # Sub-project builds (gitignored)
│   ├── nuxt.config.ts
│   └── package.json
├── backend/                     # Spring Boot 3.2 — directly at repo root level
│   ├── src/main/java/com/portfolio/{config,controller,service,mapper,entity,dto,vo,security,common}/
│   ├── src/main/resources/{application*.yml, init.sql}
│   └── pom.xml
└── nginx/portfolio.conf
```

## Key Conventions

### Language & Comments
- **All comments must be in Chinese** — exceptions: third-party API references, open-source licenses, standard algorithm names
- Site language: `zh-CN`

### File Naming
- Vue components: **PascalCase** (`AppHeader.vue`)
- TypeScript composables/utils: **camelCase** (`useTheme.ts`)
- SCSS files: **camelCase** (`_variables.scss`)
- Nuxt pages: **kebab-case** (`tech-stack.vue`, `[slug].vue`)
- **Forbidden**: snake_case, all-lowercase-no-separator

### Styling Architecture
Two-layer: **global styles** (`styles/`) + **component-scoped** (`<style scoped>`).

`styles/` loaded via `nuxt.config.ts` → `css: ['@/styles/main.scss']`:
- `_variables.scss` — all design tokens (colors, spacing, shadows, glow, fonts)
- `_lightTheme.scss` / `_darkTheme.scss` — theme overrides
- `_mixins.scss` — glassmorphism, glow effects
- `_animations.scss` — keyframes + utility classes
- `_globals.scss` — html/body base, scrollbar, selection
- `_utilities.scss` — custom classes beyond Tailwind
- `main.scss` — imports all in order

**Rules**:
1. **No hardcoded colors** — always `var(--color-primary)`
2. **No redefining animations in components** — use utility classes like `.animate-fade-in-up`
3. Component `<style scoped>` only for layout/positioning — no colors, fonts, shadows
4. All glow/glass effects through `_mixins.scss`
5. All components auto-import variables + mixins via `vite.css.preprocessorOptions.scss.additionalData`

### Design Language
"Light & Shadow Design Language" — glassmorphism + layered glows. Dark mode = "dark neon" style.
Performance: ≤10 simultaneous `box-shadow`/`drop-shadow` on screen.

### Animation
- Simple entry → CSS classes (`.animate-fade-in-up`, `.stagger-N` delays)
- Scroll-triggered → GSAP ScrollTrigger
- Page transitions → GSAP Timeline
- GSAP only in `<ClientOnly>` components
- Must support `prefers-reduced-motion: reduce`

## Development Commands

### Environment (Windows)
```powershell
# Install MySQL 8.0 (port 3306, root/123456) and Memurai (Windows Redis, port 6379)
# Manually run init.sql to create tables on first start
```

### Frontend (from `frontend/`)
```bash
pnpm install
pnpm run dev          # → http://localhost:3000
pnpm run build        # SSG → .output/public/
pnpm run lint
pnpm run test         # Playwright E2E
```

### Backend (from `backend/`)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev   # → http://localhost:8080
mvn test              # JUnit + MockMvc
mvn -B verify         # CI: lint + test + package
```

### Health Check
```bash
curl http://localhost:8080/api/v1/health   # → {"code":200,"message":"success"}
```

## API Pattern

Uniform `Result<T>` response for all endpoints:
```json
{ "code": 200, "message": "success", "data": {}, "timestamp": 1714291200000 }
```
Codes: 200/400/401/403/404/500. Public endpoints `/api/v1/*`; admin `/api/v1/admin/*` (JWT required).

## Project Detail Strategy (V1)

Projects displayed via **iframe** with `sandbox` attribute, loaded from `/demos/<project-name>/`. Sub-projects must build with **Hash router** (`VITE_ROUTER_MODE=hash`) to prevent URL hijacking. Mobile: screenshot gallery fallback.

## Content Strategy
- **V1**: Local Markdown via Nuxt Content module
- **V2**: Backend REST API (`/api/v1/*`)

## First-Time Setup (when starting Phase 1)

1. Update `.gitignore` — add `node_modules/`, `.env`, `public/demos/`, `target/`, `.output/`, `.nuxt/`
2. Init frontend: `npx nuxi@latest init frontend` + pin Node 18.20.0 / pnpm 9 via volta
3. Init backend: Maven archetype in `backend/` with Spring Boot 3.2.5 parent
4. Install MySQL 8.0 + Memurai locally
5. Create `backend/src/main/resources/init.sql`
