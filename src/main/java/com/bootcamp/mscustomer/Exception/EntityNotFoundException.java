package com.bootcamp.mscustomer.Exception;

import com.bootcamp.mscustomer.util.I18AbleException;

public class EntityNotFoundException extends I18AbleException {
    public EntityNotFoundException(String key, Object... args) {
        super(key, args);
    }
}
