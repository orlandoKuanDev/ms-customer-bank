package com.bootcamp.mscustomer.Exception;

import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.Set;

@Component
public class ValidationService {
    public void validate(Object param) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(param);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
