package com.portfolio.service;

import com.portfolio.entity.VisitorDaily;

import java.util.List;
import java.util.Map;

/**
 * 访客统计服务接口
 */
public interface VisitorService {

    /**
     * 记录一次页面访问（PV）
     */
    void recordPv();

    /**
     * 记录一次独立访客（UV）
     */
    void recordUv();

    /**
     * 记录访客心跳（Redis 30s TTL）
     * @param visitorId 访客唯一标识
     */
    void heartbeat(String visitorId);

    /**
     * 获取当前在线人数
     */
    long getOnlineCount();

    /**
     * 获取最近 N 天的访问统计
     */
    List<VisitorDaily> getRecentStats(int days);

    /**
     * 获取总访问统计
     */
    Map<String, Long> getTotalStats();
}
