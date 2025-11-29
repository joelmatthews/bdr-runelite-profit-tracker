package com.profittracker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profittracker.adapters.filesystem.FileSystemAdapter;
import com.profittracker.domain.BdrFileType;
import com.profittracker.domain.LeaguePlayerModel;
import com.profittracker.services.leagueservice.LeagueService;
import com.profittracker.services.leagueservice.LeagueServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LeagueServiceImplTest {

    private final LeagueService _leagueService;
    @SuppressWarnings("unchecked")
    private final FileSystemAdapter<BdrFileType> _fileSystemAdapter =
            (FileSystemAdapter<BdrFileType>) mock(FileSystemAdapter.class);
    private final ObjectMapper _objectMapper = mock(ObjectMapper.class);
    private final ObjectMapper _testMapper = new ObjectMapper();

    public LeagueServiceImplTest() {
        this._leagueService = new LeagueServiceImpl(_fileSystemAdapter, _objectMapper);
    }

    @Rule
    public TemporaryFolder stubFolder = new TemporaryFolder();

    @Test
    public void addLeaguePlayerId_ShouldAddALeaguePlayerIdAndReturnJsonWhenJsonFileAlreadyExists() throws IOException {
        // Arrange
        String idToAdd = "00000000-1111-2222-3333-444444444444";
        Date timestamp = new Date();

        Path stubFilePath = Paths.get("leaguedata.json");
        File stubJsonDir = stubFolder.newFolder(".runelite", "bdr");
        File stubJsonFile = new File(stubJsonDir, stubFilePath.toString());
        stubJsonFile.createNewFile();

        LeaguePlayerModel stubLeagueData = new LeaguePlayerModel();
        stubLeagueData.leaguePlayerIds.add("754de33e-6eb1-4ce5-a43c-b07b023738a1"); // add a uuid into stub json to simulate an id already being in there
        stubLeagueData.lastUpdated = timestamp;

        _testMapper.writeValue(stubJsonFile, stubLeagueData); // writes json into the test file
        when(_objectMapper.readValue(stubJsonFile, LeaguePlayerModel.class)).thenReturn(stubLeagueData); // injected mapper returns stub data that we just added to test file
        when(_fileSystemAdapter.getFilePath(stubFilePath.toString(), BdrFileType.LEAGUEDATA)).thenReturn(stubJsonFile.toPath());
        doNothing().when(_objectMapper).writeValue(stubJsonFile, LeaguePlayerModel.class);

        JsonNode testJsonNode = _testMapper.readTree(stubJsonFile);
        when(_objectMapper.readTree(any(File.class))).thenReturn(testJsonNode);

        String stubJsonString = _testMapper.writeValueAsString(stubLeagueData);
        when(_objectMapper.writeValueAsString(any(JsonNode.class))).thenReturn(stubJsonString);

        String expectedJsonStringResult = _testMapper.writeValueAsString(stubLeagueData);

        // Act
        String actualJsonStringResult = _leagueService.addLeaguePlayerId(idToAdd);

        // Assert
        assertEquals(expectedJsonStringResult, actualJsonStringResult);
    }

    @Test
    public void addLeaguePlayerId_ShouldCreateNewJsonFileAndAddLeaguePlayerIdIfNoExistingFileFoundAndShouldReturnJson() throws IOException {
        // Arrange
        String idToAdd = "00000000-1111-2222-3333-444444444444";
        Date timestamp = new Date();

        Path stubFilePath = Paths.get("leaguedata.json");
        File stubJsonDir = stubFolder.newFolder(".runelite", "bdr");
        File stubJsonFile = new File(stubJsonDir, stubFilePath.toString());
        stubJsonFile.createNewFile();

        LeaguePlayerModel stubLeagueData = new LeaguePlayerModel();
        stubLeagueData.leaguePlayerIds.add("754de33e-6eb1-4ce5-a43c-b07b023738a1"); // add a uuid into stub json to simulate an id already being in there
        stubLeagueData.lastUpdated = timestamp;

        _testMapper.writeValue(stubJsonFile, stubLeagueData); // writes json into the test file

        when(_fileSystemAdapter.getFilePath(stubFilePath.toString(), BdrFileType.LEAGUEDATA)).thenReturn(null);
        doNothing().when(_objectMapper).writeValue(stubJsonFile, LeaguePlayerModel.class);

        JsonNode testJsonNode = _testMapper.readTree(stubJsonFile);
        when(_objectMapper.readTree(any(File.class))).thenReturn(testJsonNode);

        String stubJsonString = _testMapper.writeValueAsString(stubLeagueData);
        when(_objectMapper.writeValueAsString(any(JsonNode.class))).thenReturn(stubJsonString);

        String expectedJsonStringResult = _testMapper.writeValueAsString(stubLeagueData);

        // Act
        String actualJsonStringResult = _leagueService.addLeaguePlayerId(idToAdd);

        // Assert
        assertEquals(expectedJsonStringResult, actualJsonStringResult);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addLeaguePlayerId_ShouldThrowWhenPlayerIdAlreadyExists() throws IOException {
        // Arrange
        String idToAdd = "754de33e-6eb1-4ce5-a43c-b07b023738a1";
        Date timestamp = new Date();

        Path stubFilePath = Paths.get("leaguedata.json");
        File stubJsonDir = stubFolder.newFolder(".runelite", "bdr", "leaguedata");
        File stubJsonFile = new File(stubJsonDir, stubFilePath.toString());
        stubJsonFile.createNewFile();

        LeaguePlayerModel stubLeagueData = new LeaguePlayerModel();
        stubLeagueData.leaguePlayerIds.add("754de33e-6eb1-4ce5-a43c-b07b023738a1"); // add a uuid into stub json to simulate an id already being in there
        stubLeagueData.lastUpdated = timestamp;

        _testMapper.writeValue(stubJsonFile, stubLeagueData); // writes json into the test file
        when(_objectMapper.readValue(stubJsonFile, LeaguePlayerModel.class)).thenReturn(stubLeagueData); // injected mapper returns stub data that we just added to test file
        when(_fileSystemAdapter.getFilePath(stubFilePath.toString(), BdrFileType.LEAGUEDATA)).thenReturn(stubJsonFile.toPath());
        doNothing().when(_objectMapper).writeValue(stubJsonFile, LeaguePlayerModel.class);

        JsonNode testJsonNode = _testMapper.readTree(stubJsonFile);
        when(_objectMapper.readTree(any(File.class))).thenReturn(testJsonNode);

        String stubJsonString = _testMapper.writeValueAsString(stubLeagueData);
        when(_objectMapper.writeValueAsString(any(JsonNode.class))).thenReturn(stubJsonString);

        String expectedExceptionMessage = "League player id " + idToAdd + " already exists in leagueData";

        // Act && Assert
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(expectedExceptionMessage);

        _leagueService.addLeaguePlayerId(idToAdd);
    }

//
//    @Test
//    public void getLeaguePlayerIds_ShouldReturnListOfAllLeaguePlayerIds() throws IOException {
//
//    }
}