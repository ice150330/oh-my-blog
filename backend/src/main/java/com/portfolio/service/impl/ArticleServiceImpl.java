package com.portfolio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.portfolio.common.exception.ResourceNotFoundException;
import com.portfolio.dto.ArticleQuery;
import com.portfolio.entity.Article;
import com.portfolio.mapper.ArticleMapper;
import com.portfolio.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 文章服务实现
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleMapper articleMapper;

    @Override
    public Page<Article> listPublished(ArticleQuery query) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, 1)
               .eq(Article::getDeleted, 0);

        // 按标签筛选（使用 JSON_CONTAINS）
        if (StringUtils.hasText(query.getTag())) {
            wrapper.apply("JSON_CONTAINS(tags, {0})", "\"" + query.getTag() + "\"");
        }

        wrapper.orderByDesc(Article::getCreatedAt);

        return page(new Page<>(query.getPage(), query.getSize()), wrapper);
    }

    @Override
    public Article getBySlug(String slug) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getSlug, slug)
               .eq(Article::getStatus, 1)
               .eq(Article::getDeleted, 0);

        Article article = getOne(wrapper);
        if (article == null) {
            throw new ResourceNotFoundException("文章不存在: " + slug);
        }
        return article;
    }

    @Override
    public List<String> listTags() {
        return articleMapper.selectTags();
    }

    @Override
    public List<Map<String, Object>> listMonthlyArchive() {
        return articleMapper.selectMonthlyArchive();
    }

    @Override
    public void incrementViewCount(Long id) {
        Article article = getById(id);
        if (article != null) {
            article.setViewCount(article.getViewCount() + 1);
            updateById(article);
        }
    }

    // ========== 管理接口 ==========

    @Override
    public Page<Article> adminList(ArticleQuery query) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getDeleted, 0);

        if (StringUtils.hasText(query.getTag())) {
            wrapper.apply("JSON_CONTAINS(tags, {0})", "\"" + query.getTag() + "\"");
        }

        wrapper.orderByDesc(Article::getCreatedAt);

        return page(new Page<>(query.getPage(), query.getSize()), wrapper);
    }

    @Override
    public Article getById(Long id) {
        Article article = super.getById(id);
        if (article == null) {
            throw new ResourceNotFoundException("文章不存在: id=" + id);
        }
        return article;
    }

    @Override
    public Article create(Article article) {
        save(article);
        return article;
    }

    @Override
    public Article update(Article article) {
        Article existing = getById(article.getId());
        updateById(article);
        return article;
    }

    @Override
    public void delete(Long id) {
        Article existing = getById(id);
        removeById(id);
    }
}
