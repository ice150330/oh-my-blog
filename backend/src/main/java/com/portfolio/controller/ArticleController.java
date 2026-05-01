package com.portfolio.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.portfolio.dto.ArticleQuery;
import com.portfolio.entity.Article;
import com.portfolio.service.ArticleService;
import com.portfolio.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 文章控制器
 */
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    // ==================== 公开接口 ====================

    /**
     * 分页查询已发布文章
     */
    @GetMapping("/api/v1/articles")
    public Result<Page<Article>> list(ArticleQuery query) {
        return Result.success(articleService.listPublished(query));
    }

    /**
     * 根据 slug 查询文章详情
     */
    @GetMapping("/api/v1/articles/{slug}")
    public Result<Article> detail(@PathVariable String slug) {
        Article article = articleService.getBySlug(slug);
        // 阅读量 +1
        articleService.incrementViewCount(article.getId());
        return Result.success(article);
    }

    /**
     * 获取标签云
     */
    @GetMapping("/api/v1/articles/tags")
    public Result<List<String>> tags() {
        return Result.success(articleService.listTags());
    }

    /**
     * 按月归档
     */
    @GetMapping("/api/v1/articles/archives")
    public Result<List<Map<String, Object>>> archives() {
        return Result.success(articleService.listMonthlyArchive());
    }

    // ==================== 管理接口 ====================

    /**
     * 管理端列表（含草稿）
     */
    @GetMapping("/api/v1/admin/articles")
    public Result<Page<Article>> adminList(ArticleQuery query) {
        return Result.success(articleService.adminList(query));
    }

    /**
     * 管理端详情
     */
    @GetMapping("/api/v1/admin/articles/{id}")
    public Result<Article> adminDetail(@PathVariable Long id) {
        return Result.success(articleService.getById(id));
    }

    /**
     * 创建文章
     */
    @PostMapping("/api/v1/admin/articles")
    public Result<Article> create(@RequestBody Article article) {
        return Result.success(articleService.create(article));
    }

    /**
     * 更新文章
     */
    @PutMapping("/api/v1/admin/articles/{id}")
    public Result<Article> update(@PathVariable Long id, @RequestBody Article article) {
        article.setId(id);
        return Result.success(articleService.update(article));
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/api/v1/admin/articles/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return Result.success();
    }
}
