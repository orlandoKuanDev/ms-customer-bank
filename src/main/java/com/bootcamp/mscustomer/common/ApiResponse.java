package com.bootcamp.mscustomer.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * The type Api response.
 *
 * @param <T> the type parameter
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private T data;
}
