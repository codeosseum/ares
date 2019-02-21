package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.matchmaking.foundation.MatchmakingFacade;
import com.codeosseum.ares.matchmaking.foundation.persistence.MatchPersistedEvent;
import org.springframework.stereotype.Component;

@Component
public class RemovePlayersFromMatchmakingListener implements EventConsumer<MatchPersistedEvent> {
    private final MatchmakingFacade matchmakingFacade;

    public RemovePlayersFromMatchmakingListener(final MatchmakingFacade matchmakingFacade) {
        this.matchmakingFacade = matchmakingFacade;
    }

    @Override
    public void accept(final MatchPersistedEvent event) {
        event.getMatch().getMatchConfiguration().getPlayers()
                .forEach(matchmakingFacade::removePlayerFromMatchmaking);
    }
}
