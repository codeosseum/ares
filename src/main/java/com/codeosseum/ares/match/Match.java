package com.codeosseum.ares.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.net.URI;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    private String id;

    private List<String> players;

    private Mode mode;

    private Status status;

    private URI serverUri;

    private List<MatchEvent> events;
}
