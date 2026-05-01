package com.portfolio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.portfolio.entity.VisitorDaily;
import org.apache.ibatis.annotations.Param;

/**
 * 访客统计 Mapper
 */
public interface VisitorDailyMapper extends BaseMapper<VisitorDaily> {

    /**
     * 插入或更新今日统计（PV +1）
     */
    int upsertPv(@Param("date") String date);

    /**
     * 插入或更新今日统计（UV +1）
     */
    int upsertUv(@Param("date") String date);
}
