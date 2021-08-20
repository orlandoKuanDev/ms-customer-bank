package com.bootcamp.mscustomer.Exception;

import com.bootcamp.mscustomer.util.I18AbleException;

/**
 * The type Entity not found exception.
 */
public class EntityNotFoundException extends I18AbleException {
    /**
     * Instantiates a new Entity not found exception.
     *
     * @param key  the key
     * @param args the args
     */
    public EntityNotFoundException(String key, Object... args) {
        super(key, args);
    }
}
