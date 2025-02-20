package dev.anilp.ecommerce.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    DISABLED_ACCOUNT("User account is disabled"),
    BAD_CREDENTIALS("Username and / or password is incorrect"),
    ACCOUNT_LOCKED("User account is locked"),
    USER_NOT_FOUND("User not found"),
    RESOURCE_NOT_FOUND("Resource not found"),
    DUPLICATE_RESOURCE("Resource already exists"),
    ROLE_NOT_FOUND("Role not found"),
    EXPIRED("Token has expired"),
    INVALID_TOKEN("Token is invalid");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}
