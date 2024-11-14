package dev.anilp.ecommerce.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException exp, HttpServletRequest request) {
        final HttpStatus status = NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(ExceptionResponse.builder()
                        .path(request.getRequestURI())
                        .error(exp.getClass().getSimpleName())
                        .message(exp.getMessage())
                        .statusCode(status.value())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp, HttpServletRequest request) {
        final HttpStatus status = BAD_REQUEST;
        List<String> errors = exp.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseEntity
                .status(status)
                .body(ExceptionResponse.builder()
                        .path(request.getRequestURI())
                        .error(exp.getClass().getSimpleName())
                        .statusCode(status.value())
                        .timestamp(LocalDateTime.now())
                        .errors(errors)
                        .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp, HttpServletRequest request) {
        final HttpStatus status = INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(status)
                .body(ExceptionResponse.builder()
                        .path(request.getRequestURI())
                        .error(exp.getClass().getSimpleName())
                        .message(exp.getMessage())
                        .statusCode(status.value())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

}
