package dev.anilp.ecommerce.address;

import dev.anilp.ecommerce.address.dto.AddressResponse;
import dev.anilp.ecommerce.address.dto.NewAddressRequest;
import dev.anilp.ecommerce.address.dto.UpdateAddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toAddress(NewAddressRequest newAddressRequest);

    AddressResponse toAddressResponse(Address address);

    void updateAddress(@MappingTarget Address address, UpdateAddressRequest updateAddressRequest);
}
