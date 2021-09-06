package com.bootcamp.mscustomer.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
class GlobalErrorAttributes extends DefaultErrorAttributes {
    private static final Logger logger = LoggerFactory.getLogger(GlobalErrorAttributes.class);
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,  ErrorAttributeOptions options) {
        Throwable error = super.getError(request);
        Map<String, Object> errorAttributes = new HashMap<>(8);
        errorAttributes.put("message", error.getMessage());
        errorAttributes.put("method", request.methodName());
        errorAttributes.put("path", request.path());
        MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations
                .from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
        HttpStatus errorStatus = determineHttpStatusCode(error, responseStatusAnnotation);
        errorAttributes.put("status", errorStatus.value());
        errorAttributes.put("code", errorStatus.value());
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("requestId", request.exchange().getRequest().getId());
        errorAttributes.put("error", errorStatus.getReasonPhrase());
        errorAttributes.put("exception", error.getClass().getName());
        return errorAttributes;
    }

    private HttpStatus determineHttpStatusCode(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation) {
        if (error instanceof ResponseStatusException) {
            return ((ResponseStatusException) error).getStatus();
        }
        return responseStatusAnnotation.getValue("code", HttpStatus.class).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}