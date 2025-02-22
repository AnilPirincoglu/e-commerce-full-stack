package dev.anilp.ecommerce.auth;

import dev.anilp.ecommerce.user.Gender;
import dev.anilp.ecommerce.validation.Age;
import dev.anilp.ecommerce.validation.Password;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RegistrationRequest(
        @NotBlank(message = "Firstname cannot be blank")
        @Size(min = 2, max = 50, message = "Firstname must be between 2 and 50 characters")
        String firstname,
        @NotBlank(message = "Lastname cannot be blank")
        @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters")
        String lastname,
        @NotNull(message = "Gender cannot be null")
        @Enumerated(EnumType.STRING)
        Gender gender,
        @NotNull(message = "Date of birth cannot be null. Please use the format yyyy-MM-dd.")
        @Past(message = "Date of birth must be a past date")
        @Age
        LocalDate dateOfBirth,
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Password cannot be blank")
        @Password
        String password

) {
}
