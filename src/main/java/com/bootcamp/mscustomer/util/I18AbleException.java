package com.bootcamp.mscustomer.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type 18 able exception.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class I18AbleException extends RuntimeException{
    /**
     * The Params.
     */
    protected final transient Object[] params;

    /**
     * Instantiates a new 18 able exception.
     *
     * @param key  the key
     * @param args the args
     */
    public I18AbleException(String key, Object... args) {
        super(key);
        params = args;
    }
}