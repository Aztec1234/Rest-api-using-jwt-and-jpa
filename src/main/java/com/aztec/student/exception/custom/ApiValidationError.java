package com.aztec.student.exception.custom;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    @JsonProperty("rejected_value")
    private Object rejectedValue;
    private String message;

    public ApiValidationError(Object rejectedValue, String message) {
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}