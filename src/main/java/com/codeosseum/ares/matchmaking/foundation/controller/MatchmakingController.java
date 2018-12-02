package com.codeosseum.ares.matchmaking.foundation.controller;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchmakerFacade;
import com.codeosseum.ares.security.util.AuthenticationService;
import com.codeosseum.ares.web.Paths;
import com.codeosseum.ares.web.Views;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MatchmakingController {
    private final AuthenticationService authenticationService;

    private final MatchmakerFacade matchmakerFacade;

    public MatchmakingController(AuthenticationService authenticationService, MatchmakerFacade matchmakerFacade) {
        this.authenticationService = authenticationService;
        this.matchmakerFacade = matchmakerFacade;
    }

    @GetMapping(Paths.Game.MATCHMAKING)
    public String getMatchmakingPage() {
        return Views.Game.MATCHMAKING;
    }

    @GetMapping(Paths.Api.MATCHMAKING)
    public ResponseEntity checkForMatch() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(Paths.Api.MATCHMAKING)
    public ResponseEntity removeFromMatchmaking() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
