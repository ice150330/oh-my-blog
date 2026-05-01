package com.portfolio.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.portfolio.dto.ArticleQuery;
import com.portfolio.entity.Article;

import java.util.List;
import java.util.Map;

/**
 * 文章服务接口
 */
public interface ArticleService {

    /**
     * 分页查询已发布文章（支持按标签筛选）
     */
    Page<Article> listPublished(ArticleQuery query);

    /**
     * 根据 slug 查询文章详情
     */
    Article getBySlug(String slug);

    /**
     * 获取标签云（所有不重复的标签）
     */
    List<String> listTags();

    /**
     * 按月归档
     * @return [{"month": "2024-01", "count": 5}, ...]
     */
    List<Map<String, Object>> listMonthlyArchive();

    /**
     * 增加阅读量
     */
    void incrementViewCount(Long id);

    // ========== 管理接口 ==========

    /**
     * 管理端分页查询（含草稿）
     */
    Page<Article> adminList(ArticleQuery query);

    /**
     * 根据 ID 查询
     */
    Article getById(Long id);

    /**
     * 创建文章
     */
    Article create(Article article);

    /**
     * 更新文章
     */
    Article update(Article article);

    /**
     * 删除文章（逻辑删除）
     */
    void delete(Long id);
}
