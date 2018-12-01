package com.codeosseum.ares.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    private String id;

    private String username;

    private int matchesPlayed;

    private int matchesWon;

    private int rank;

    private int experience;
}
