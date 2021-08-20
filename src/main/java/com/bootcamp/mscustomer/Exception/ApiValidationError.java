package com.bootcamp.mscustomer.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Api validation error.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationError extends ApiSubError{
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    /**
     * Instantiates a new Api validation error.
     *
     * @param object  the object
     * @param message the message
     */
    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
