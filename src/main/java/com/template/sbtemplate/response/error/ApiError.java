package com.template.sbtemplate.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApiError {

    @JsonProperty("success")
    private boolean success;
    @JsonProperty("status")
    private HttpStatus status;
    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    @JsonProperty("error")
    private Error error;

    private ApiError() {
        this.success = false;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
        this.error = new Error("Unexpected Error", "Unexpected Error");
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.error = new Error("Unexpected error", ex.getLocalizedMessage());
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.error = new Error(message, ex.getLocalizedMessage());
    }

    private void addSubError(ApiSubError error) {
        if (null == this.error.subErrors) {
            this.error.subErrors = new ArrayList<>();
        }
        this.error.subErrors.add(error);
    }

    public void addError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    public void addError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    private void addValidationError(FieldError error) {
        this.addError(
            error.getObjectName(),
            error.getField(),
            error.getRejectedValue(),
            error.getDefaultMessage()
        );
    }

    public void addValidationErrors(List<FieldError> errors) {
        errors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addError(
            objectError.getObjectName(),
            objectError.getDefaultMessage()
        );
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    public class Error {

        @JsonProperty("message")
        private String message;
        @JsonProperty("debug_message")
        private String debugMessage;
        @JsonProperty("sub_errors")
        private List<ApiSubError> subErrors;

        public Error(String message, String debugMessage) {
            this.message = message;
            this.debugMessage = debugMessage;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDebugMessage() {
            return debugMessage;
        }

        public void setDebugMessage(String debugMessage) {
            this.debugMessage = debugMessage;
        }

        public List<ApiSubError> getSubErrors() {
            return subErrors;
        }

        public void setSubErrors(List<ApiSubError> subErrors) {
            this.subErrors = subErrors;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
