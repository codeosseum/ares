package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import com.codeosseum.ares.servermanagement.Server;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AssignedMatch {
    private final MatchmakingResult matchmakingResult;

    private final Server assignedServer;

    private final LocalDateTime timestamp;
}
