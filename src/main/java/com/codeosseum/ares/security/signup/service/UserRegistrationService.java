package com.codeosseum.ares.security.signup.service;

import com.codeosseum.ares.user.User;

public interface UserRegistrationService {
    User register(RegistrationDetails registrationDetails) throws RegistrationFailedException;
}
