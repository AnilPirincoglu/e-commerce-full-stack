package dev.anilp.ecommerce.address;

import dev.anilp.ecommerce.address.dto.AddressResponse;
import dev.anilp.ecommerce.address.dto.NewAddressRequest;
import dev.anilp.ecommerce.address.dto.UpdateAddressRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AddressService {

    AddressResponse findById(Long addressId, Authentication connectedUser);

    List<AddressResponse> findAll(Authentication connectedUser);

    void addAddress(NewAddressRequest newAddressRequest, Authentication connectedUser);

    void deleteAddress(Long addressId, Authentication connectedUser);

    void updateAddress(Long addressId, UpdateAddressRequest updateAddressRequest, Authentication connectedUser);

}
