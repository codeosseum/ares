package com.codeosseum.ares.matchmaking.faultseeding.twoplayer;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.EligoMatchmaker;
import com.codeosseum.eligo.classifier.Classifier;
import com.codeosseum.eligo.classifier.Classifiers;
import com.codeosseum.eligo.matchmaker.Matchmaker;
import com.codeosseum.eligo.matchmaker.Matchmakers;
import com.codeosseum.eligo.matchmaker.decisiontree.MatchFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeosseum.eligo.matchmaker.decisiontree.BucketMatcher.hasAtLeast;

public class TwoPlayerFaultSeedingMatchmakerImpl extends EligoMatchmaker<TwoPlayerFaultSeedingMatchmakingProfile, TwoPlayerFaultSeedingMatchConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwoPlayerFaultSeedingMatchmakerImpl.class);

    private static final int PLAYER_COUNT = 2;

    private static Matchmaker<TwoPlayerFaultSeedingMatchmakingProfile, TwoPlayerFaultSeedingMatchConfiguration> eligoMatchmakerFactory() {
        final MatchFunction<TwoPlayerFaultSeedingMatchmakingProfile, TwoPlayerFaultSeedingMatchConfiguration> matchFunction =
                MatchFunction.<TwoPlayerFaultSeedingMatchmakingProfile, TwoPlayerFaultSeedingMatchConfiguration>builder()
                    .predicate(hasAtLeast(2))
                    .supplier(picker -> {
                        final List<String> usernames = picker.pickMany(PLAYER_COUNT).stream()
                                .map(TwoPlayerFaultSeedingMatchmakingProfile::getUsername)
                                .collect(Collectors.toList());

                        final TwoPlayerFaultSeedingMatchConfiguration configuration = new TwoPlayerFaultSeedingMatchConfiguration(usernames);

                        LOGGER.debug("Created a new Two Player Fault Seeding configuration: {}", configuration);

                        return configuration;
                    })
                    .build();

        final Classifier<TwoPlayerFaultSeedingMatchmakingProfile> classifier = Classifiers.openInterval(Collections.emptyList(), TwoPlayerFaultSeedingMatchmakingProfile::getRank);

        return Matchmakers.<TwoPlayerFaultSeedingMatchmakingProfile, TwoPlayerFaultSeedingMatchConfiguration>decisionTree()
                .matchFunction(matchFunction)
                .classifier(classifier)
                .build();
    }

    public TwoPlayerFaultSeedingMatchmakerImpl() {
        super(eligoMatchmakerFactory());
    }
}
