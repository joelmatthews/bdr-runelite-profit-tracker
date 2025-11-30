package com.profittracker.services.leagueservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profittracker.adapters.filesystem.FileSystemAdapter;
import com.profittracker.domain.BdrFileType;
import com.profittracker.domain.LeaguePlayerModel;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class LeagueServiceImpl implements LeagueService {


//    private static final String LEAGUE_PLAYER_ID_REGEX =
//            "^[0-9a-fA-F]{8}-" +
//                    "[0-9a-fA-F]{4}-" +
//                    "[0-9a-fA-F]{4}-" +
//                    "[0-9a-fA-F]{4}-" +
//                    "[0-9a-fA-F]{12}$";
//
//    private static final Pattern LEAGUE_ID_PATTERN =
//            Pattern.compile(LEAGUE_PLAYER_ID_REGEX);

    private final FileSystemAdapter _fileSystemAdapter;
    private final ObjectMapper _mapper;

    public LeagueServiceImpl(FileSystemAdapter fileSystemAdapter, ObjectMapper mapper) {
        this._fileSystemAdapter = fileSystemAdapter;
        this._mapper = mapper;
    }

    @Override
    public String addLeaguePlayerId(String id) throws IOException, IllegalArgumentException {
        try {
            validateLeaguePlayerId(id);

            Path leagueDataFilePath = Paths.get("leaguedata.json");
            Path leagueDataDirPath = _fileSystemAdapter.getFileTypePath(BdrFileType.LEAGUEDATA);

            Path existingFilePath = _fileSystemAdapter.getFilePath(leagueDataFilePath.toString(), leagueDataDirPath);

            // if exstingFilePath is null, the file does not exist
            if (existingFilePath == null) {
                // if the file does not exist, create an empty leaguedata.json file
                File newFile = leagueDataDirPath.resolve(leagueDataFilePath).toFile();
                LeaguePlayerModel leagueData = new LeaguePlayerModel();
                leagueData.lastUpdated = new Date();
                leagueData.leaguePlayerIds.add(id);
                _mapper.writeValue(newFile, leagueData);
                JsonNode json = _mapper.readTree(newFile);
                String jsonString = _mapper.writeValueAsString(json);
                return jsonString;
            } else if(existingFilePath != null) {
                LeaguePlayerModel leagueData = _mapper.readValue(existingFilePath.toFile(), LeaguePlayerModel.class);

                if (leagueData.leaguePlayerIds.contains(id)) {
                    throw new IllegalArgumentException("League player id " + id + " already exists in leagueData");
                }

                leagueData.lastUpdated = new Date();
                leagueData.leaguePlayerIds.add(id);
                _mapper.writeValue(existingFilePath.toFile(), leagueData);
                JsonNode json = _mapper.readTree(existingFilePath.toFile());
                String jsonString = _mapper.writeValueAsString(json);
                return jsonString;
            }

            return null;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw e;
        }
    }

    @Override
    public LeaguePlayerModel getLeaguePlayer() throws IOException {
        try {
            Path leagueDataFilePath = Paths.get("leaguedata.json");
            Path leagueDataDirPath = _fileSystemAdapter.getFileTypePath(BdrFileType.LEAGUEDATA);
            Path existingFilePath = _fileSystemAdapter.getFilePath(leagueDataFilePath.toString(), leagueDataDirPath);

            LeaguePlayerModel leagueData;

            if (existingFilePath != null) {
                leagueData = _mapper.readValue(existingFilePath.toFile(), LeaguePlayerModel.class);
                return leagueData;
            }

            return null;
        } catch (Exception e) {
            System.out.print("LeagueService error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void validateLeaguePlayerId(String id) throws IllegalArgumentException {
        if (id == null)
        {
            throw new IllegalArgumentException("ID was Null. Please enter a valid ID");
        }

        try {
            UUID.fromString(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid format. Good Example: 68a0ae63-32a2-4e89-997b-4b26d5950112");
        }
    }
}
