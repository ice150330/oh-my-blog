# oh-my-blog —— AI 编码代理项目指南

> 本文件面向 AI 编码代理。记录项目当前状态、技术栈、代码规范与注意事项。

---

## 一、项目概述

`oh-my-blog` 是一个个人作品集与博客站点（Portfolio Blog），用于展示站长的全栈技术能力、项目经历与专业文章。

**当前状态（V3.0 开发阶段）**
- `frontend/`：**已完成 V1 基础版本**，Nuxt 3 + 光影设计系统 + 全部页面路由
- `backend/`：**已完成 V1 基础版本**，Spring Boot 3.2 + JWT + SSE + MyBatis Plus
- `docs/plan/`：6 份详细的中文规划文档，涵盖完整路线图
- **前后端尚未完成 API 对接**，前端当前使用 Mock 数据，后端 API 已就绪

---

## 二、技术栈

### 前端
| 技术 | 版本 | 说明 |
|------|------|------|
| Nuxt 3 | ^3.21 | Vue 生态、原生 SSG/SSR/Nitro |
| Vue 3 | ^3.5 | 组合式 API |
| TypeScript | ^5.9 | 全项目类型安全 |
| Tailwind CSS | ^3.4 | 原子化样式 + 自定义光影 Token |
| SCSS / Sass | ^1.99 | 全局样式层（7 文件架构） |
| Pinia | ^2.3 | 状态管理（主题持久化） |
| @nuxt/content | ^2.13 | Markdown 内容渲染 |
| pnpm | ^9.15 | 包管理器 |

### 后端
| 技术 | 版本 | 说明 |
|------|------|------|
| Java 17 | LTS | 运行时 |
| Spring Boot | ^3.2.5 | 主框架 |
| MyBatis Plus | ^3.5.5 | ORM + 分页 |
| Spring Security | ^6.2 | JWT 认证 |
| JJWT | 0.12.5 | JWT 实现 |
| Maven | ^3.9 | 构建工具 |

### 中间件与基础设施
| 技术 | 版本 | 用途 |
|------|------|------|
| MySQL | 8.0 | 主数据库（账号 root / 123456） |
| Memurai | — | Windows 版 Redis（端口 6379） |
| Nginx | — | 反向代理 + 静态文件（生产环境） |
| Docker / Docker Compose | — | 仅生产环境服务器部署使用 |

---

## 三、项目目录结构

```
oh-my-blog/
├── frontend/                    # Nuxt 3 前端（已完成基础版本）
│   ├── components/
│   │   ├── layout/              # AppHeader, AppFooter
│   │   ├── ui/                  # ArticleCard, ProjectCard, IconSprite
│   │   └── animation/           # （预留）
│   ├── content/
│   │   └── blog/                # Markdown 博客文章
│   ├── layouts/
│   │   └── default.vue          # 玻璃拟态背景布局
│   ├── pages/                   # 文件路由
│   │   ├── index.vue            # 首页 Hero
│   │   ├── about.vue            # 关于页
│   │   ├── contact.vue          # 联系页
│   │   ├── blog/
│   │   │   ├── index.vue        # 博客列表
│   │   │   └── [slug].vue       # 博客详情（Markdown）
│   │   └── projects/
│   │       ├── index.vue        # 项目列表
│   │       └── [id].vue         # 项目详情（iframe Demo）
│   ├── composables/
│   │   └── useApi.ts            # API 客户端封装（待对接）
│   ├── stores/
│   │   └── theme.ts             # Pinia 主题状态（持久化）
│   ├── styles/                  # 7 文件光影设计系统
│   │   ├── _fonts.scss
│   │   ├── _variables.scss
│   │   ├── _lightTheme.scss
│   │   ├── _darkTheme.scss
│   │   ├── _mixins.scss
│   │   ├── _animations.scss
│   │   ├── _globals.scss
│   │   ├── _utilities.scss
│   │   ├── tailwind.css
│   │   └── main.scss
│   ├── types/
│   │   └── index.ts             # TypeScript 类型定义
│   ├── public/
│   │   ├── font/                # 6 款 woff2 字体
│   │   ├── icon/                # 42 个 SVG + sprite.svg
│   │   └── images/              # （预留）
│   ├── material/                # 原始设计资产备份
│   ├── nuxt.config.ts
│   └── package.json
├── backend/                     # Spring Boot 后端（已完成基础版本）
│   ├── src/main/java/com/portfolio/
│   │   ├── PortfolioApplication.java
│   │   ├── config/              # Security, MyBatisPlus, Redis, SSE, Web
│   │   ├── controller/          # 8 个 REST Controller
│   │   ├── service/             # 业务逻辑 + Impl
│   │   ├── mapper/              # MyBatis Plus Mapper
│   │   ├── entity/              # 5 个实体类
│   │   ├── dto/                 # 请求参数 DTO
│   │   ├── vo/
│   │   │   └── Result.java      # 统一响应包装
│   │   ├── security/            # JWT Token + Filter
│   │   └── common/              # 全局异常处理 + 错误码
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   ├── application-dev.yml
│   │   ├── application-prod.yml
│   │   ├── mapper/              # MyBatis XML
│   │   └── db/init.sql          # 数据库初始化 + 初始数据
│   ├── src/test/                # 集成测试
│   └── pom.xml
├── docs/
│   └── plan/                    # 6 份阶段规划文档
│       ├── 01-项目基础与技术架构.md
│       ├── 02-设计系统与前端框架.md
│       ├── 03-核心页面开发.md
│       ├── 04-项目展示与内容系统.md
│       ├── 05-后端服务与数据层.md
│       └── 06-部署运维与上线.md
├── nginx/                       # Nginx 配置（生产环境，规划中）
├── .gitignore
├── LICENSE
└── README.md
```

---

## 四、构建与运行命令

### 环境准备（Windows 本地）
```powershell
# 1. 确保 MySQL 8.0 运行（端口 3306，账号 root，密码 123456）
# 2. 确保 Memurai 运行（端口 6379）
# 3. 首次启动后端前，手动执行 init.sql 建表：
mysql -u root -p < backend/src/main/resources/db/init.sql
```

### 后端
```powershell
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
# 健康检查：http://localhost:8080/api/v1/health
# Swagger UI：http://localhost:8080/swagger-ui.html
```

### 前端
```powershell
cd frontend
pnpm install
pnpm run dev
# 开发服务器：http://localhost:3000
```

---

## 五、前后端 API 对接状态

### 后端 API 端点（已就绪）

| 端点 | 方法 | 功能 | 状态 |
|------|------|------|------|
| `/api/v1/health` | GET | 健康检查 | ✅ |
| `/api/v1/articles` | GET | 文章列表分页 | ✅ |
| `/api/v1/articles/{slug}` | GET | 文章详情（自动+1阅读量） | ✅ |
| `/api/v1/articles/tags` | GET | 标签云 | ✅ |
| `/api/v1/articles/archives` | GET | 按月归档 | ✅ |
| `/api/v1/projects` | GET | 项目列表分页 | ✅ |
| `/api/v1/projects/{slug}` | GET | 项目详情（slug 查询） | ✅ |
| `/api/v1/projects/categories` | GET | 项目分类 | ✅ |
| `/api/v1/tech-stacks` | GET | 技术栈列表 | ✅ |
| `/api/v1/visitor/sse` | GET | SSE 实时在线人数推送 | ✅ |
| `/api/v1/visitor/heartbeat` | POST | 访客心跳（Redis 计数） | ✅ |
| `/api/v1/visitor/pv` | POST | 记录页面浏览量 | ✅ |
| `/api/v1/visitor/stats?days=7` | GET | 最近 N 天访问统计 | ✅ |
| `/api/v1/visitor/total` | GET | 总访问统计 | ✅ |
| `/api/v1/admin/login` | POST | 管理员登录（JWT） | ✅ |
| `/api/v1/upload` | POST | 文件上传 | ✅ |

### 前端 API 封装（`composables/useApi.ts`）

当前前端期望的端点与后端实际端点存在以下差异，对接时需注意：

| 差异点 | 前端当前 | 后端实际 | 处理方式 |
|--------|---------|---------|---------|
| **响应解包** | 直接返回 `T` | 包装为 `Result<T>`（含 `code/message/data/timestamp`） | 前端需修改 `useApi.ts`，从 `response.data` 中提取 |
| **项目详情** | `/projects/{id}` | `/projects/{slug}` | 前端路由改为 `[slug].vue`，或用 slug 查询 |
| **登录** | `/auth/login` | `/admin/login` | 前端改为 `/admin/login` |
| **访客统计** | `/statistics` | 拆分为 `/visitor/stats` + `/visitor/total` | 前端需拆分调用 |
| **访客记录** | `/visitors` POST | `/visitor/pv` POST + `/visitor/heartbeat` POST | 前端改为对应端点 |

### 数据结构映射

**后端 `Project` → 前端 `ProjectCard`：**
- `summary` → `description`
- `coverUrl` → `cover`
- `techStack`（JSON string）→ `techStack`（string[]，需 `JSON.parse`）
- `highlights`（JSON string）→ 新增字段，项目亮点数组
- `demoUrl` → `iframeDemo`
- 后端无 `tags`、`featured` 字段 → 可从前端移除或从 `category` 映射

**后端 `Article` → 前端 `ArticleCard`：**
- `tags`（JSON string）→ `tags`（string[]，需 `JSON.parse`）
- `viewCount` → 已有，可显示
- 后端无 `readTime` → 前端可计算或后端补充

---

## 六、代码风格与命名规范

### 6.1 文件命名

| 文件类型 | 命名规范 | 示例 |
|----------|----------|------|
| Vue 组件 | 大驼峰（PascalCase） | `AppHeader.vue`、`ArticleCard.vue` |
| TypeScript 组合式函数 | 小驼峰（camelCase） | `useApi.ts` |
| TypeScript 工具模块 | 小驼峰（camelCase） | `useApi.ts` |
| SCSS 样式文件 | 小驼峰（camelCase） | `variables.scss` |
| Nuxt 页面文件 | 框架约定 | `index.vue`、`[slug].vue` |

> **禁止**使用下划线命名或纯小写无分隔。组件文件必须为大驼峰。

### 6.2 注释规范

**所有注释必须使用中文**，方便团队协作与后期维护。

例外：第三方 API 文档引用、开源协议声明、标准算法名称可保留英文。

### 6.3 前端样式管理规范

前端样式采用 **"公共样式 + 组件级样式"** 双层架构。

**`styles/` 目录结构：**
```
styles/
├── _variables.scss        # 设计 Token
├── _lightTheme.scss       # 亮色模式
├── _darkTheme.scss        # 暗色模式
├── _mixins.scss           # SCSS 混合宏
├── _animations.scss       # 全局动画
├── _globals.scss          # 全局基础样式
├── _utilities.scss        # 工具类
├── _fonts.scss            # 字体定义
├── tailwind.css           # Tailwind 指令
└── main.scss              # 入口文件
```

**规范原则：**
1. **禁止硬编码色值**：必须使用 CSS 变量。
2. **禁止组件内重复定义通用动画**：统一使用 `_animations.scss`。
3. **组件级样式仅写布局与特殊覆盖**：颜色、字体、阴影用全局 Token。
4. **光影效果统一由 `_mixins.scss` 提供**。

### 6.4 光影设计语言（Light & Shadow）

**设计原则：**
- **光为引**：柔和光晕引导视觉焦点
- **影为界**：细腻阴影划分层级
- **玻璃为媒**：磨砂玻璃通透感
- **微动为灵**：细微光影流动反馈

**核心 glow Token：**
```scss
:root {
  --glow-primary: 0 0 20px rgba(59, 130, 246, 0.15), 0 0 40px rgba(59, 130, 246, 0.08);
  --glow-primary-strong: 0 0 30px rgba(59, 130, 246, 0.25), 0 0 60px rgba(59, 130, 246, 0.12);
  --gradient-hero: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 50%, #f1f5f9 100%);
  --gradient-card-shine: linear-gradient(135deg, rgba(255,255,255,0.6) 0%, rgba(255,255,255,0.1) 50%, rgba(255,255,255,0.3) 100%);
}
```

**性能约束**：同时生效的 `box-shadow` / `filter: drop-shadow()` 元素不得超过 10 个。

---

## 七、数据库

已创建的 5 张表（详见 `backend/src/main/resources/db/init.sql`）：

| 表名 | 说明 | 初始数据 |
|------|------|---------|
| `project` | 项目 | 9 条 |
| `tech_stack` | 技术栈 | 14 条 |
| `article` | 文章 | 3 条 |
| `visitor_daily` | 访客统计（按日） | 7 条 |
| `admin` | 管理员 | 1 条（admin / admin123） |

---

## 八、安全考量

1. **认证**：Spring Security + JWT（JJWT 0.12.5），管理员接口需 Bearer Token。
2. **密码存储**：BCrypt 哈希（管理员默认密码 admin123）。
3. **CORS**：后端 `WebConfig.java` 已配置跨域。
4. **SQL 注入**：MyBatis Plus，无字符串拼接 SQL。
5. **XSS**：Vue 模板自动转义。
6. **iframe 沙箱**：`sandbox="allow-scripts allow-same-origin"`。
7. **敏感文件**：`.env`、`.nuxt/`、`node_modules/`、`target/` 已加入 `.gitignore`。

---

## 九、剩余工作与 V2 规划

### 9.1 前后端对接（高优先级）

- [ ] 修改 `useApi.ts` 解包 `Result<T>` 响应
- [ ] 项目详情路由从 `[id]` 改为 `[slug]`
- [ ] 访客统计接入 SSE 实时在线人数
- [ ] 登录接口改为 `/admin/login`
- [ ] 技术栈页接入 `/api/v1/tech-stacks`
- [ ] 关于页技术栈从硬编码改为 API 获取

### 9.2 前端功能增强

- [ ] 技术栈页 `/tech-stack`（ECharts 雷达图，文档有规划）
- [ ] 成长时间线 `/timeline`（文档有规划）
- [ ] 打字机动画（Hero 区域）
- [ ] 数字滚动动画（统计数据）
- [ ] 文章按月归档展示
- [ ] Markdown 代码高亮优化
- [ ] 图片懒加载与占位符
- [ ] 项目 Demo 构建产物放入 `public/demos/`

### 9.3 后端功能增强

- [ ] 文章 `readTime` 字段或计算逻辑
- [ ] 评论系统（可选 V2）
- [ ] RSS 生成接口
- [ ] 更完善的单元测试覆盖

### 9.4 部署运维

- [ ] Nginx 配置文件
- [ ] Docker Compose 生产部署
- [ ] CI/CD 流水线
- [ ] Lighthouse 性能测试与优化

---

## 十、向 AI 代理的特别提醒

1. **修改前先确认文件存在**：前后端代码已大量存在，不要重复创建。
2. **以中文编写所有注释**：硬性规范。
3. **不要硬编码颜色/间距/阴影**：使用 CSS 变量。
4. **样式分层**：全局 Token 放 `styles/`，组件只写布局。
5. **前后端对接时注意 `Result<T>` 包装**：API 响应需从 `response.data` 提取实际数据。
6. **后端使用 slug 而非 id 查询**：项目详情和文章详情均使用 slug 作为路径参数。
7. **性能红线**：不得在同一视口中制造超过 10 个同时生效的 `box-shadow` 元素。
