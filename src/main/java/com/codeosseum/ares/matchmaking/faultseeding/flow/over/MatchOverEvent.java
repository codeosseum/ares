package com.codeosseum.ares.matchmaking.faultseeding.flow.over;

import java.util.List;

import lombok.Value;

@Value
public class MatchOverEvent {
    public static final String IDENTIFIER = "fault-seeding-match-over";

    private final String matchId;

    private final String serverId;

    private final FinalScore finalScore;

    @Value
    public static final class FinalScore {
        private final List<String> winners;

        private final List<Position> ranking;

        private final Result result;

        public enum Result {
            WIN, DRAW
        }
    }

    @Value
    public static final class Position {
        private final List<String> players;

        private final int score;
    }
}
