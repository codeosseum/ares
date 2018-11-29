package com.codeosseum.ares.security.registration.controller;

import com.codeosseum.ares.config.web.Endpoints;
import com.codeosseum.ares.security.util.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private static final String REDIRECT_TO_GAME_HOME = "redirect:" + Endpoints.Paths.Game.HOME;

    private final AuthenticationService authenticationService;

    public RegistrationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping(Endpoints.Paths.REGISTRATION)
    public String getRegistrationView() {
        return authenticationService.hasAuthenticatedUser() ? REDIRECT_TO_GAME_HOME : Endpoints.Views.REGISTRATION;
    }

    @PostMapping(Endpoints.Paths.Api.REGISTRATION)
    @ResponseBody
    public ResponseEntity registerUser(@RequestBody @Valid final Registration registration, final BindingResult bindingResult) {
        return new ResponseEntity(HttpStatus.OK);
    }

    private static final class RegistrationFailureResponse {

    }
}
