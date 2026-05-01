package com.portfolio.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章实体
 */
@Data
@TableName("article")
public class Article {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** URL 标识符 */
    private String slug;

    /** 文章标题 */
    private String title;

    /** 文章摘要 */
    private String summary;

    /** Markdown 正文 */
    private String content;

    /** 封面图片 URL */
    private String coverUrl;

    /** 标签 JSON 数组 */
    private String tags;

    /** 状态：1=已发布 0=草稿 */
    private Integer status;

    /** 阅读量 */
    private Long viewCount;

    /** 逻辑删除 */
    @TableLogic
    private Integer deleted;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
