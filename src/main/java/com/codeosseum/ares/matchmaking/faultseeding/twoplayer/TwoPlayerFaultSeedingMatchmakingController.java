package com.codeosseum.ares.matchmaking.faultseeding.twoplayer;

import com.codeosseum.ares.security.util.AuthenticationService;
import com.codeosseum.ares.user.User;
import com.codeosseum.ares.web.Paths;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Controller
public class TwoPlayerFaultSeedingMatchmakingController {
    private static final String MODE_PATH = "/two-player-fault-seeding";

    private final AuthenticationService authenticationService;

    private final TwoPlayerFaultSeedingMatchmakingService matchmakingService;

    public TwoPlayerFaultSeedingMatchmakingController(AuthenticationService authenticationService, TwoPlayerFaultSeedingMatchmakingService matchmakingService) {
        this.authenticationService = authenticationService;
        this.matchmakingService = matchmakingService;
    }

    @PostMapping(Paths.Api.MATCHMAKING + MODE_PATH)
    public ResponseEntity initiateMatchmaking() {
        if (!authenticationService.hasAuthenticatedUser()) {
            return new ResponseEntity(UNAUTHORIZED);
        }

        authenticationService.getAuthenticatedUser()
                .map(User::getUsername)
                .map(TwoPlayerFaultSeedingMatchmakingRequest::new)
                .ifPresent(matchmakingService::addToMatchmaking);

        return new ResponseEntity(NO_CONTENT);
    }
}
