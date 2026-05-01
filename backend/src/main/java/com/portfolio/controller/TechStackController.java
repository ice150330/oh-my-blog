package com.portfolio.controller;

import com.portfolio.entity.TechStack;
import com.portfolio.service.TechStackService;
import com.portfolio.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 技术栈控制器
 */
@RestController
@RequiredArgsConstructor
public class TechStackController {

    private final TechStackService techStackService;

    /**
     * 获取技术栈（按分类分组）
     */
    @GetMapping("/api/v1/techstack")
    public Result<Map<String, List<TechStack>>> listGrouped() {
        return Result.success(techStackService.listGrouped());
    }

    /**
     * 获取雷达图数据
     */
    @GetMapping("/api/v1/techstack/radar")
    public Result<List<Map<String, Object>>> radarData() {
        return Result.success(techStackService.getRadarData());
    }

    /**
     * 获取技术栈列表（不分组）
     */
    @GetMapping("/api/v1/techstack/list")
    public Result<List<TechStack>> listAll() {
        return Result.success(techStackService.listAll());
    }
}
