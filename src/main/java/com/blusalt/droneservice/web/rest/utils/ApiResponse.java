package com.blusalt.droneservice.web.rest.utils;

/**
 * @author Young Maryclaret Nnenna <claretyoung@gmail.com>
 */
public class ApiResponse<T> {
    private String message;
    private Integer code;
    private T data;

    public ApiResponse(int code, String message, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public ApiResponse(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public ApiResponse() {
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

