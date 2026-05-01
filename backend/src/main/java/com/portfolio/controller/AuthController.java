package com.portfolio.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.portfolio.common.constant.ErrorCode;
import com.portfolio.dto.LoginRequest;
import com.portfolio.entity.Admin;
import com.portfolio.mapper.AdminMapper;
import com.portfolio.security.JwtTokenProvider;
import com.portfolio.vo.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 管理员登录
     */
    @PostMapping("/api/v1/admin/login")
    public Result<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
        // 查询管理员
        Admin admin = adminMapper.selectOne(
                new LambdaQueryWrapper<Admin>()
                        .eq(Admin::getUsername, request.getUsername())
        );

        if (admin == null || !passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            return Result.error(ErrorCode.LOGIN_FAILED, "用户名或密码错误");
        }

        // 生成 JWT
        String token = jwtTokenProvider.createToken(admin.getUsername());

        return Result.success(Map.of(
                "token", token,
                "username", admin.getUsername(),
                "nickname", admin.getNickname() != null ? admin.getNickname() : admin.getUsername()
        ));
    }
}
