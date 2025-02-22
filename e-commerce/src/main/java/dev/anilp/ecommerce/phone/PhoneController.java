package dev.anilp.ecommerce.phone;

import dev.anilp.ecommerce.phone.dto.NewPhoneRequest;
import dev.anilp.ecommerce.phone.dto.PhoneResponse;
import dev.anilp.ecommerce.phone.dto.UpdatePhoneRequest;
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
@RequestMapping("/phones")
@RequiredArgsConstructor
@Tag(name = "Phone", description = "Endpoints for managing phones")
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping("{phone-id}")
    public ResponseEntity<PhoneResponse> findPhoneById(
            @PathVariable("phone-id") Long phoneId,
            Authentication connectedUser) {
        return ResponseEntity.ok(phoneService.findById(phoneId, connectedUser));
    }

    @GetMapping
    public ResponseEntity<List<PhoneResponse>> findAllPhones(Authentication connectedUser) {
        return ResponseEntity.ok(phoneService.findAll(connectedUser));
    }

    @PostMapping
    public ResponseEntity<Void> savePhone(@Valid @RequestBody NewPhoneRequest newPhoneRequest,
                                          Authentication connectedUser) {
        phoneService.addPhone(newPhoneRequest, connectedUser);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{phone-id}")
    public ResponseEntity<Void> deletePhone(@PathVariable("phone-id") Long phoneId,
                                            Authentication connectedUser) {
        phoneService.deletePhone(phoneId, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{phone-id}")
    public ResponseEntity<Void> updatePhone(@PathVariable("phone-id") Long phoneId,
                                            @Valid @RequestBody UpdatePhoneRequest updatePhoneRequest,
                                            Authentication connectedUser) {
        phoneService.updatePhone(phoneId, updatePhoneRequest, connectedUser);
        return ResponseEntity.ok().build();
    }
}
