package com.codeosseum.ares.security.signup.config;

import com.codeosseum.ares.player.PlayerService;
import com.codeosseum.ares.security.signup.service.DefaultUserRegistrationServiceImpl;
import com.codeosseum.ares.security.signup.service.PlayerCreatingUserRegistrationServiceImpl;
import com.codeosseum.ares.security.signup.service.UserRegistrationService;
import com.codeosseum.ares.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfig {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final PlayerService playerService;

    public ServiceConfig(UserRepository userRepository, PasswordEncoder passwordEncoder, PlayerService playerService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.playerService = playerService;
    }

    @Bean
    public UserRegistrationService userRegistrationService() {
        return new PlayerCreatingUserRegistrationServiceImpl(wrappedUserRegistrationService(), playerService);
    }

    private UserRegistrationService wrappedUserRegistrationService() {
        return new DefaultUserRegistrationServiceImpl(userRepository, passwordEncoder);
    }
}
