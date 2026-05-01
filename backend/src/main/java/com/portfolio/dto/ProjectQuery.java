package com.portfolio.dto;

import lombok.Data;

/**
 * 项目查询参数
 */
@Data
public class ProjectQuery {

    /** 分类筛选 */
    private String category;

    /** 页码（从 1 开始） */
    private Integer page = 1;

    /** 每页大小 */
    private Integer size = 10;
}
