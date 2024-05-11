package com.wolroys.wellbeing.util.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

abstract class SubViolation {
}

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ValidationError extends SubViolation {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
