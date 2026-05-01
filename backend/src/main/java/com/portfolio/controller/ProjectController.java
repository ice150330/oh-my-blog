package com.portfolio.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.portfolio.dto.ProjectQuery;
import com.portfolio.entity.Project;
import com.portfolio.service.ProjectService;
import com.portfolio.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目控制器
 */
@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // ==================== 公开接口 ====================

    /**
     * 分页查询已发布项目
     */
    @GetMapping("/api/v1/projects")
    public Result<Page<Project>> list(ProjectQuery query) {
        return Result.success(projectService.listPublished(query));
    }

    /**
     * 根据 slug 查询项目详情
     */
    @GetMapping("/api/v1/projects/{slug}")
    public Result<Project> detail(@PathVariable String slug) {
        return Result.success(projectService.getBySlug(slug));
    }

    /**
     * 获取所有分类
     */
    @GetMapping("/api/v1/projects/categories")
    public Result<List<String>> categories() {
        return Result.success(projectService.listCategories());
    }

    // ==================== 管理接口 ====================

    /**
     * 管理端列表（含草稿）
     */
    @GetMapping("/api/v1/admin/projects")
    public Result<Page<Project>> adminList(ProjectQuery query) {
        return Result.success(projectService.adminList(query));
    }

    /**
     * 管理端详情
     */
    @GetMapping("/api/v1/admin/projects/{id}")
    public Result<Project> adminDetail(@PathVariable Long id) {
        return Result.success(projectService.getById(id));
    }

    /**
     * 创建项目
     */
    @PostMapping("/api/v1/admin/projects")
    public Result<Project> create(@RequestBody Project project) {
        return Result.success(projectService.create(project));
    }

    /**
     * 更新项目
     */
    @PutMapping("/api/v1/admin/projects/{id}")
    public Result<Project> update(@PathVariable Long id, @RequestBody Project project) {
        project.setId(id);
        return Result.success(projectService.update(project));
    }

    /**
     * 删除项目
     */
    @DeleteMapping("/api/v1/admin/projects/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return Result.success();
    }
}
