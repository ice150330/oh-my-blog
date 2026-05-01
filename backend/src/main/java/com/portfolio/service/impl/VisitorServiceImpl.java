package com.portfolio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.portfolio.entity.VisitorDaily;
import com.portfolio.mapper.VisitorDailyMapper;
import com.portfolio.service.VisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 访客统计服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorDailyMapper visitorDailyMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String VISITOR_KEY_PREFIX = "visitor:";
    private static final Duration HEARTBEAT_TTL = Duration.ofSeconds(30);
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void recordPv() {
        String today = LocalDate.now().format(DATE_FMT);
        visitorDailyMapper.upsertPv(today);
    }

    @Override
    public void recordUv() {
        String today = LocalDate.now().format(DATE_FMT);
        visitorDailyMapper.upsertUv(today);
    }

    @Override
    public void heartbeat(String visitorId) {
        String key = VISITOR_KEY_PREFIX + visitorId;
        redisTemplate.opsForValue().set(key, "1", HEARTBEAT_TTL);
    }

    @Override
    public long getOnlineCount() {
        Set<String> keys = redisTemplate.keys(VISITOR_KEY_PREFIX + "*");
        return keys != null ? keys.size() : 0;
    }

    @Override
    public List<VisitorDaily> getRecentStats(int days) {
        LambdaQueryWrapper<VisitorDaily> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(VisitorDaily::getDate, LocalDate.now().minusDays(days - 1))
               .orderByAsc(VisitorDaily::getDate);

        return visitorDailyMapper.selectList(wrapper);
    }

    @Override
    public Map<String, Long> getTotalStats() {
        LambdaQueryWrapper<VisitorDaily> wrapper = new LambdaQueryWrapper<>();
        List<VisitorDaily> all = visitorDailyMapper.selectList(wrapper);

        long totalPv = all.stream().mapToLong(VisitorDaily::getPv).sum();
        long totalUv = all.stream().mapToLong(VisitorDaily::getUv).sum();

        Map<String, Long> stats = new HashMap<>();
        stats.put("totalPv", totalPv);
        stats.put("totalUv", totalUv);
        return stats;
    }
}
