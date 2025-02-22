package dev.anilp.ecommerce.phone.dto;

import dev.anilp.ecommerce.phone.PhoneType;

public record PhoneResponse(
        Long id,
        PhoneType phoneType,
        String phoneNumber
) {
}
