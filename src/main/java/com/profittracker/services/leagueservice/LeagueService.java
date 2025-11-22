package com.profittracker.services.leagueservice;

import java.io.IOException;
import java.util.List;

public interface LeagueService {
    public boolean createJsonFile() throws IOException; // creates an empty json file if one does not exist and returns true if successful
    public boolean addLeaguePlayerId() throws IOException; // adds a league player id to the json file and returns true if successful
    public List<String> getLeaguePlayerIds() throws IOException; // returns a complete list of every league player id in the json file
}
