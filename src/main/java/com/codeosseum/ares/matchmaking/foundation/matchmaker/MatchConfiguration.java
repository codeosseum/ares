package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import com.codeosseum.ares.match.Mode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MatchConfiguration {
    private Mode mode;

    private List<String> players;
}
