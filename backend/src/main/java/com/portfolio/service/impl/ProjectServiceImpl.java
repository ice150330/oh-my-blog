package com.portfolio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.portfolio.common.exception.ResourceNotFoundException;
import com.portfolio.dto.ProjectQuery;
import com.portfolio.entity.Project;
import com.portfolio.mapper.ProjectMapper;
import com.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 项目服务实现
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    private final ProjectMapper projectMapper;

    @Override
    public Page<Project> listPublished(ProjectQuery query) {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getStatus, 1)
               .eq(Project::getDeleted, 0);

        // 按分类筛选
        if (StringUtils.hasText(query.getCategory())) {
            wrapper.eq(Project::getCategory, query.getCategory());
        }

        // 按排序序号升序
        wrapper.orderByAsc(Project::getSortOrder);

        return page(new Page<>(query.getPage(), query.getSize()), wrapper);
    }

    @Override
    public Project getBySlug(String slug) {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getSlug, slug)
               .eq(Project::getStatus, 1)
               .eq(Project::getDeleted, 0);

        Project project = getOne(wrapper);
        if (project == null) {
            throw new ResourceNotFoundException("项目不存在: " + slug);
        }
        return project;
    }

    @Override
    public List<String> listCategories() {
        return projectMapper.selectCategories();
    }

    // ========== 管理接口 ==========

    @Override
    public Page<Project> adminList(ProjectQuery query) {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getDeleted, 0);

        if (StringUtils.hasText(query.getCategory())) {
            wrapper.eq(Project::getCategory, query.getCategory());
        }

        wrapper.orderByAsc(Project::getSortOrder);

        return page(new Page<>(query.getPage(), query.getSize()), wrapper);
    }

    @Override
    public Project getById(Long id) {
        Project project = super.getById(id);
        if (project == null) {
            throw new ResourceNotFoundException("项目不存在: id=" + id);
        }
        return project;
    }

    @Override
    public Project create(Project project) {
        save(project);
        return project;
    }

    @Override
    public Project update(Project project) {
        Project existing = getById(project.getId());
        updateById(project);
        return project;
    }

    @Override
    public void delete(Long id) {
        Project existing = getById(id);
        removeById(id);
    }
}
