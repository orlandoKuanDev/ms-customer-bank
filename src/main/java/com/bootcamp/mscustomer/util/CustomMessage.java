package com.bootcamp.mscustomer.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CustomMessage {
    private final MessageSource messageSource;
    public CustomMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public String getLocalMessage(String key, Object[] params){
        return messageSource.getMessage(key, params, Locale.ENGLISH);
    }
    public String getLocalMessage(String key){
        return messageSource.getMessage(key, null, Locale.ENGLISH);
    }
}
