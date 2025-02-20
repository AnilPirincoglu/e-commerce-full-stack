package dev.anilp.ecommerce.phone.dto;

import dev.anilp.ecommerce.phone.PhoneType;
import dev.anilp.ecommerce.validation.PhoneNumber;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record UpdatePhoneRequest(
        @NotNull(message = "Phone type cannot be null")
        @Enumerated(EnumType.STRING)
        PhoneType phoneType,
        @PhoneNumber
        String phoneNumber
) {
}
