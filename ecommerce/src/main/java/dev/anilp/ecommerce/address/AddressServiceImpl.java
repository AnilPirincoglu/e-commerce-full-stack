package dev.anilp.ecommerce.address;

import dev.anilp.ecommerce.address.dto.AddressResponse;
import dev.anilp.ecommerce.address.dto.NewAddressRequest;
import dev.anilp.ecommerce.address.dto.UpdateAddressRequest;
import dev.anilp.ecommerce.exception.ExceptionMessage;
import dev.anilp.ecommerce.exception.ResourceNotFoundException;
import dev.anilp.ecommerce.user.User;
import dev.anilp.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    @Override
    @Transactional
    public AddressResponse findById(Long addressId,
                                    Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Address address = user.getAddresses().stream()
                .filter(item -> item.getId().equals(addressId))
                .findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException(ExceptionMessage.RESOURCE_NOT_FOUND.getMessage()));
        return addressMapper.toAddressResponse(address);
    }

    @Override
    @Transactional
    public List<AddressResponse> findAll(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        return user.getAddresses().stream()
                .map(addressMapper::toAddressResponse)
                .toList();
    }

    @Override
    @Transactional
    public void addAddress(
            NewAddressRequest addressRequest,
            Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Address address = addressMapper.toAddress(addressRequest);
        user.addAddress(address);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Address address = user.getAddresses().stream()
                .filter(item -> item.getId().equals(addressId))
                .findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException(ExceptionMessage.RESOURCE_NOT_FOUND.getMessage()));
        user.removeAddress(address);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateAddress(Long addressId, UpdateAddressRequest updateAddressRequest, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Address address = user.getAddresses().stream()
                .filter(item -> item.getId().equals(addressId))
                .findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException(ExceptionMessage.RESOURCE_NOT_FOUND.getMessage()));
        addressMapper.updateAddress(address, updateAddressRequest);
        addressRepository.save(address);
    }
}
