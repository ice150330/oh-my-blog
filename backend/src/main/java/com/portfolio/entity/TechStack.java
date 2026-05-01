package com.portfolio.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 技术栈实体
 */
@Data
@TableName("tech_stack")
public class TechStack {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 技术名称 */
    private String name;

    /** 图标名称/URL */
    private String icon;

    /** 分类：language/framework/tool/database/cloud/other */
    private String category;

    /** 熟练度 0-100 */
    private Integer proficiency;

    /** 简要描述 */
    private String description;

    /** 排序序号 */
    private Integer sortOrder;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
