package com.portfolio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.portfolio.entity.Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 项目 Mapper
 */
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 查询所有不重复的分类
     */
    @Select("SELECT DISTINCT category FROM project WHERE deleted = 0 AND status = 1")
    List<String> selectCategories();
}
