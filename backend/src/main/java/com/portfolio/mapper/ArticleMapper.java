package com.portfolio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.portfolio.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文章 Mapper
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询所有不重复的标签
     */
    List<String> selectTags();

    /**
     * 按月归档统计
     * @return [{"month": "2024-01", "count": 5}, ...]
     */
    List<Map<String, Object>> selectMonthlyArchive();
}
