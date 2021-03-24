package com.sh.demo.common.exception;


/**
 * 自定义消息异常
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}