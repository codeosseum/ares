package com.codeosseum.ares.match.faultseeding.controller;

import com.codeosseum.ares.web.Paths;
import com.codeosseum.ares.web.Views;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaultSeedingController {

    @GetMapping(Paths.Game.FAULT_SEEDING)
    public String getFaultSeeding() {
        return Views.Game.FAULT_SEEDING;
    }

}
