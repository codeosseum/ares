package com.codeosseum.ares.security.registration.service;

import com.codeosseum.ares.security.registration.controller.Registration;
import com.codeosseum.ares.user.User;

public interface RegistrationService {
    User register(RegistrationDetails registrationDetails) throws RegistrationFailedException;
}
