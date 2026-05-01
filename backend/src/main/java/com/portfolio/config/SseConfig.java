package com.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * SSE 异步支持配置
 */
@Configuration
@EnableAsync
public class SseConfig {
    // SseEmitter 的异步执行由 @EnableAsync 提供支持
    // 线程池使用 Spring Boot 默认的 SimpleAsyncTaskExecutor
}
