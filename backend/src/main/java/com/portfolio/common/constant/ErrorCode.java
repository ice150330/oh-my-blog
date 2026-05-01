package com.portfolio.common.constant;

/**
 * 错误码常量
 */
public final class ErrorCode {

    private ErrorCode() {
        throw new UnsupportedOperationException("常量类不可实例化");
    }

    // --- 通用错误码 ---
    public static final int SUCCESS = 200;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int CONFLICT = 409;
    public static final int INTERNAL_ERROR = 500;

    // --- 业务错误码 ---
    /** 参数校验失败 */
    public static final int VALIDATION_ERROR = 4001;
    /** 用户名或密码错误 */
    public static final int LOGIN_FAILED = 4011;
    /** Token 过期或无效 */
    public static final int TOKEN_INVALID = 4012;
    /** 资源不存在 */
    public static final int RESOURCE_NOT_FOUND = 4041;
    /** 文件上传失败 */
    public static final int UPLOAD_FAILED = 5001;
}
