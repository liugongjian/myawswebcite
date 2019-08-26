package com.myawscite.backend.entity;


public class RestResult<T> {

    private Integer success;

    private Integer code;

    private String message;

    private T data;

    public RestResult() {
    }

    public RestResult(Integer success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> RestResult<T> genResult(Integer success, Integer code, String message, T data) {
        return new RestResult<>(success, code, message, data);
    }

    public static <T> RestResult<T> genSuccessResult(T data) {
        return new RestResult<>(0, 200, "", data);
    }

    public static <T> RestResult<T> genSuccessResult(String message, T data) {
        return new RestResult<>(0, 200, message, data);
    }

    public static <T> RestResult<T> genFailResult(Integer code, String message) {
        return new RestResult<>(1, code, message, null);
    }

}
