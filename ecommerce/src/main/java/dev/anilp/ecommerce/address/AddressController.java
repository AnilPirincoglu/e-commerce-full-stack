package dev.anilp.ecommerce.address;

import dev.anilp.ecommerce.address.dto.AddressResponse;
import dev.anilp.ecommerce.address.dto.NewAddressRequest;
import dev.anilp.ecommerce.address.dto.UpdateAddressRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Tag(name = "Address", description = "Endpoints for managing addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("{address-id}")
    public ResponseEntity<AddressResponse> findAddressById(
            @PathVariable("address-id") Long addressId,
            Authentication connectedUser) {
        return ResponseEntity.ok(addressService.findById(addressId, connectedUser));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> findAllAddresses(Authentication connectedUser) {
        return ResponseEntity.ok(addressService.findAll(connectedUser));
    }

    @PostMapping
    public ResponseEntity<Void> saveAddress(
            @Valid @RequestBody NewAddressRequest newAddressRequest,
            Authentication connectedUser) {
        addressService.addAddress(newAddressRequest, connectedUser);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{address-id}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable("address-id") Long addressId,
            Authentication connectedUser) {
        addressService.deleteAddress(addressId, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{address-id}")
    public ResponseEntity<Void> updateAddress(
            @PathVariable("address-id") Long addressId,
            @Valid @RequestBody UpdateAddressRequest updateAddressRequest,
            Authentication connectedUser) {
        addressService.updateAddress(addressId, updateAddressRequest, connectedUser);
        return ResponseEntity.ok().build();
    }
}
