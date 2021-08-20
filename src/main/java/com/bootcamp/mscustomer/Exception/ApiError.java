package com.bootcamp.mscustomer.Exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * HTTP [RFC7230]
 */
@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
public class ApiError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;
    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status the status
     */
    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status  the status
     * @param message the message
     */
    public ApiError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status the status
     * @param ex     the ex
     */
    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status  the status
     * @param message the message
     * @param ex      the ex
     */
    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    /**
     * Add validation error.
     *
     * @param object  the object
     * @param message the message
     */
    public void addValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    /**
     * Add validation errors.
     *
     * @param fieldErrors the field errors
     */
    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    /**
     * Add validation error.
     *
     * @param globalErrors the global errors
     */
    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    /**
     * Add validation errors.
     *
     * @param constraintViolations the constraint violations
     */
    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }
}
