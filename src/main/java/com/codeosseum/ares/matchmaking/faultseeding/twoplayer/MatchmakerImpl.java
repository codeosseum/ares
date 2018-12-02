package com.codeosseum.ares.matchmaking.faultseeding.twoplayer;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.EligoMatchmaker;
import com.codeosseum.eligo.classifier.Classifier;
import com.codeosseum.eligo.classifier.Classifiers;
import com.codeosseum.eligo.matchmaker.Matchmaker;
import com.codeosseum.eligo.matchmaker.Matchmakers;
import com.codeosseum.eligo.matchmaker.decisiontree.MatchFunction;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeosseum.eligo.matchmaker.decisiontree.BucketMatcher.hasAtLeast;

public class MatchmakerImpl extends EligoMatchmaker<MatchmakingProfile, MatchConfiguration> {
    private static final int PLAYER_COUNT = 2;

    private static Matchmaker<MatchmakingProfile, MatchConfiguration> eligoMatchmakerFactory() {
        final MatchFunction<MatchmakingProfile, MatchConfiguration> matchFunction =
                MatchFunction.<MatchmakingProfile, MatchConfiguration>builder()
                    .predicate(hasAtLeast(2))
                    .supplier(picker -> {
                        final List<String> usernames = picker.pickMany(PLAYER_COUNT).stream()
                                .map(MatchmakingProfile::getUsername)
                                .collect(Collectors.toList());

                        return new MatchConfiguration(usernames);
                    })
                    .build();

        final Classifier<MatchmakingProfile> classifier = Classifiers.openInterval(Collections.emptyList(), MatchmakingProfile::getRank);

        return Matchmakers.<MatchmakingProfile, MatchConfiguration>decisionTree()
                .matchFunction(matchFunction)
                .classifier(classifier)
                .build();
    }

    public MatchmakerImpl() {
        super(eligoMatchmakerFactory());
    }
}
