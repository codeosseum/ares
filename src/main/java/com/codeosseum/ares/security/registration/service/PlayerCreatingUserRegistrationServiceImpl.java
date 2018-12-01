package com.codeosseum.ares.security.registration.service;

import com.codeosseum.ares.player.PlayerService;
import com.codeosseum.ares.user.User;

public class PlayerCreatingUserRegistrationServiceImpl implements UserRegistrationService {
    private final UserRegistrationService userRegistrationService;

    private final PlayerService playerService;

    public PlayerCreatingUserRegistrationServiceImpl(UserRegistrationService userRegistrationService, PlayerService playerService) {
        this.userRegistrationService = userRegistrationService;
        this.playerService = playerService;
    }

    @Override
    public User register(RegistrationDetails registrationDetails) throws RegistrationFailedException {
        try {
            playerService.createPlayer(registrationDetails.getUsername());

            return userRegistrationService.register(registrationDetails);
        } catch (RegistrationFailedException rfe) {
            throw rfe;
        } catch (Exception e) {
            throw new RegistrationFailedException(registrationDetails, e);
        }
    }
}
