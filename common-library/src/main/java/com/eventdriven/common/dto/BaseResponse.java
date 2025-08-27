package com.eventdriven.common.dto;

import java.time.LocalDateTime;

public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String errorCode;
    private LocalDateTime timestamp;

    public BaseResponse() {
        this.timestamp = LocalDateTime.now();
    }

    // Success response
    public BaseResponse(T data) {
        this();
        this.success = true;
        this.data = data;
        this.message = "Operation successful";
    }
    // basarili
    public BaseResponse(T data, String message) {
        this();
        this.success = true;
        this.data = data;
        this.message = message;
    }
    // basarisiz
    public BaseResponse(String message, String errorCode) {
        this();
        this.success = false;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(data);
    }

    public static <T> BaseResponse<T> success(T data, String message) {
        return new BaseResponse<>(data, message);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(message, null);
    }

    public static <T> BaseResponse<T> error(String message, String errorCode) {
        return new BaseResponse<>(message, errorCode);
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", errorCode='" + errorCode + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}