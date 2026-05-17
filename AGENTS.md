# oh-my-blog —— AI 编码代理项目指南

> 本文件面向 AI 编码代理。记录项目当前状态、技术栈、代码规范与注意事项。
> 所有信息基于实际代码与配置文件，请勿假设未经验证的内容。

---

## 一、项目概述

`oh-my-blog` 是一个个人作品集与博客站点（Portfolio Blog），用于展示站长的全栈技术能力、项目经历与专业文章。

**当前状态（V1.0 基础版本）**
- `frontend/`：Nuxt 3 前端，光影设计系统，全部页面路由，已通过 `useApi.ts` 完成与后端 API 的对接
- `backend/`：Spring Boot 3.2 后端，JWT 认证，SSE 实时在线人数，MyBatis Plus ORM，REST API 已就绪
- `docs/plan/`：6 份中文规划文档，涵盖完整路线图
- `docker-compose.yml`：开发环境编排（MySQL + Redis + 后端应用）

**关键事实**
- 前后端 API 对接已在 `frontend/composables/useApi.ts` 中完成，自动解包 `Result<T>`、处理字段映射、解析 JSON 字符串数组
- 博客文章与项目数据均来自后端 API，前端源码中**不存在** `content/` 目录
- 项目详情页路由已是 `[slug].vue`，文章详情页路由也是 `[slug].vue`

---

## 二、技术栈

### 前端

| 技术 | 版本 | 说明 |
|------|------|------|
| Nuxt | ^3.11.2 | Vue 生态，SSR + SSG 混合，关键路由预渲染 |
| Vue | ^3.4.21 | 组合式 API |
| TypeScript | ^5.4.5 | 全项目类型安全，严格模式（`strict: true`） |
| Tailwind CSS | ^3.4（模块） | 原子化样式，暗色模式通过 `.dark` 类切换 |
| SCSS / Sass | ^1.77.0 | 全局样式层（7 文件架构 + fonts + tailwind.css） |
| Pinia | ^2.1.7 | 状态管理，仅主题状态，持久化到 `localStorage` |
| @nuxt/content | ^2.12.1 | 模块已安装，但**未使用本地 Markdown 内容源** |
| @pinia/nuxt | ^0.5.1 | Pinia Nuxt 模块 |
| @nuxtjs/tailwindcss | ^6.12.0 | Tailwind CSS Nuxt 模块 |
| Element Plus | ^2.13.7 | UI 组件库，客户端插件，主要用于 Demo 页面 |
| @element-plus/icons-vue | ^2.3.2 | Element Plus 图标 |
| ECharts | ^6.0.0 | 图表库 |
| vue-echarts | ^8.0.1 | ECharts Vue 封装 |
| pnpm | 9.0.0 | 包管理器（Volta 锁定） |
| Node | 18.20.0 | 运行时（Volta 锁定） |

### 后端

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 运行时基线 |
| Spring Boot | 3.2.5 | 主框架 |
| Spring Security | 6.2.x | JWT 认证 |
| MyBatis Plus | 3.5.5 | ORM + 分页，`mybatis-plus-spring-boot3-starter` |
| JJWT | 0.12.5 | `jjwt-api` + `jjwt-impl` + `jjwt-jackson` |
| MySQL Connector/J | runtime | MySQL 8.0 驱动 |
| SpringDoc OpenAPI | 2.3.0 | Swagger UI：`/swagger-ui.html` |
| Lombok | — | `optional` |
| Maven | 3.9+ | 构建工具 |

### 中间件与基础设施

| 技术 | 版本/说明 | 用途 |
|------|-----------|------|
| MySQL | 8.0 | 主数据库 |
| Redis | 7-alpine（Docker）/ Memurai（Windows 本地） | 访客会话、在线人数统计 |
| Docker / Docker Compose | 开发环境已配置 | 本地编排 |
| Nginx | 规划中 | 反向代理 + 静态文件（生产环境），**源码中尚未创建 `nginx/` 目录** |

---

## 三、项目目录结构

```
oh-my-blog/
├── frontend/                    # Nuxt 3 前端
│   ├── app.vue                  # 应用根组件（NuxtLayout + NuxtPage）
│   ├── nuxt.config.ts           # Nuxt 配置（ssr: true, prerender 关键路由）
│   ├── package.json             # 依赖与脚本
│   ├── tsconfig.json            # TypeScript 配置
│   ├── pages/                   # 文件系统路由
│   │   ├── index.vue            # 首页（Hero + 统计 + 最新文章/项目）
│   │   ├── about.vue            # 关于页（个人简介 + 技术栈 + 时间线）
│   │   ├── contact.vue          # 联系页（联系信息 + 表单）
│   │   ├── blog/
│   │   │   ├── index.vue        # 博客列表（标签筛选）
│   │   │   └── [slug].vue       # 博客详情（简易 Markdown 渲染）
│   │   └── projects/
│   │       ├── index.vue        # 项目列表
│   │       └── [slug].vue       # 项目详情（iframe Demo）
│   ├── layouts/
│   │   ├── default.vue          # 默认布局（玻璃拟态背景 + 访客统计）
│   │   └── demo.vue             # Demo 全屏布局（隐藏博客导航）
│   ├── components/
│   │   ├── layout/
│   │   │   ├── AppHeader.vue    # 顶部导航（固定 + 玻璃拟态 + 主题切换）
│   │   │   └── AppFooter.vue    # 页脚
│   │   └── ui/
│   │       ├── ArticleCard.vue  # 文章卡片
│   │       ├── ProjectCard.vue  # 项目卡片
│   │       └── IconSprite.vue   # SVG Sprite 图标组件
│   ├── composables/
│   │   ├── useApi.ts            # API 客户端封装（已对接后端，自动解包 Result<T>）
│   │   └── demos/               # Demo 页面静态数据
│   ├── stores/
│   │   └── theme.ts             # Pinia 主题状态（light/dark/system，持久化）
│   ├── types/
│   │   └── index.ts             # TypeScript 类型定义（Article, Project, Statistics 等）
│   ├── styles/                  # 光影设计系统
│   │   ├── main.scss            # 入口文件（按依赖顺序 @use 各模块）
│   │   ├── tailwind.css         # Tailwind 三指令
│   │   ├── _variables.scss      # 基础设计 Token（间距、圆角、过渡、字体大小）
│   │   ├── _lightTheme.scss     # 亮色主题 "日光玻璃"
│   │   ├── _darkTheme.scss      # 暗色主题 "暗夜霓虹"
│   │   ├── _mixins.scss         # SCSS 混合宏（glass-base, card-glass, btn-glow 等）
│   │   ├── _animations.scss     # 关键帧动画 + 工具类 + 交错延迟 + prefers-reduced-motion
│   │   ├── _globals.scss        # CSS Reset + 全局基础样式 + 页面过渡
│   │   ├── _utilities.scss      # 工具类（.card-glass, .btn-glow, .text-gradient 等）
│   │   ├── _fonts.scss          # 6 款 @font-face 定义 + CSS 变量字体栈
│   │   └── demos/               # Demo 页面专用样式
│   ├── plugins/
│   │   └── element-plus.client.ts  # Element Plus 客户端插件
│   ├── public/                  # 静态资源（不经过构建处理）
│   │   ├── font/                # 6 款 woff2 字体
│   │   ├── icon/                # 42 个 SVG 图标 + sprite.svg
│   │   └── demos/               # 4 个项目 Demo 构建产物（独立静态页面）
│   └── material/                # 原始设计资产备份（字体、图标源文件）
│
├── backend/                     # Spring Boot 后端
│   ├── pom.xml                  # Maven 构建配置
│   ├── Dockerfile               # 生产环境容器镜像（eclipse-temurin:17-jre-alpine）
│   └── src/
│       ├── main/java/com/portfolio/
│       │   ├── PortfolioApplication.java
│       │   ├── config/          # Security, MyBatisPlus, Redis, SSE, Web/CORS
│       │   ├── controller/      # 8 个 REST Controller
│       │   ├── service/         # 业务接口 + impl/
│       │   ├── mapper/          # MyBatis Plus Mapper 接口
│       │   ├── entity/          # 5 个实体类（Article, Project, TechStack, VisitorDaily, Admin）
│       │   ├── dto/             # 请求参数 DTO（ArticleQuery, ProjectQuery, LoginRequest）
│       │   ├── vo/
│       │   │   └── Result.java  # 统一响应包装体 {code, message, data, timestamp}
│       │   ├── security/        # JwtTokenProvider + JwtAuthenticationFilter
│       │   ├── common/          # 全局异常处理 + 错误码 + ResourceNotFoundException
│       │   └── runner/
│       │       └── DataInitializer.java  # dev 环境自动初始化测试数据
│       ├── main/resources/
│       │   ├── application.yml      # 主配置（Jackson/上传/MyBatis Plus/JWT）
│       │   ├── application-dev.yml  # 开发环境（本地 MySQL 3306 / Redis 6379）
│       │   ├── application-prod.yml # 生产环境（环境变量 + HikariCP）
│       │   ├── mapper/              # MyBatis XML（ArticleMapper.xml, VisitorDailyMapper.xml）
│       │   └── db/init.sql          # 建表 + 初始数据（5 张表）
│       └── test/java/com/portfolio/controller/
│           ├── HealthControllerTest.java
│           └── ProjectControllerTest.java
│
├── docs/
│   └── plan/                    # 6 份阶段规划文档
│       ├── 01-项目基础与技术架构.md
│       ├── 02-设计系统与前端框架.md
│       ├── 03-核心页面开发.md
│       ├── 04-项目展示与内容系统.md
│       ├── 05-后端服务与数据层.md
│       └── 06-部署运维与上线.md
│
├── docker-compose.yml           # 开发环境 Docker Compose（端口：app 8080, MySQL 3307, Redis 6380）
├── AGENTS.md                    # 本文件
├── README.md                    # 项目说明
├── LICENSE                      # MIT
└── .gitignore
```

> **注意**：源码中不存在 `frontend/content/`、`nginx/`、`.github/workflows/` 目录。

---

## 四、构建与运行命令

### 环境准备（Windows 本地开发）

```powershell
# 1. 确保 MySQL 8.0 运行（端口 3306，账号 root，密码 123456）
# 2. 确保 Memurai（Windows 版 Redis）运行（端口 6379，无密码）
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

- dev 环境下，`DataInitializer`（`@Profile("dev")`）会自动写入测试数据，无需每次手动执行 SQL。
- 生产环境通过环境变量注入数据库和 Redis 连接信息，见 `application-prod.yml`。

### 前端

```powershell
cd frontend
pnpm install
pnpm run dev
# 开发服务器：http://localhost:3000
```

### Docker Compose（开发环境）

```powershell
docker-compose up -d
```

| 服务 | 容器内端口 | 宿主机映射端口 | 说明 |
|------|-----------|---------------|------|
| app（Spring Boot） | 8080 | 8080 | 后端 API |
| mysql（MySQL 8.0） | 3306 | **3307** | 注意：不是 3306 |
| redis（Redis 7-alpine） | 6379 | **6380** | 注意：不是 6379 |

MySQL 默认密码：`root123`（可通过 `MYSQL_PASSWORD` 环境变量覆盖）。

---

## 五、API 架构

### 5.1 统一响应格式

所有后端接口返回 `Result<T>`：

```json
{
  "code": 200,
  "message": "success",
  "data": { ... },
  "timestamp": 1714500000000
}
```

前端 `useApi.ts` 已封装 `request<T>()` 函数，自动从 `response.data` 提取实际数据，并在 `code !== 200` 时抛出异常。

### 5.2 后端 API 端点

| 端点 | 方法 | 功能 | 权限 |
|------|------|------|------|
| `/api/v1/health` | GET | 健康检查 | 公开 |
| `/api/v1/articles` | GET | 文章列表分页（`?tag=`） | 公开 |
| `/api/v1/articles/{slug}` | GET | 文章详情（slug），自动 +1 阅读量 | 公开 |
| `/api/v1/articles/tags` | GET | 标签云 | 公开 |
| `/api/v1/articles/archives` | GET | 按月归档 | 公开 |
| `/api/v1/admin/articles` | GET/POST | 管理端文章列表 / 创建 | 需 JWT |
| `/api/v1/admin/articles/{id}` | GET/PUT/DELETE | 管理端文章详情 / 更新 / 删除（逻辑删除） | 需 JWT |
| `/api/v1/projects` | GET | 项目列表分页（`?category=`） | 公开 |
| `/api/v1/projects/{slug}` | GET | 项目详情（slug） | 公开 |
| `/api/v1/projects/categories` | GET | 分类列表 | 公开 |
| `/api/v1/admin/projects` | GET/POST | 管理端项目列表 / 创建 | 需 JWT |
| `/api/v1/admin/projects/{id}` | GET/PUT/DELETE | 管理端项目详情 / 更新 / 删除 | 需 JWT |
| `/api/v1/techstack` | GET | 技术栈按分类分组 | 公开 |
| `/api/v1/techstack/radar` | GET | 雷达图数据 | 公开 |
| `/api/v1/techstack/list` | GET | 技术栈列表（不分组） | 公开 |
| `/api/v1/visitor/sse` | GET | SSE 实时在线人数推送 | 公开 |
| `/api/v1/visitor/heartbeat` | POST | 访客心跳（Redis 30s TTL） | 公开 |
| `/api/v1/visitor/pv` | POST | 记录页面浏览量 | 公开 |
| `/api/v1/visitor/stats?days=7` | GET | 最近 N 天访问统计 | 公开 |
| `/api/v1/visitor/total` | GET | 总 PV/UV | 公开 |
| `/api/v1/admin/login` | POST | 管理员登录（JWT） | 公开 |
| `/api/v1/admin/upload` | POST | 文件上传（V2 桩实现） | 需 JWT |

> **注意**：技术栈端点是 `/api/v1/techstack`（无连字符），旧文档中的 `/api/v1/tech-stacks` 不正确。

### 5.3 前端 API 封装（`composables/useApi.ts`）

已导出 5 个 API 组合：

| 组合 | 功能 |
|------|------|
| `useArticleApi()` | `getList`, `getBySlug`, `getTags`, `getArchives` |
| `useProjectApi()` | `getList`, `getBySlug`, `getCategories` |
| `useVisitorApi()` | `subscribeSse`, `heartbeat`, `recordPv`, `getStats`, `getTotal` |
| `useAuthApi()` | `login`（`/admin/login`） |
| `useTechStackApi()` | `getGrouped`, `getRadar`, `getList` |

**自动处理的数据转换**：
- `tags`、`techStack`、`highlights` 字段从 JSON 字符串解析为 `string[]`
- `summary` → `description`、`coverUrl` → `cover`、`demoUrl` → `iframeDemo`
- `readTime` 前端估算：按每分钟 500 字计算

---

## 六、数据库

5 张表定义于 `backend/src/main/resources/db/init.sql`：

| 表名 | 说明 | 初始数据 | 关键字段 |
|------|------|---------|---------|
| `project` | 项目 | 9 条 | `slug` 唯一索引，`category` 索引，`tech_stack`/`highlights` JSON |
| `tech_stack` | 技术栈 | 14 条 | `category` 索引，`proficiency` 0-100 |
| `article` | 文章 | 3 条 | `slug` 唯一索引，`tags` JSON，`view_count` |
| `visitor_daily` | 访客日统计 | 7 条 | `date` 唯一索引，`pv`/`uv` |
| `admin` | 管理员 | 1 条 | `username` 唯一，`password` BCrypt 哈希 |

**默认管理员账号**：`admin` / `admin123`

**MyBatis Plus 全局配置**：
- `map-underscore-to-camel-case: true`
- 逻辑删除字段 `deleted`，删除值 `1`，未删除 `0`
- `created_at` / `updated_at` 自动填充

---

## 七、代码风格与命名规范

### 7.1 文件命名

| 文件类型 | 命名规范 | 示例 |
|----------|----------|------|
| Vue 组件 | 大驼峰（PascalCase） | `AppHeader.vue`、`ArticleCard.vue` |
| TypeScript 组合式函数 / 工具模块 | 小驼峰（camelCase） | `useApi.ts` |
| SCSS 样式文件 | 小驼峰（camelCase） | `_variables.scss` |
| Nuxt 页面文件 | 框架约定 | `index.vue`、`[slug].vue` |

> **禁止**使用下划线命名或纯小写无分隔。组件文件必须为大驼峰。

### 7.2 注释规范

**所有注释必须使用中文**，方便团队协作与后期维护。

例外：第三方 API 文档引用、开源协议声明、标准算法名称可保留英文。

### 7.3 前端样式管理规范

前端样式采用 **"公共样式 + 组件级样式"** 双层架构。

**`styles/` 目录：**
```
styles/
├── _variables.scss        # 基础设计 Token
├── _lightTheme.scss       # 亮色模式 "日光玻璃"
├── _darkTheme.scss        # 暗色模式 "暗夜霓虹"
├── _mixins.scss           # SCSS 混合宏
├── _animations.scss       # 全局动画 + prefers-reduced-motion
├── _globals.scss          # CSS Reset + 全局基础样式
├── _utilities.scss        # 工具类
├── _fonts.scss            # 字体定义
├── tailwind.css           # Tailwind 指令
└── main.scss              # 入口文件（按依赖顺序 @use）
```

**规范原则：**
1. **禁止硬编码色值**：必须使用 CSS 变量。
2. **禁止组件内重复定义通用动画**：统一使用 `_animations.scss`。
3. **组件级样式仅写布局与特殊覆盖**：颜色、字体、阴影用全局 Token。
4. **光影效果统一由 `_mixins.scss` 提供**。

### 7.4 光影设计语言（Light & Shadow）

**设计原则：**
- **光为引**：柔和光晕引导视觉焦点
- **影为界**：细腻阴影划分层级
- **玻璃为媒**：磨砂玻璃通透感
- **微动为灵**：细微光影流动反馈

**性能红线**：同一视口中同时生效的 `box-shadow` / `filter: drop-shadow()` 元素**不得超过 10 个**。

---

## 八、测试

### 前端

**当前无任何测试。**
- 未安装 Vitest、Jest、Cypress、Playwright 等测试框架
- `package.json` scripts 中无测试命令
- 项目中不存在 `.spec.ts` 或 `.test.ts` 文件

### 后端

仅 2 个 `@WebMvcTest` 单元测试：
- `HealthControllerTest.java`：测试健康检查返回 `code=200`
- `ProjectControllerTest.java`：测试项目列表分页、分类列表、404 场景

两者均 `@Import(SecurityConfig.class)` 并 `@MockBean` 注入 JWT 相关组件以绕过真实认证。

---

## 九、安全考量

1. **认证**：Spring Security + JWT（JJWT 0.12.5），管理员接口需 `Bearer <token>`。
2. **密码存储**：BCrypt 哈希（默认密码 `admin123`）。
3. **CORS**：后端 `WebConfig.java` 已配置跨域。
4. **SQL 注入**：MyBatis Plus，无字符串拼接 SQL。
5. **XSS**：Vue 模板自动转义。
6. **iframe 沙箱**：`sandbox="allow-scripts allow-same-origin"`。
7. **JWT Secret**：当前硬编码在 `application.yml` 中，生产环境应通过环境变量注入。
8. **敏感文件**：`.env`、`.nuxt/`、`node_modules/`、`target/` 已加入 `.gitignore`。

---

## 十、部署相关

### Dockerfile（`backend/Dockerfile`）

- 基础镜像：`eclipse-temurin:17-jre-alpine`
- 时区：`Asia/Shanghai`
- 非 root 用户运行（`appuser:appgroup`）
- 健康检查：每 30s 请求 `http://localhost:8080/api/v1/health`
- 启动命令：`java -jar -Dspring.profiles.active=prod app.jar`

### docker-compose.yml（项目根目录）

仅用于**开发/本地**环境：
- MySQL 映射到宿主机 **3307**，Redis 映射到 **6380**
- `init.sql` 通过 volume 挂载到 `/docker-entrypoint-initdb.d/`，容器首次启动时自动执行

### 尚未实现的部署组件

| 组件 | 状态 | 说明 |
|------|------|------|
| `nginx/` 目录 | ❌ 不存在 | 计划中，用于生产环境反向代理 + SSL |
| `.github/workflows/` | ❌ 不存在 | 计划中，包含前端 CI、后端 CI、Demo 构建 |
| 生产环境 Docker Compose | ❌ 不存在 | 计划中有独立编排方案（见 `docs/plan/06-部署运维与上线.md`） |

---

## 十一、向 AI 代理的特别提醒

1. **修改前先确认文件存在**：前后端代码已大量存在，不要重复创建。特别是 `content/`、`nginx/`、`.github/workflows/` 目前不存在。
2. **以中文编写所有注释**：硬性规范。
3. **不要硬编码颜色/间距/阴影**：使用 CSS 变量。
4. **样式分层**：全局 Token 放 `styles/`，组件只写布局。
5. **前后端对接已完成**：`useApi.ts` 已自动解包 `Result<T>`，不要重复造轮子。
6. **后端使用 slug 而非 id 查询**：公开接口 `/articles/{slug}`、`/projects/{slug}` 均使用 slug。
7. **技术栈端点无连字符**：正确路径是 `/api/v1/techstack`，不是 `/api/v1/tech-stacks`。
8. **性能红线**：不得在同一视口中制造超过 10 个同时生效的 `box-shadow` 元素。
9. **JSON 字段**：后端 `tech_stack`、`highlights`、`tags` 在实体中为 `String` 类型，前端 `useApi.ts` 已自动解析，后端 Service 层需自行处理 `JSON.stringify`。
10. **MySQL 特有语法**：`JSON_TABLE`、`ON DUPLICATE KEY UPDATE` 均为 MySQL 特有，数据库不可随意替换。
