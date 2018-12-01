package com.codeosseum.ares.match.repository;

import com.codeosseum.ares.match.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, String>, FindMatchIncludingPlayerRepository {

}
