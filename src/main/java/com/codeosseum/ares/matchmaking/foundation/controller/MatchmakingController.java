package com.codeosseum.ares.matchmaking.foundation.controller;

import com.codeosseum.ares.matchmaking.foundation.MatchmakingFacade;
import com.codeosseum.ares.security.util.AuthenticationService;
import com.codeosseum.ares.user.User;
import com.codeosseum.ares.web.Paths;
import com.codeosseum.ares.web.Views;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Controller
public class MatchmakingController {
    private final AuthenticationService authenticationService;

    private final MatchmakingFacade matchmakingFacade;

    public MatchmakingController(AuthenticationService authenticationService, MatchmakingFacade matchmakingFacade) {
        this.authenticationService = authenticationService;
        this.matchmakingFacade = matchmakingFacade;
    }

    @GetMapping(Paths.Game.MATCHMAKING)
    public String getMatchmakingPage() {
        return Views.Game.MATCHMAKING;
    }

    @GetMapping(Paths.Api.MATCHMAKING)
    public ResponseEntity checkForMatch() {
        if (!authenticationService.hasAuthenticatedUser()) {
            return new ResponseEntity(UNAUTHORIZED);
        }

        return authenticationService.getAuthenticatedUser()
                .map(User::getUsername)
                .flatMap(matchmakingFacade::getMatchForPlayer)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(NO_CONTENT));
    }

    @DeleteMapping(Paths.Api.MATCHMAKING)
    public ResponseEntity removeFromMatchmaking() {
        if (!authenticationService.hasAuthenticatedUser()) {
            return new ResponseEntity(UNAUTHORIZED);
        }

        authenticationService.getAuthenticatedUser()
                .map(User::getUsername)
                .ifPresent(matchmakingFacade::removePlayerFromMatchmaking);

        return new ResponseEntity(HttpStatus.OK);
    }
}
