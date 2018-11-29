package com.codeosseum.ares.security.registration.service;

import com.codeosseum.ares.user.Role;
import com.codeosseum.ares.user.User;
import com.codeosseum.ares.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
public class DefaultRegistrationServiceImpl implements RegistrationService {
    private final String FAILURE_MESSAGE = "Failed to register user";

    private final UserRepository userRepository;

    public DefaultRegistrationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
                .password(registrationDetails.getPassword())
                .username(registrationDetails.getUsername())
                .roles(Collections.singleton(Role.USER))
                .build();
    }
}
