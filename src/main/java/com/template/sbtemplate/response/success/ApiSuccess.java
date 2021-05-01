package com.template.sbtemplate.response.success;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

public class ApiSuccess<T> {
    @JsonProperty("data")
    private T data;
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("status")
    private HttpStatus status;
    @JsonProperty("timestamp")
    private OffsetDateTime timestamp;

    public static <T> ResponseEntity<ApiSuccess<T>> ok(T data) {
        return ResponseEntity.ok(
            new ApiSuccess<T>()
                .setData(data)
                .setSuccess(true)
                .setStatus(HttpStatus.OK)
                .setTimestamp(OffsetDateTime.now())
        );
    }

    public T getData() {
        return data;
    }

    public ApiSuccess<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ApiSuccess<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public ApiSuccess<T> setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ApiSuccess<T> setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
