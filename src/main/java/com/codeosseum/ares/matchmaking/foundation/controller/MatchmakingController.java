package com.codeosseum.ares.matchmaking.controller;

import com.codeosseum.ares.web.Paths;
import com.codeosseum.ares.web.Views;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class MatchmakingController {
    @GetMapping(Paths.Game.MATCHMAKING)
    public String getMatchmakingPage() {
        return Views.Game.MATCHMAKING;
    }

    @GetMapping(Paths.Api.MATCHMAKING)
    public ResponseEntity checkForMatch() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(Paths.Api.MATCHMAKING)
    public ResponseEntity addToMatchmaking() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(Paths.Api.MATCHMAKING)
    public ResponseEntity removeFromMatchmaking() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
