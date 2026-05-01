# Oh My Blog

> 基于光影设计语言的个人作品集与博客系统 —— 展示全栈技术能力、项目经历与专业文章。

[//]: # "screenshot-placeholder"

---

## ✨ 项目介绍

`oh-my-blog` 是一个现代化的个人博客与作品集站点，采用**光影设计语言**（Light & Shadow Design System），融合玻璃拟态、霓虹光晕与细腻阴影，打造沉浸式的阅读与浏览体验。

**当前版本**：V1.0（基础版本）

**核心特性**：
- 🎨 光影设计系统 —— 玻璃拟态 UI + 暗色/亮色主题切换
- 📝 Markdown 博客文章 —— 后端驱动，支持标签筛选与归档
- 🚀 项目展示 —— 技术栈标签 + iframe Demo 演示
- 📊 实时访客统计 —— SSE 在线人数 + PV/UV 统计
- 🔐 JWT 管理员认证 —— Spring Security + RBAC

---

## 🛠 技术栈

### 前端
| 技术 | 版本 | 说明 |
|------|------|------|
| Nuxt 3 | ^3.21 | Vue 生态、SSG/SSR/Nitro |
| Vue 3 | ^3.5 | 组合式 API |
| TypeScript | ^5.9 | 全项目类型安全 |
| Tailwind CSS | ^3.4 | 原子化样式 |
| SCSS / Sass | ^1.99 | 全局样式层（7 文件架构）|
| Pinia | ^2.3 | 状态管理（主题持久化）|
| @nuxt/content | ^2.13 | Markdown 内容渲染 |

### 后端
| 技术 | 版本 | 说明 |
|------|------|------|
| Java 17 | LTS | 运行时 |
| Spring Boot | ^3.2.5 | 主框架 |
| MyBatis Plus | ^3.5.5 | ORM + 分页 |
| Spring Security | ^6.2 | JWT 认证 |
| JJWT | 0.12.5 | JWT 实现 |

### 中间件
| 技术 | 用途 |
|------|------|
| MySQL 8.0 | 主数据库 |
| Redis | 缓存、访客会话、SSE 管理 |

---

## 🚀 快速开始

### 环境准备

1. **MySQL 8.0**（端口 3306，账号 `root` / `123456`）
2. **Redis**（端口 6379，Windows 可使用 Memurai）
3. **Java 17** + **Maven 3.9**
4. **Node.js 18** + **pnpm**

### 数据库初始化

```bash
# 首次启动前执行建表脚本
mysql -u root -p < backend/src/main/resources/db/init.sql
```

### 启动后端

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

- 健康检查：http://localhost:8080/api/v1/health
- Swagger UI：http://localhost:8080/swagger-ui.html

### 启动前端

```bash
cd frontend
pnpm install
pnpm run dev
```

- 开发服务器：http://localhost:3000

---

## 📁 项目结构

```
oh-my-blog/
├── backend/              # Spring Boot 后端
│   ├── src/main/java/    # 控制器、服务、实体、Mapper
│   ├── src/main/resources/
│   │   ├── db/init.sql   # 数据库初始化
│   │   └── mapper/       # MyBatis XML
│   └── pom.xml
├── frontend/             # Nuxt 3 前端
│   ├── components/       # Vue 组件（layout/ui）
│   ├── composables/      # API 封装（useApi.ts）
│   ├── pages/            # 文件路由
│   ├── styles/           # 光影设计系统（7 文件架构）
│   └── nuxt.config.ts
├── docs/plan/            # 6 份阶段规划文档
├── docker-compose.yml    # 生产环境编排
└── README.md
```

---

## 📡 API 概览

| 端点 | 方法 | 功能 |
|------|------|------|
| `/api/v1/health` | GET | 健康检查 |
| `/api/v1/articles` | GET | 文章列表分页 |
| `/api/v1/articles/{slug}` | GET | 文章详情（自动+1阅读量）|
| `/api/v1/articles/tags` | GET | 标签云 |
| `/api/v1/projects` | GET | 项目列表分页 |
| `/api/v1/projects/{slug}` | GET | 项目详情 |
| `/api/v1/techstack` | GET | 技术栈分组 |
| `/api/v1/visitor/sse` | GET | SSE 实时在线人数 |
| `/api/v1/visitor/pv` | POST | 记录页面浏览量 |
| `/api/v1/visitor/total` | GET | 总访问统计 |
| `/api/v1/admin/login` | POST | 管理员登录（JWT）|

---

## 🗺 路线图

- [x] 基础前后端框架搭建
- [x] 光影设计系统实现
- [x] 核心页面开发（首页/博客/项目/关于/联系）
- [x] 后端 REST API + JWT 认证
- [x] 前后端 API 对接
- [ ] 管理后台界面
- [ ] 评论系统
- [ ] RSS 订阅
- [ ] Lighthouse 性能优化
- [ ] CI/CD 自动化部署

---

## 📄 许可证

[MIT](LICENSE)
