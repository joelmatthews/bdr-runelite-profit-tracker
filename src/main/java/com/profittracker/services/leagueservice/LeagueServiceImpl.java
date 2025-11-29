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
            Path existingFilePath = _fileSystemAdapter.getFilePath(leagueDataFilePath.toString(), BdrFileType.LEAGUEDATA);

            LeaguePlayerModel leagueData;

            if (existingFilePath != null) {
                leagueData = _mapper.readValue(existingFilePath.toFile(), LeaguePlayerModel.class);

                if (leagueData.leaguePlayerIds.contains(id)) {
                    throw new IllegalArgumentException("League player id " + id + " already exists in leagueData");
                }

                leagueData.leaguePlayerIds.add(id);
            } else {
                leagueData = new LeaguePlayerModel();
                leagueData.leaguePlayerIds.add(id);
            }

            leagueData.lastUpdated = new Date();
            Path resolvedPath = BdrFileType.LEAGUEDATA.getPath().resolve(leagueDataFilePath);
            _mapper.writeValue(resolvedPath.toFile(), leagueData);

            // confirm JSON was written to the file and return the JSON
            JsonNode json = _mapper.readTree(resolvedPath.toFile());
            String jsonString = _mapper.writeValueAsString(json);

            return jsonString;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<String> getLeaguePlayerIds() throws IOException {
        try {
            Path existingFilePath = _fileSystemAdapter.getFilePath("leagedata.json", BdrFileType.LEAGUEDATA);

            LeaguePlayerModel leagueData;

            if (existingFilePath != null) {
                leagueData = _mapper.readValue(existingFilePath.toFile(), LeaguePlayerModel.class);
                return leagueData.leaguePlayerIds;
            }

            return null;
        } catch (Exception e) {
            System.out.print("LeagueService error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void validateLeaguePlayerId(String id) throws IllegalArgumentException {
        if (id == null)
        {
            throw new IllegalArgumentException("Please enter a valid ID");
        }

        try {
            UUID.fromString(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid format. Good Example: 68a0ae63-32a2-4e89-997b-4b26d5950112");
        }
    }
}
