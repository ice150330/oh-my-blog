package com.portfolio.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 项目实体
 */
@Data
@TableName("project")
public class Project {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** URL 标识符 */
    private String slug;

    /** 项目标题 */
    private String title;

    /** 项目简介 */
    private String summary;

    /** 封面图片 URL */
    private String coverUrl;

    /** 分类：data-engineering/data-analysis/data-visualization/machine-learning */
    private String category;

    /** 技术栈 JSON 数组 */
    private String techStack;

    /** 项目亮点 JSON 数组 */
    private String highlights;

    /** Demo 地址 */
    private String demoUrl;

    /** 仓库地址 */
    private String repoUrl;

    /** 排序序号 */
    private Integer sortOrder;

    /** 状态：1=已发布 0=草稿 */
    private Integer status;

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
