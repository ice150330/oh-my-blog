package com.portfolio.controller;

import com.portfolio.entity.VisitorDaily;
import com.portfolio.service.VisitorService;
import com.portfolio.vo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 访客统计控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    /** SSE 客户端列表 */
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    /**
     * SSE 长连接
     */
    @GetMapping(value = "/api/v1/visitor/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam(required = false, defaultValue = "anonymous") String visitorId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitters.add(emitter);

        // 初始在线人数
        visitorService.heartbeat(visitorId);
        sendOnlineCount();

        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            sendOnlineCount();
        });

        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            sendOnlineCount();
        });

        emitter.onError(e -> {
            emitters.remove(emitter);
            sendOnlineCount();
        });

        return emitter;
    }

    /**
     * 心跳接口
     */
    @PostMapping("/api/v1/visitor/heartbeat")
    public Result<Map<String, Long>> heartbeat(@RequestParam(required = false, defaultValue = "anonymous") String visitorId) {
        visitorService.heartbeat(visitorId);
        return Result.success(Map.of("online", visitorService.getOnlineCount()));
    }

    /**
     * 记录 PV
     */
    @PostMapping("/api/v1/visitor/pv")
    public Result<Void> recordPv() {
        visitorService.recordPv();
        return Result.success();
    }

    /**
     * 获取最近 N 天访问统计
     */
    @GetMapping("/api/v1/visitor/stats")
    public Result<List<VisitorDaily>> stats(@RequestParam(defaultValue = "7") int days) {
        return Result.success(visitorService.getRecentStats(days));
    }

    /**
     * 获取总访问统计
     */
    @GetMapping("/api/v1/visitor/total")
    public Result<Map<String, Long>> totalStats() {
        return Result.success(visitorService.getTotalStats());
    }

    /**
     * 向所有 SSE 客户端广播在线人数
     */
    private void sendOnlineCount() {
        long onlineCount = visitorService.getOnlineCount();
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("online")
                        .data(onlineCount));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}
