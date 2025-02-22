package dev.anilp.ecommerce.auth;

import dev.anilp.ecommerce.email.EmailService;
import dev.anilp.ecommerce.exception.DuplicateResourceException;
import dev.anilp.ecommerce.exception.ExpiredTokenException;
import dev.anilp.ecommerce.exception.ResourceNotFoundException;
import dev.anilp.ecommerce.role.RoleRepository;
import dev.anilp.ecommerce.security.JwtService;
import dev.anilp.ecommerce.token.Token;
import dev.anilp.ecommerce.token.TokenRepository;
import dev.anilp.ecommerce.user.User;
import dev.anilp.ecommerce.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

import static dev.anilp.ecommerce.email.EmailTemplateName.ACTIVATE_ACCOUNT;
import static dev.anilp.ecommerce.exception.ExceptionMessage.DUPLICATE_RESOURCE;
import static dev.anilp.ecommerce.exception.ExceptionMessage.EXPIRED;
import static dev.anilp.ecommerce.exception.ExceptionMessage.INVALID_TOKEN;
import static dev.anilp.ecommerce.exception.ExceptionMessage.ROLE_NOT_FOUND;
import static dev.anilp.ecommerce.exception.ExceptionMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Transactional
    public void register(RegistrationRequest request) throws MessagingException {
        String roleName = "USER";
        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException(ROLE_NOT_FOUND.getMessage()));

        userRepository.findByEmail(request.email())
                .ifPresent(user -> {
                    throw new DuplicateResourceException(DUPLICATE_RESOURCE.getMessage());
                });

        var user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .gender(request.gender())
                .dateOfBirth(request.dateOfBirth())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountLocked(false)
                .enabled(false)
                .build();
        user.addRole(role);
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode();
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = (User) auth.getPrincipal();
        claims.put("fullName", user.getFullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException(INVALID_TOKEN.getMessage()));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new ExpiredTokenException(EXPIRED.getMessage());
        }

        User user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.getMessage()));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
