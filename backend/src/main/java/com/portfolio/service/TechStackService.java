package com.portfolio.service;

import com.portfolio.entity.TechStack;

import java.util.List;
import java.util.Map;

/**
 * 技术栈服务接口
 */
public interface TechStackService {

    /**
     * 获取所有技术栈，按分类分组
     * @return Map<分类名, 技术栈列表>
     */
    Map<String, List<TechStack>> listGrouped();

    /**
     * 获取雷达图数据
     * @return [{name: "Java", value: 90}, ...]
     */
    List<Map<String, Object>> getRadarData();

    /**
     * 获取所有技术栈（按 sort_order 排序）
     */
    List<TechStack> listAll();
}
