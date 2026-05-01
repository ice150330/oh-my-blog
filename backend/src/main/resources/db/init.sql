-- ====================================================
-- oh-my-blog 初始化数据库脚本
-- ====================================================

CREATE DATABASE IF NOT EXISTS oh_my_blog
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE oh_my_blog;

-- ====================================================
-- 1. 项目表
-- ====================================================
CREATE TABLE IF NOT EXISTS project (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    slug        VARCHAR(100)  NOT NULL UNIQUE COMMENT 'URL 标识符',
    title       VARCHAR(200)  NOT NULL COMMENT '项目标题',
    summary     VARCHAR(500)  NOT NULL COMMENT '项目简介',
    cover_url   VARCHAR(500)  DEFAULT NULL COMMENT '封面图片 URL',
    category    VARCHAR(50)   NOT NULL COMMENT '分类：data-engineering/data-analysis/data-visualization/machine-learning',
    tech_stack  JSON          DEFAULT NULL COMMENT '技术栈 JSON 数组',
    highlights  JSON          DEFAULT NULL COMMENT '项目亮点 JSON 数组',
    demo_url    VARCHAR(500)  DEFAULT NULL COMMENT 'Demo 地址',
    repo_url    VARCHAR(500)  DEFAULT NULL COMMENT '仓库地址',
    sort_order  INT           DEFAULT 0 COMMENT '排序序号',
    status      TINYINT       DEFAULT 1 COMMENT '状态：1=已发布 0=草稿',
    deleted     TINYINT       DEFAULT 0 COMMENT '逻辑删除：1=已删除 0=正常',
    created_at  DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_category (category),
    INDEX idx_slug (slug),
    INDEX idx_sort_order (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目';

-- ====================================================
-- 2. 技术栈表
-- ====================================================
CREATE TABLE IF NOT EXISTS tech_stack (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL COMMENT '技术名称',
    icon        VARCHAR(100)  DEFAULT NULL COMMENT '图标名称/URL',
    category    VARCHAR(50)   NOT NULL COMMENT '分类：language/framework/tool/database/cloud/other',
    proficiency INT           DEFAULT 0 COMMENT '熟练度 0-100',
    description VARCHAR(500)  DEFAULT NULL COMMENT '简要描述',
    sort_order  INT           DEFAULT 0 COMMENT '排序序号',
    created_at  DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_category (category),
    INDEX idx_sort_order (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='技术栈';

-- ====================================================
-- 3. 文章表
-- ====================================================
CREATE TABLE IF NOT EXISTS article (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    slug        VARCHAR(200)  NOT NULL UNIQUE COMMENT 'URL 标识符',
    title       VARCHAR(300)  NOT NULL COMMENT '文章标题',
    summary     VARCHAR(500)  NOT NULL COMMENT '文章摘要',
    content     MEDIUMTEXT    NOT NULL COMMENT 'Markdown 正文',
    cover_url   VARCHAR(500)  DEFAULT NULL COMMENT '封面图片 URL',
    tags        JSON          DEFAULT NULL COMMENT '标签 JSON 数组',
    status      TINYINT       DEFAULT 1 COMMENT '状态：1=已发布 0=草稿',
    view_count  BIGINT        DEFAULT 0 COMMENT '阅读量',
    deleted     TINYINT       DEFAULT 0 COMMENT '逻辑删除：1=已删除 0=正常',
    created_at  DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_slug (slug),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章';

-- ====================================================
-- 4. 访客统计表（按日聚合）
-- ====================================================
CREATE TABLE IF NOT EXISTS visitor_daily (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    date        DATE          NOT NULL UNIQUE COMMENT '统计日期',
    pv          BIGINT        DEFAULT 0 COMMENT '页面浏览量',
    uv          BIGINT        DEFAULT 0 COMMENT '独立访客数',
    created_at  DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE INDEX idx_date (date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='访客每日统计';

-- ====================================================
-- 5. 管理员表
-- ====================================================
CREATE TABLE IF NOT EXISTS admin (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)   NOT NULL UNIQUE COMMENT '用户名',
    password    VARCHAR(255)  NOT NULL COMMENT 'BCrypt 加密密码',
    nickname    VARCHAR(100)  DEFAULT NULL COMMENT '昵称',
    avatar_url  VARCHAR(500)  DEFAULT NULL COMMENT '头像 URL',
    created_at  DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员';

-- ====================================================
-- 初始数据
-- ====================================================

-- 管理员（密码: admin123 的 BCrypt 哈希）
INSERT INTO admin (username, password, nickname) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '站长');

-- 技术栈数据
INSERT INTO tech_stack (name, icon, category, proficiency, description, sort_order) VALUES
('Java', 'java', 'language', 90, 'Java 17，Spring Boot 3.2 生态系统', 1),
('Python', 'python', 'language', 85, '数据处理与分析，Pandas / NumPy / Scikit-learn', 2),
('SQL', 'sql', 'language', 88, 'MySQL 8.0，复杂查询与性能优化', 3),
('TypeScript', 'typescript', 'language', 80, 'Nuxt 3 + Vue 3 全栈开发', 4),
('Spring Boot', 'springboot', 'framework', 90, 'Spring Boot 3.2，企业级后端开发', 5),
('MyBatis Plus', 'mybatis', 'framework', 85, 'ORM 框架，代码生成与分页', 6),
('Vue 3', 'vuejs', 'framework', 82, 'Composition API + TypeScript', 7),
('Nuxt 3', 'nuxtjs', 'framework', 78, 'SSG/SSR 混合渲染框架', 8),
('Redis', 'redis', 'database', 80, '缓存、分布式锁、SSE 会话管理', 9),
('MySQL', 'mysql', 'database', 88, '关系型数据库设计与优化', 10),
('Docker', 'docker', 'tool', 75, '容器化部署，Docker Compose 编排', 11),
('Git', 'git', 'tool', 85, '版本控制，GitHub Actions CI/CD', 12),
('Linux', 'linux', 'tool', 78, 'Ubuntu 22.04 服务器运维', 13),
('ECharts', 'echarts', 'tool', 82, '数据可视化图表库', 14);

-- 项目数据
INSERT INTO project (slug, title, summary, category, tech_stack, highlights, demo_url, repo_url, sort_order) VALUES
('data-pipeline-platform', '数据管道平台',
 '基于 Apache Airflow + Spark 构建的离线和实时数据处理平台，日均处理数据量 500GB+，支持 30+ 数据源接入和自定义数据清洗规则。',
 'data-engineering',
 '["Airflow", "Spark", "Kafka", "Python", "Docker"]',
 '["日处理 500GB+ 数据", "支持 30+ 数据源", "可视化 DAG 编排", "自动重试与告警"]',
 'https://demo.example.com/data-pipeline', 'https://github.com/portfolio/data-pipeline', 1),

('user-portrait-system', '用户画像系统',
 '基于 Flink + ClickHouse 的实时用户画像系统，构建 200+ 标签体系，实现毫秒级查询响应，支撑精准营销与推荐场景。',
 'data-engineering',
 '["Flink", "ClickHouse", "Redis", "Java", "Kafka"]',
 '["200+ 标签体系", "毫秒级查询", "实时更新延迟 < 1s", "OLAP 多维分析"]',
 'https://demo.example.com/user-portrait', 'https://github.com/portfolio/user-portrait', 2),

('sales-bi-dashboard', '销售 BI 看板',
 '基于 ECharts + Vue 3 构建的交互式商业智能看板，涵盖 GMV、转化率、用户留存等 50+ 指标，支持拖拽式自定义布局。',
 'data-visualization',
 '["Vue 3", "ECharts", "TypeScript", "Nuxt 3", "WebSocket"]',
 '["50+ 业务指标", "拖拽自定义布局", "实时数据刷新", "PC/移动双端适配"]',
 'https://demo.example.com/sales-bi', 'https://github.com/portfolio/sales-bi', 3),

('real-time-log-analyzer', '实时日志分析平台',
 '基于 ELK Stack + Kafka 构建的日志采集分析平台，支持 10 万条/秒日志写入，提供全文检索与异常自动检测。',
 'data-engineering',
 '["Elasticsearch", "Logstash", "Kibana", "Kafka", "Filebeat"]',
 '["10万条/秒写入", "全文检索 < 100ms", "异常自动检测", "监控告警集成"]',
 NULL, 'https://github.com/portfolio/log-analyzer', 4),

('churn-prediction-model', '客户流失预测模型',
 '基于 XGBoost + SHAP 的客户流失预测模型，AUC 0.92，提供可解释的特征重要性分析，辅助客户挽留策略制定。',
 'machine-learning',
 '["Python", "XGBoost", "SHAP", "Scikit-learn", "MLflow"]',
 '["AUC 0.92", "SHAP 可解释性", "MLflow 模型管理", "月度自动重训练"]',
 'https://demo.example.com/churn-prediction', 'https://github.com/portfolio/churn-prediction', 5),

('etl-automation-framework', 'ETL 自动化框架',
 '基于 Python 开发的 ETL 自动化框架，支持 YAML 配置驱动、增量同步、数据质量校验，降低数据接入开发成本 60%。',
 'data-engineering',
 '["Python", "Pandas", "Apache Arrow", "Docker", "MySQL"]',
 '["YAML 配置驱动", "增量同步", "数据质量校验", "开发效率提升 60%"]',
 NULL, 'https://github.com/portfolio/etl-framework', 6),

('ad-recommendation-engine', '广告推荐引擎',
 '基于协同过滤 + DeepFM 的混合推荐引擎，CTR 提升 35%，支持 A/B 实验框架与实时效果监控。',
 'machine-learning',
 '["Python", "TensorFlow", "Redis", "Flask", "Docker"]',
 '["CTR 提升 35%", "混合推荐策略", "A/B 实验框架", "在线推理 < 50ms"]',
 NULL, 'https://github.com/portfolio/ad-recommendation', 7),

('data-governance-portal', '数据治理门户',
 '基于 Spring Boot + Vue 3 的数据资产管理与数据血缘可视化平台，覆盖元数据管理、数据质量监控、数据生命周期管理。',
 'data-analysis',
 '["Spring Boot", "Vue 3", "Neo4j", "MyBatis Plus", "ECharts"]',
 '["数据血缘可视化", "元数据管理", "质量评分体系", "RBAC 权限控制"]',
 'https://demo.example.com/data-governance', 'https://github.com/portfolio/data-governance', 8),

('smart-city-traffic', '智慧城市交通分析',
 '基于轨迹数据挖掘的城市交通流量分析与拥堵预测系统，覆盖 5000+ 路口，预测准确率 85%，支持城市交通信号优化决策。',
 'data-analysis',
 '["Hadoop", "Spark MLlib", "GeoPandas", "Folium", "Airflow"]',
 '["覆盖 5000+ 路口", "拥堵预测准确率 85%", "时空轨迹分析", "交互式地图可视化"]',
 NULL, 'https://github.com/portfolio/smart-city-traffic', 9);

-- 文章数据
INSERT INTO article (slug, title, summary, content, tags) VALUES
('spring-boot-3-migration-guide', 'Spring Boot 3.x 迁移实战指南',
 '从 Spring Boot 2.x 迁移到 3.x 的完整指南，包括 Jakarta EE、GraalVM 原生镜像、Observability 等关键变化。',
 '# Spring Boot 3.x 迁移实战指南\n\n## 背景\n\nSpring Boot 3.0 带来了许多重大变化，本文记录了实际项目迁移过程中遇到的问题与解决方案。\n\n## Jakarta EE 迁移\n\n所有 `javax.*` 包需要替换为 `jakarta.*` 包：\n\n```java\n// 旧（Spring Boot 2.x）\nimport javax.persistence.Entity;\nimport javax.annotation.PostConstruct;\n\n// 新（Spring Boot 3.x）\nimport jakarta.persistence.Entity;\nimport jakarta.annotation.PostConstruct;\n```\n\n## 关键变化\n\n1. **Java 17 基线**：必须使用 Java 17+\n2. **GraalVM 支持**：Spring Native 已合并到 Spring Boot 核心\n3. **Observability**：Actuator 新增 Micrometer Tracing\n4. **HTTP 客户端**：RestTemplate 逐步被 HTTP Interface 取代\n\n## 总结\n\n整体迁移过程相对顺利，建议尽早迁移到 Spring Boot 3.x 以获得更好的性能和安全性。',
 '["Spring Boot", "Java", "后端开发"]'),

('vue3-composition-api-patterns', 'Vue 3 Composition API 实用模式',
 '深入探讨 Vue 3 Composition API 的实战模式，包括状态管理、副作用处理、可组合函数设计。',
 '# Vue 3 Composition API 实用模式\n\n## 为什么选择 Composition API？\n\nComposition API 提供了更好的逻辑复用和类型推导能力。\n\n## 核心模式\n\n### 1. 状态管理模式\n\n```typescript\n// composables/useCounter.ts\nexport function useCounter(initial = 0) {\n  const count = ref(initial);\n  const increment = () => count.value++;\n  return { count, increment };\n}\n```\n\n### 2. 数据请求模式\n\n```typescript\nexport function useFetch<T>(url: string) {\n  const data = ref<T | null>(null);\n  const error = ref<Error | null>(null);\n  const loading = ref(false);\n  \n  const execute = async () => {\n    loading.value = true;\n    try {\n      data.value = await fetch(url).then(r => r.json());\n    } catch (e) {\n      error.value = e as Error;\n    } finally {\n      loading.value = false;\n    }\n  };\n  \n  return { data, error, loading, execute };\n}\n```\n\n## 总结\n\n合理使用 `composable` 可以大幅提升代码复用率。',
 '["Vue 3", "TypeScript", "前端开发"]'),

('building-data-pipeline-from-scratch', '从零搭建企业级数据管道',
 '记录从零搭建企业级数据管道的完整历程：技术选型、架构设计、性能优化与运维实践。',
 '# 从零搭建企业级数据管道\n\n## 技术选型\n\n| 组件 | 选择 | 备选 |\n|------|------|------|\n| 调度引擎 | Apache Airflow | DolphinScheduler |\n| 计算引擎 | Apache Spark | Flink |\n| 消息队列 | Apache Kafka | Pulsar |\n| 存储层 | HDFS + MinIO | S3 |\n\n## 架构设计\n\n### Lambda 架构\n\n批处理层（Batch Layer）+ 速度层（Speed Layer）+ 服务层（Serving Layer）。\n\n### 数据流转\n\n```\n数据源 → Kafka → Flink(实时) / Spark(离线) → HDFS → Hive → BI\n                    ↓\n                  Redis(热数据) → API\n```\n\n## 性能优化\n\n1. **分区策略**：按日期 + 业务维度分区\n2. **小文件合并**：定时合并小于 128MB 的文件\n3. **数据压缩**：Parquet + Snappy 压缩\n\n## 总结\n\n数据管道建设是一个持续迭代的过程，需要根据业务需求灵活调整。',
 '["数据工程", "架构设计", "Python"]');

-- 访客统计初始数据（最近 7 天）
INSERT INTO visitor_daily (date, pv, uv) VALUES
(CURDATE() - INTERVAL 6 DAY, 120, 45),
(CURDATE() - INTERVAL 5 DAY, 135, 52),
(CURDATE() - INTERVAL 4 DAY, 98, 38),
(CURDATE() - INTERVAL 3 DAY, 156, 61),
(CURDATE() - INTERVAL 2 DAY, 142, 55),
(CURDATE() - INTERVAL 1 DAY, 168, 63),
(CURDATE(), 89, 31);
