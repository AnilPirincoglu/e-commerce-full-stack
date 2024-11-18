package dev.anilp.ecommerce.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final String DIGIT_PATTERN = ".*\\d.*";
    private static final String LOWERCASE_PATTERN = ".*[a-z].*";
    private static final String UPPERCASE_PATTERN = ".*[A-Z].*";
    private static final String SPECIAL_CHAR_PATTERN = ".*[@#$%^&+=.,_-].*";
    private static final String LENGTH_PATTERN = ".{6,12}";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        if (!Pattern.matches(LENGTH_PATTERN, password)) {
            context.buildConstraintViolationWithTemplate("Password must be between 6 and 12 characters long")
                    .addConstraintViolation();
            isValid = false;
        }

        if (!Pattern.matches(DIGIT_PATTERN, password)) {
            context.buildConstraintViolationWithTemplate("Password must contain at least one digit")
                    .addConstraintViolation();
            isValid = false;
        }

        if (!Pattern.matches(LOWERCASE_PATTERN, password)) {
            context.buildConstraintViolationWithTemplate("Password must contain at least one lowercase letter")
                    .addConstraintViolation();
            isValid = false;
        }

        if (!Pattern.matches(UPPERCASE_PATTERN, password)) {
            context.buildConstraintViolationWithTemplate("Password must contain at least one uppercase letter")
                    .addConstraintViolation();
            isValid = false;
        }

        if (!Pattern.matches(SPECIAL_CHAR_PATTERN, password)) {
            context.buildConstraintViolationWithTemplate("Password must contain at least one special character (@#$%^&+=.,_-)")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}