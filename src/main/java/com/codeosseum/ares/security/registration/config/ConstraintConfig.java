package com.codeosseum.ares.security.registration.config;

import com.codeosseum.ares.security.registration.constraint.UniqueEmailValidator;
import com.codeosseum.ares.security.registration.constraint.UniqueUsernameValidator;
import com.codeosseum.ares.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstraintConfig {
    private final UserRepository userRepository;

    public ConstraintConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UniqueEmailValidator uniqueEmailValidator() {
        return new UniqueEmailValidator(userRepository);
    }

    @Bean
    public UniqueUsernameValidator uniqueUsernameValidator() {
        return new UniqueUsernameValidator(userRepository);
    }
}
