package com.codeosseum.ares.match.repository;

import com.codeosseum.ares.match.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatchRepositoryImpl implements FindMatchIncludingPlayerRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Match> findIncludingPlayerByUsername(final String username) {
        Query selectQuery = new Query(Criteria.where("players").is(username));

        return mongoTemplate.find(selectQuery, Match.class);
    }
}
