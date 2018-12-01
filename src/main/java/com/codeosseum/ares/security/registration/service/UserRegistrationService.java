package com.codeosseum.ares.security.registration.service;

import com.codeosseum.ares.user.User;

public interface UserRegistrationService {
    User register(RegistrationDetails registrationDetails) throws RegistrationFailedException;
}
