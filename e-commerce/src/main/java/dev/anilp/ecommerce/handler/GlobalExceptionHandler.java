package dev.anilp.ecommerce.handler;

import dev.anilp.ecommerce.exception.DuplicateResourceException;
import dev.anilp.ecommerce.exception.ExpiredTokenException;
import dev.anilp.ecommerce.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;

import static dev.anilp.ecommerce.exception.ExceptionMessage.ACCOUNT_LOCKED;
import static dev.anilp.ecommerce.exception.ExceptionMessage.BAD_CREDENTIALS;
import static dev.anilp.ecommerce.exception.ExceptionMessage.DISABLED_ACCOUNT;
import static dev.anilp.ecommerce.exception.ExceptionMessage.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(ResourceNotFoundException exp, HttpServletRequest request) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .path(request.getRequestURI())
                        .error(exp.getClass().getSimpleName())
                        .message(exp.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ExceptionResponse> handleException(ExpiredTokenException exp) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(ExceptionResponse.builder()
                        .error(exp.getClass().getSimpleName())
                        .message(exp.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ExceptionResponse> handleException(DuplicateResourceException exp, HttpServletRequest request) {
        return ResponseEntity
                .status(CONFLICT)
                .body(ExceptionResponse.builder()
                        .path(request.getRequestURI())
                        .error(exp.getClass().getSimpleName())
                        .message(exp.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        final HttpStatus status = UNAUTHORIZED;
        return ResponseEntity
                .status(status)
                .body(ExceptionResponse.builder()
                        .error(exp.getClass().getSimpleName())
                        .message(DISABLED_ACCOUNT.getMessage())
                        .statusCode(status.value())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        final HttpStatus status = UNAUTHORIZED;
        return ResponseEntity
                .status(status)
                .body(ExceptionResponse.builder()
                        .error(exp.getClass().getSimpleName())
                        .message(BAD_CREDENTIALS.getMessage())
                        .statusCode(status.value())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(ExceptionResponse.builder()
                        .error(exp.getClass().getSimpleName())
                        .message(ACCOUNT_LOCKED.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .error(exp.getClass().getSimpleName())
                        .message(USER_NOT_FOUND.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .error(exp.getClass().getSimpleName())
                        .message(exp.getMessage())
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
        getErrorLog(exp);

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

    private void getErrorLog(Exception exp) {
        LOG.error("Error Name: {}, Error Message: {}, Stack-Trace:\n{}",
                exp.getClass().getSimpleName(),
                exp.getMessage(),
                getStackTraceAsString(exp));
    }

    private String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

}
