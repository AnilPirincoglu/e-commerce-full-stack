package dev.anilp.ecommerce.address.dto;

import dev.anilp.ecommerce.address.AddressType;
import dev.anilp.ecommerce.validation.PostalCode;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateAddressRequest(
        @NotNull(message = "Address type cannot be null")
        @Enumerated(EnumType.STRING)
        AddressType addressType,
        @NotBlank(message = "Address name cannot be blank")
        @Size(min = 1, max = 50, message = "Address name must be between 1 and 50 characters")
        String name,
        @NotBlank(message = "Address line cannot be blank")
        @Size(min = 10, max = 255, message = "Address line must be between 10 and 255 characters")
        String addressLine,
        @NotBlank(message = "Street cannot be blank")
        @Size(min = 5, max = 50, message = "Street must be between 5 and 50 characters")
        String street,
        @NotBlank(message = "District cannot be blank")
        @Size(min = 3, max = 50, message = "District must be between 5 and 50 characters")
        String district,
        @NotBlank(message = "City cannot be blank")
        @Size(min = 3, max = 50, message = "City must be between 5 and 50 characters")
        String city,
        @NotBlank(message = "Postal code cannot be blank")
        @PostalCode
        String postalCode
) {
}
