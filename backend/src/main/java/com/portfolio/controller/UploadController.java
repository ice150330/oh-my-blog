package com.portfolio.controller;

import com.portfolio.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件上传控制器（V2 完整实现）
 */
@Slf4j
@RestController
public class UploadController {

    /**
     * 文件上传（V2 桩实现）
     */
    @PostMapping("/api/v1/admin/upload")
    public Result<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        log.info("收到上传文件: {}, 大小: {} bytes", file.getOriginalFilename(), file.getSize());
        // V2: 实现 OSS/本地存储上传逻辑
        return Result.success("文件上传功能将在 V2 中实现", Map.of(
                "filename", file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown",
                "size", String.valueOf(file.getSize())
        ));
    }
}
