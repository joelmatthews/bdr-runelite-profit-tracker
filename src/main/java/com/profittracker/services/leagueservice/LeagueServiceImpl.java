package com.profittracker.services.leagueservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profittracker.adapters.filesystem.FileSystemAdapter;
import com.profittracker.domain.LeaguePlayerModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static net.runelite.client.RuneLite.RUNELITE_DIR;

public class LeagueServiceImpl implements LeagueService {

    FileSystemAdapter _fileSystemAdapter;

    public LeagueServiceImpl(FileSystemAdapter fileSystemAdapter) {
        this._fileSystemAdapter = fileSystemAdapter;
    }

    @Override
    public boolean addLeaguePlayerId(String id) throws IOException {
        // if a file exists, open it and map the json in it to a LeaguePlayerModel
        String directory = RUNELITE_DIR + "/bdr/" + "leaguedata";

        try {
            Path existingFilePath = _fileSystemAdapter.getFilePath("leagedata.json", directory);
            ObjectMapper mapper = new ObjectMapper();

            LeaguePlayerModel leagueData;

            if (existingFilePath != null) {
                leagueData = mapper.readValue(existingFilePath.toFile(), LeaguePlayerModel.class);
                leagueData.leaguePlayerIds.add(id);
            } else {
                leagueData = new LeaguePlayerModel();
                leagueData.leaguePlayerIds.add(id);
            }

            leagueData.lastUpdated = new Date();
            mapper.writeValue(existingFilePath.toFile(), leagueData);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
