package dev.anilp.ecommerce.phone;

import dev.anilp.ecommerce.exception.DuplicateResourceException;
import dev.anilp.ecommerce.exception.ExceptionMessage;
import dev.anilp.ecommerce.exception.ResourceNotFoundException;
import dev.anilp.ecommerce.phone.dto.NewPhoneRequest;
import dev.anilp.ecommerce.phone.dto.PhoneResponse;
import dev.anilp.ecommerce.phone.dto.UpdatePhoneRequest;
import dev.anilp.ecommerce.user.User;
import dev.anilp.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final PhoneMapper phoneMapper;
    private final UserRepository userRepository;

    @Override
    public PhoneResponse findById(Long phoneId,
                                  Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Phone phone = user.getPhones().stream()
                .filter(item -> item.getId().equals(phoneId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.RESOURCE_NOT_FOUND.getMessage()));
        return phoneMapper.toPhoneResponse(phone);
    }

    @Override
    public List<PhoneResponse> findAll(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        return user.getPhones().stream()
                .map(phoneMapper::toPhoneResponse)
                .toList();
    }

    @Override
    @Transactional
    public void addPhone(NewPhoneRequest newPhoneRequest,
                         Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Phone phone = phoneMapper.toPhone(newPhoneRequest);
        if (phoneRepository.existsByPhoneNumber(phone.getPhoneNumber())) {
            throw new DuplicateResourceException(ExceptionMessage.DUPLICATE_RESOURCE.getMessage());
        }
        user.addPhone(phone);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deletePhone(Long phoneId,
                            Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Phone phone = user.getPhones().stream()
                .filter(item -> item.getId().equals(phoneId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.RESOURCE_NOT_FOUND.getMessage()));
        user.removePhone(phone);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePhone(Long phoneId,
                            UpdatePhoneRequest updatePhoneRequest,
                            Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Phone phone = user.getPhones().stream()
                .filter(item -> item.getId().equals(phoneId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.RESOURCE_NOT_FOUND.getMessage()));
        phoneMapper.updatePhone(phone, updatePhoneRequest);
        phoneRepository.save(phone);
    }
}
