package com.portfolio.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.portfolio.dto.ProjectQuery;
import com.portfolio.entity.Project;

import java.util.List;

/**
 * 项目服务接口
 */
public interface ProjectService {

    /**
     * 分页查询已发布的项目列表（支持按分类筛选）
     */
    Page<Project> listPublished(ProjectQuery query);

    /**
     * 根据 slug 查询项目详情
     */
    Project getBySlug(String slug);

    /**
     * 获取所有分类
     */
    List<String> listCategories();

    // ========== 管理接口 ==========

    /**
     * 管理端分页查询（含草稿）
     */
    Page<Project> adminList(ProjectQuery query);

    /**
     * 根据 ID 查询
     */
    Project getById(Long id);

    /**
     * 创建项目
     */
    Project create(Project project);

    /**
     * 更新项目
     */
    Project update(Project project);

    /**
     * 删除项目（逻辑删除）
     */
    void delete(Long id);
}
