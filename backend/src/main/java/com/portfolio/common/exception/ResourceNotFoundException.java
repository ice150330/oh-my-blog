package com.portfolio.common.exception;

import lombok.Getter;

/**
 * 资源不存在异常
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final int code;

    public ResourceNotFoundException(String message) {
        super(message);
        this.code = 404;
    }

    public ResourceNotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }
}
