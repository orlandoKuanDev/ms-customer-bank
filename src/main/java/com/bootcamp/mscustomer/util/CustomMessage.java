package com.bootcamp.mscustomer.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * The type Custom message.
 */
@Component
public class CustomMessage {
    private final MessageSource messageSource;

    /**
     * Instantiates a new Custom message.
     *
     * @param messageSource the message source
     */
    public CustomMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Get local message string.
     *
     * @param key    the key
     * @param params the params
     * @return the string
     */
    public String getLocalMessage(String key, Object[] params){
        return messageSource.getMessage(key, params, Locale.ENGLISH);
    }

    /**
     * Get local message string.
     *
     * @param key the key
     * @return the string
     */
    public String getLocalMessage(String key){
        return messageSource.getMessage(key, null, Locale.ENGLISH);
    }
}
