package com.template.sbtemplate.response.error;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiValidationError extends ApiSubError {

    @JsonProperty(value = "object")
    private String object;
    @JsonProperty(value = "field")
    private String field;
    @JsonProperty(value = "rejected_value")
    private Object rejectedValue;
    @JsonProperty(value = "message")
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    ApiValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
