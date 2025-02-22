package dev.anilp.ecommerce.phone;

import dev.anilp.ecommerce.phone.dto.NewPhoneRequest;
import dev.anilp.ecommerce.phone.dto.PhoneResponse;
import dev.anilp.ecommerce.phone.dto.UpdatePhoneRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PhoneMapper {
    Phone toPhone(NewPhoneRequest newPhoneRequest);

    PhoneResponse toPhoneResponse(Phone phone);

    void updatePhone(@MappingTarget Phone phone, UpdatePhoneRequest updatePhoneRequest);
}
