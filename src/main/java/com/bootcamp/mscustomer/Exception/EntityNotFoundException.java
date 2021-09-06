package com.bootcamp.mscustomer.Exception;

import com.bootcamp.mscustomer.util.I18AbleException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotFoundException extends I18AbleException {
    public EntityNotFoundException(String key, Object... args) {
        super(key, args);
    }
}
