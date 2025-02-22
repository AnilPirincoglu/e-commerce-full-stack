package dev.anilp.ecommerce.address.dto;

import dev.anilp.ecommerce.address.AddressType;

public record AddressResponse(
        Long id,
        String name,
        AddressType addressType,
        String addressLine,
        String street,
        String district,
        String city,
        String postalCode
) {
}
