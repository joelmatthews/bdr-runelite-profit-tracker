package com.profittracker.services.leagueservice;

import com.profittracker.domain.LeaguePlayerModel;

import java.io.IOException;
import java.util.List;

public interface LeagueService {
    public String addLeaguePlayerId(String id) throws IOException, IllegalArgumentException; // adds a league player id to the json file and returns JSON string if successful
    public LeaguePlayerModel getLeaguePlayer() throws IOException; // returns a complete list of every league player id in the json file
    public void validateLeaguePlayerId(String id) throws IllegalArgumentException;
}
