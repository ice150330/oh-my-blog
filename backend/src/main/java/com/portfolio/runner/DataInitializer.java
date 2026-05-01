package com.portfolio.runner;

import com.portfolio.entity.Admin;
import com.portfolio.entity.Article;
import com.portfolio.entity.Project;
import com.portfolio.entity.TechStack;
import com.portfolio.mapper.AdminMapper;
import com.portfolio.mapper.ArticleMapper;
import com.portfolio.mapper.ProjectMapper;
import com.portfolio.mapper.TechStackMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 数据初始化器（仅 dev 环境）
 */
@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminMapper adminMapper;
    private final ProjectMapper projectMapper;
    private final TechStackMapper techStackMapper;
    private final ArticleMapper articleMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initAdmin();
        initTechStacks();
        initProjects();
        initArticles();
        log.info("开发环境初始数据写入完成");
    }

    private void initAdmin() {
        if (adminMapper.selectCount(null) > 0) {
            log.info("管理员数据已存在，跳过初始化");
            return;
        }
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setNickname("站长");
        adminMapper.insert(admin);
        log.info("管理员初始化完成: admin/admin123");
    }

    private void initTechStacks() {
        if (techStackMapper.selectCount(null) > 0) {
            log.info("技术栈数据已存在，跳过初始化");
            return;
        }
        insertTechStack("Java", "java", "language", 90, "Java 17，Spring Boot 3.2 生态系统", 1);
        insertTechStack("Python", "python", "language", 85, "数据处理与分析，Pandas / NumPy / Scikit-learn", 2);
        insertTechStack("SQL", "sql", "language", 88, "MySQL 8.0，复杂查询与性能优化", 3);
        insertTechStack("TypeScript", "typescript", "language", 80, "Nuxt 3 + Vue 3 全栈开发", 4);
        insertTechStack("Spring Boot", "springboot", "framework", 90, "Spring Boot 3.2，企业级后端开发", 5);
        insertTechStack("MyBatis Plus", "mybatis", "framework", 85, "ORM 框架，代码生成与分页", 6);
        insertTechStack("Vue 3", "vuejs", "framework", 82, "Composition API + TypeScript", 7);
        insertTechStack("Nuxt 3", "nuxtjs", "framework", 78, "SSG/SSR 混合渲染框架", 8);
        insertTechStack("Redis", "redis", "database", 80, "缓存、分布式锁、SSE 会话管理", 9);
        insertTechStack("MySQL", "mysql", "database", 88, "关系型数据库设计与优化", 10);
        insertTechStack("Docker", "docker", "tool", 75, "容器化部署，Docker Compose 编排", 11);
        insertTechStack("Git", "git", "tool", 85, "版本控制，GitHub Actions CI/CD", 12);
        insertTechStack("Linux", "linux", "tool", 78, "Ubuntu 22.04 服务器运维", 13);
        insertTechStack("ECharts", "echarts", "tool", 82, "数据可视化图表库", 14);
        log.info("技术栈初始化完成: {} 条", 14);
    }

    private void initProjects() {
        if (projectMapper.selectCount(null) > 0) {
            log.info("项目数据已存在，跳过初始化");
            return;
        }
        insertProject("data-pipeline-platform", "数据管道平台",
                "基于 Apache Airflow + Spark 构建的离线和实时数据处理平台，日均处理数据量 500GB+，支持 30+ 数据源接入和自定义数据清洗规则。",
                "data-engineering",
                "[\"Airflow\", \"Spark\", \"Kafka\", \"Python\", \"Docker\"]",
                "[\"日处理 500GB+ 数据\", \"支持 30+ 数据源\", \"可视化 DAG 编排\", \"自动重试与告警\"]",
                "https://demo.example.com/data-pipeline", "https://github.com/portfolio/data-pipeline", 1);

        insertProject("user-portrait-system", "用户画像系统",
                "基于 Flink + ClickHouse 的实时用户画像系统，构建 200+ 标签体系，实现毫秒级查询响应，支撑精准营销与推荐场景。",
                "data-engineering",
                "[\"Flink\", \"ClickHouse\", \"Redis\", \"Java\", \"Kafka\"]",
                "[\"200+ 标签体系\", \"毫秒级查询\", \"实时更新延迟 < 1s\", \"OLAP 多维分析\"]",
                "https://demo.example.com/user-portrait", "https://github.com/portfolio/user-portrait", 2);

        insertProject("sales-bi-dashboard", "销售 BI 看板",
                "基于 ECharts + Vue 3 构建的交互式商业智能看板，涵盖 GMV、转化率、用户留存等 50+ 指标，支持拖拽式自定义布局。",
                "data-visualization",
                "[\"Vue 3\", \"ECharts\", \"TypeScript\", \"Nuxt 3\", \"WebSocket\"]",
                "[\"50+ 业务指标\", \"拖拽自定义布局\", \"实时数据刷新\", \"PC/移动双端适配\"]",
                "https://demo.example.com/sales-bi", "https://github.com/portfolio/sales-bi", 3);

        insertProject("real-time-log-analyzer", "实时日志分析平台",
                "基于 ELK Stack + Kafka 构建的日志采集分析平台，支持 10 万条/秒日志写入，提供全文检索与异常自动检测。",
                "data-engineering",
                "[\"Elasticsearch\", \"Logstash\", \"Kibana\", \"Kafka\", \"Filebeat\"]",
                "[\"10万条/秒写入\", \"全文检索 < 100ms\", \"异常自动检测\", \"监控告警集成\"]",
                null, "https://github.com/portfolio/log-analyzer", 4);

        insertProject("churn-prediction-model", "客户流失预测模型",
                "基于 XGBoost + SHAP 的客户流失预测模型，AUC 0.92，提供可解释的特征重要性分析，辅助客户挽留策略制定。",
                "machine-learning",
                "[\"Python\", \"XGBoost\", \"SHAP\", \"Scikit-learn\", \"MLflow\"]",
                "[\"AUC 0.92\", \"SHAP 可解释性\", \"MLflow 模型管理\", \"月度自动重训练\"]",
                "https://demo.example.com/churn-prediction", "https://github.com/portfolio/churn-prediction", 5);

        insertProject("etl-automation-framework", "ETL 自动化框架",
                "基于 Python 开发的 ETL 自动化框架，支持 YAML 配置驱动、增量同步、数据质量校验，降低数据接入开发成本 60%。",
                "data-engineering",
                "[\"Python\", \"Pandas\", \"Apache Arrow\", \"Docker\", \"MySQL\"]",
                "[\"YAML 配置驱动\", \"增量同步\", \"数据质量校验\", \"开发效率提升 60%\"]",
                null, "https://github.com/portfolio/etl-framework", 6);

        insertProject("ad-recommendation-engine", "广告推荐引擎",
                "基于协同过滤 + DeepFM 的混合推荐引擎，CTR 提升 35%，支持 A/B 实验框架与实时效果监控。",
                "machine-learning",
                "[\"Python\", \"TensorFlow\", \"Redis\", \"Flask\", \"Docker\"]",
                "[\"CTR 提升 35%\", \"混合推荐策略\", \"A/B 实验框架\", \"在线推理 < 50ms\"]",
                null, "https://github.com/portfolio/ad-recommendation", 7);

        insertProject("data-governance-portal", "数据治理门户",
                "基于 Spring Boot + Vue 3 的数据资产管理与数据血缘可视化平台，覆盖元数据管理、数据质量监控、数据生命周期管理。",
                "data-analysis",
                "[\"Spring Boot\", \"Vue 3\", \"Neo4j\", \"MyBatis Plus\", \"ECharts\"]",
                "[\"数据血缘可视化\", \"元数据管理\", \"质量评分体系\", \"RBAC 权限控制\"]",
                "https://demo.example.com/data-governance", "https://github.com/portfolio/data-governance", 8);

        insertProject("smart-city-traffic", "智慧城市交通分析",
                "基于轨迹数据挖掘的城市交通流量分析与拥堵预测系统，覆盖 5000+ 路口，预测准确率 85%，支持城市交通信号优化决策。",
                "data-analysis",
                "[\"Hadoop\", \"Spark MLlib\", \"GeoPandas\", \"Folium\", \"Airflow\"]",
                "[\"覆盖 5000+ 路口\", \"拥堵预测准确率 85%\", \"时空轨迹分析\", \"交互式地图可视化\"]",
                null, "https://github.com/portfolio/smart-city-traffic", 9);

        log.info("项目初始化完成: {} 个", 9);
    }

    private void initArticles() {
        if (articleMapper.selectCount(null) > 0) {
            log.info("文章数据已存在，跳过初始化");
            return;
        }

        insertArticle("spring-boot-3-migration-guide", "Spring Boot 3.x 迁移实战指南",
                "从 Spring Boot 2.x 迁移到 3.x 的完整指南，包括 Jakarta EE、GraalVM 原生镜像、Observability 等关键变化。",
                "# Spring Boot 3.x 迁移实战指南\n\n## 背景\n\nSpring Boot 3.0 带来了许多重大变化，本文记录了实际项目迁移过程中遇到的问题与解决方案。\n\n## 关键变化\n\n1. **Java 17 基线**\n2. **Jakarta EE 迁移**\n3. **GraalVM 原生镜像**\n4. **Observability 改进**",
                "[\"Spring Boot\", \"Java\", \"后端开发\"]");

        insertArticle("vue3-composition-api-patterns", "Vue 3 Composition API 实用模式",
                "深入探讨 Vue 3 Composition API 的实战模式，包括状态管理、副作用处理、可组合函数设计。",
                "# Vue 3 Composition API 实用模式\n\n## 核心模式\n\n### 状态管理模式\n### 数据请求模式\n### 副作用处理",
                "[\"Vue 3\", \"TypeScript\", \"前端开发\"]");

        insertArticle("building-data-pipeline-from-scratch", "从零搭建企业级数据管道",
                "记录从零搭建企业级数据管道的完整历程：技术选型、架构设计、性能优化与运维实践。",
                "# 从零搭建企业级数据管道\n\n## 技术选型\n\n## 架构设计\n\n## 性能优化",
                "[\"数据工程\", \"架构设计\", \"Python\"]");

        log.info("文章初始化完成: {} 篇", 3);
    }

    // ========== Helper methods ==========

    private void insertTechStack(String name, String icon, String category, int proficiency, String description, int sortOrder) {
        TechStack tech = new TechStack();
        tech.setName(name);
        tech.setIcon(icon);
        tech.setCategory(category);
        tech.setProficiency(proficiency);
        tech.setDescription(description);
        tech.setSortOrder(sortOrder);
        techStackMapper.insert(tech);
    }

    private void insertProject(String slug, String title, String summary, String category,
                                String techStack, String highlights, String demoUrl, String repoUrl, int sortOrder) {
        Project project = new Project();
        project.setSlug(slug);
        project.setTitle(title);
        project.setSummary(summary);
        project.setCategory(category);
        project.setTechStack(techStack);
        project.setHighlights(highlights);
        project.setDemoUrl(demoUrl);
        project.setRepoUrl(repoUrl);
        project.setSortOrder(sortOrder);
        project.setStatus(1);
        projectMapper.insert(project);
    }

    private void insertArticle(String slug, String title, String summary, String content, String tags) {
        Article article = new Article();
        article.setSlug(slug);
        article.setTitle(title);
        article.setSummary(summary);
        article.setContent(content);
        article.setTags(tags);
        article.setStatus(1);
        article.setViewCount(0L);
        articleMapper.insert(article);
    }
}
