package dev.anilp.ecommerce.phone;

import dev.anilp.ecommerce.phone.dto.NewPhoneRequest;
import dev.anilp.ecommerce.phone.dto.PhoneResponse;
import dev.anilp.ecommerce.phone.dto.UpdatePhoneRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PhoneService {

    PhoneResponse findById(Long phoneId, Authentication connectedUser);

    List<PhoneResponse> findAll(Authentication connectedUser);

    void addPhone(NewPhoneRequest newPhoneRequest, Authentication connectedUser);

    void deletePhone(Long phoneId, Authentication connectedUser);

    void updatePhone(Long phoneId, UpdatePhoneRequest updatePhoneRequest, Authentication connectedUser);
}
