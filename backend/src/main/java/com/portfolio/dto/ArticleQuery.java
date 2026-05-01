package com.portfolio.dto;

import lombok.Data;

/**
 * 文章查询参数
 */
@Data
public class ArticleQuery {

    /** 标签筛选 */
    private String tag;

    /** 页码（从 1 开始） */
    private Integer page = 1;

    /** 每页大小 */
    private Integer size = 10;
}
