package com.codeosseum.ares.match.repository;

import com.codeosseum.ares.match.Match;

import java.util.List;

public interface FindMatchIncludingPlayerRepository {
    List<Match> findIncludingPlayerByUsername(String username);
}
