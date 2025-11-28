package com.profittracker.services.leagueservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profittracker.adapters.filesystem.FileSystemAdapter;
import com.profittracker.domain.LeaguePlayerModel;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.runelite.client.RuneLite.RUNELITE_DIR;

public class LeagueServiceImpl implements LeagueService {

    FileSystemAdapter _fileSystemAdapter;

    @Inject
    public LeagueServiceImpl(FileSystemAdapter fileSystemAdapter) {
        this._fileSystemAdapter = fileSystemAdapter;
    }

    @Override
    public boolean addLeaguePlayerId(String id) throws IOException {
        String directory = RUNELITE_DIR + "/bdr/" + "leaguedata";

        try {
            Path existingFilePath = _fileSystemAdapter.getFilePath("leaguedata.json", directory);
            ObjectMapper mapper = new ObjectMapper();

            LeaguePlayerModel leagueData;
            Path filePath;

            if (existingFilePath != null) {
                leagueData = mapper.readValue(existingFilePath.toFile(), LeaguePlayerModel.class);

                // Check if ID already exists
                if (leagueData.leaguePlayerIds.contains(id)) {
                    return false; // ID already exists
                }

                leagueData.leaguePlayerIds.add(id);
                filePath = existingFilePath;
            } else {
                leagueData = new LeaguePlayerModel();
                leagueData.leaguePlayerIds.add(id);

                // Create directory if it doesn't exist
                Path dirPath = Paths.get(directory);
                if (!Files.exists(dirPath)) {
                    Files.createDirectories(dirPath);
                }

                filePath = Paths.get(directory, "leaguedata.json");
            }

            leagueData.lastUpdated = new Date();
            mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), leagueData);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> getLeaguePlayerIds() throws IOException {
        String directory = RUNELITE_DIR + "/bdr/" + "leaguedata";

        try {
            Path existingFilePath = _fileSystemAdapter.getFilePath("leaguedata.json", directory);

            if (existingFilePath == null) {
                return new ArrayList<>(); // Return empty list if file doesn't exist
            }

            ObjectMapper mapper = new ObjectMapper();
            LeaguePlayerModel leagueData = mapper.readValue(existingFilePath.toFile(), LeaguePlayerModel.class);

            return leagueData.leaguePlayerIds;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
