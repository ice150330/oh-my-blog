package com.portfolio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.portfolio.entity.TechStack;
import com.portfolio.mapper.TechStackMapper;
import com.portfolio.service.TechStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 技术栈服务实现
 */
@Service
@RequiredArgsConstructor
public class TechStackServiceImpl extends ServiceImpl<TechStackMapper, TechStack> implements TechStackService {

    private final TechStackMapper techStackMapper;

    @Override
    public Map<String, List<TechStack>> listGrouped() {
        LambdaQueryWrapper<TechStack> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(TechStack::getSortOrder);

        List<TechStack> all = list(wrapper);

        return all.stream()
                .collect(Collectors.groupingBy(
                        TechStack::getCategory,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    @Override
    public List<Map<String, Object>> getRadarData() {
        LambdaQueryWrapper<TechStack> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(TechStack::getSortOrder);

        List<TechStack> all = list(wrapper);

        return all.stream()
                .map(tech -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("name", tech.getName());
                    item.put("value", tech.getProficiency());
                    item.put("category", tech.getCategory());
                    return item;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TechStack> listAll() {
        LambdaQueryWrapper<TechStack> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(TechStack::getSortOrder);
        return list(wrapper);
    }
}
