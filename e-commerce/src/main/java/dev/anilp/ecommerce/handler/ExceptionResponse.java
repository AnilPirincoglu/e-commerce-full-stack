package dev.anilp.ecommerce.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ExceptionResponse(
        String path,
        String error,
        String message,
        Integer statusCode,
        LocalDateTime timestamp,
        List<String> errors
) {
}
