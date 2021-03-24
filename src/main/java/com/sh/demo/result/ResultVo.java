package com.sh.demo.result;

import lombok.Data;

/**
 * 通用返回对象
 * Created by macro on 2019/4/19.
 */
@Data
public class ResultVo<T> {
    /**
     * 失败 ,自定义失败原因
     * 200，成功
     */
    private int code;
    private String msg;
    private T data;

    protected ResultVo() {
    }

    public ResultVo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     */
    public static <T> ResultVo<T> success() {
        return new ResultVo<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     */
    public static <T> ResultVo<T> success(String message) {
        return new ResultVo<T>(ResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ResultVo<T> success(String message, T data) {
        return new ResultVo<T>(ResultCode.SUCCESS.getCode(), message, data);
    }


    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> ResultVo<T> error(IErrorCode errorCode) {
        return new ResultVo<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> ResultVo<T> error(String message) {
        return new ResultVo<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResultVo<T> error() {
        return error(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResultVo<T> validateFailed() {
        return error(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> ResultVo<T> validateFailed(String message) {
        return new ResultVo<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResultVo<T> unauthorized(T data) {
        return new ResultVo<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResultVo<T> forbidden(T data) {
        return new ResultVo<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * 最终返回失败结果
     * @param message 提示信息
     */
    public static <T> ResultVo<T> endError(String message,T data) {
        return new ResultVo<T>(ResultCode.END_FAILED.getCode(), message, data);
    }


}
