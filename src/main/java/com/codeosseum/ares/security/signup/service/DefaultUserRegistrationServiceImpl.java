package com.codeosseum.ares.security.signup.service;

import com.codeosseum.ares.user.Role;
import com.codeosseum.ares.user.User;
import com.codeosseum.ares.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Objects;

public class DefaultUserRegistrationServiceImpl implements UserRegistrationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DefaultUserRegistrationServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(final RegistrationDetails registrationDetails) throws RegistrationFailedException {
        final User user = convertRegistrationDetailsToUser(Objects.requireNonNull(registrationDetails));

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RegistrationFailedException(registrationDetails, e);
        }
    }

    private User convertRegistrationDetailsToUser(final RegistrationDetails registrationDetails) {
        return User.builder()
                .email(registrationDetails.getEmail())
                .password(passwordEncoder.encode(registrationDetails.getPassword()))
                .username(registrationDetails.getUsername())
                .roles(Collections.singleton(Role.USER))
                .build();
    }
}
