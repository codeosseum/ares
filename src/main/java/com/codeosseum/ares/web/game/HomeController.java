package com.codeosseum.ares.web.game;

import com.codeosseum.ares.security.util.AuthenticationService;
import com.codeosseum.ares.user.User;
import com.codeosseum.ares.web.Paths;
import com.codeosseum.ares.web.Views;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static final String USERNAME_ATTRIBUTE = "username";

    private final AuthenticationService authenticationService;

    public HomeController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping(Paths.Game.HOME)
    public String getHome(final Model model) {
        authenticationService.getAuthenticatedUser()
                .map(User::getUsername)
                .ifPresent(username -> model.addAttribute(USERNAME_ATTRIBUTE, username));

        return Views.Game.HOME;
    }
}
