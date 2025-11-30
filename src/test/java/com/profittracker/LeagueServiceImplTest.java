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
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LeagueServiceImplTest {

    private final LeagueService _leagueService;
    @SuppressWarnings("unchecked")
    private final FileSystemAdapter _fileSystemAdapter =
            (FileSystemAdapter) mock(FileSystemAdapter.class);
    private final ObjectMapper _objectMapper = mock(ObjectMapper.class);
    private final ObjectMapper _testMapper = new ObjectMapper();

    public LeagueServiceImplTest() {
        this._leagueService = new LeagueServiceImpl(_fileSystemAdapter, _testMapper);
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
        when(_fileSystemAdapter.getFileTypePath(BdrFileType.LEAGUEDATA)).thenReturn(stubJsonDir.toPath());
        when(_fileSystemAdapter.getFilePath(stubFilePath.toString(), stubJsonDir.toPath())).thenReturn(stubJsonFile.toPath());


        JsonNode testJsonNode = _testMapper.readTree(stubJsonFile);

        String stubJsonString = _testMapper.writeValueAsString(stubLeagueData);

        stubLeagueData.leaguePlayerIds.add(idToAdd);
        String expectedJsonStringResult = _testMapper.writeValueAsString(stubLeagueData);

        // Act
        String actualJsonStringResult = _leagueService.addLeaguePlayerId(idToAdd);

        // Assert
        assertEquals(_testMapper.readValue(expectedJsonStringResult, LeaguePlayerModel.class).leaguePlayerIds, _testMapper.readValue(actualJsonStringResult, LeaguePlayerModel.class).leaguePlayerIds);
        System.out.print(actualJsonStringResult);
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
        stubLeagueData.leaguePlayerIds.add(idToAdd);
        stubLeagueData.lastUpdated = timestamp;

        when (_fileSystemAdapter.getFileTypePath(BdrFileType.LEAGUEDATA)).thenReturn(stubJsonDir.toPath());
        when(_fileSystemAdapter.getFilePath(stubFilePath.toString(), stubJsonDir.toPath())).thenReturn(null); // forces us down the create new file path

        String expectedJsonStringResult = _testMapper.writeValueAsString(stubLeagueData);

        // Act
        String actualJsonStringResult = _leagueService.addLeaguePlayerId(idToAdd);

        // Assert
        assertEquals(stubLeagueData.leaguePlayerIds.get(0), _testMapper.readValue(actualJsonStringResult, LeaguePlayerModel.class).leaguePlayerIds.get(0));
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
        Path leagueDataDirPath = _fileSystemAdapter.getFileTypePath(BdrFileType.LEAGUEDATA);
        when(_fileSystemAdapter.getFilePath(stubFilePath.toString(), leagueDataDirPath)).thenReturn(stubJsonFile.toPath());
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
    @Test
    public void getLeaguePlayer_ShouldReturnLeaguePlayer() throws IOException {
        // Arrange
        Path stubFilePath = Paths.get("leaguedata.json");
        File stubJsonDir = stubFolder.newFolder(".runelite", "bdr");
        File stubJsonFile = new File(stubJsonDir, stubFilePath.toString());
        stubJsonFile.createNewFile();

        Date timestamp = new Date();
        LeaguePlayerModel stubLeagueData = new LeaguePlayerModel();

        for (int i = 0; i <= 5; i++) {
            stubLeagueData.leaguePlayerIds.add(UUID.randomUUID().toString()); // add a uuid into stub json to simulate an id already being in there
        }
        stubLeagueData.lastUpdated = timestamp;

        _testMapper.writeValue(stubJsonFile, stubLeagueData); // writes json into the test file
        Path leagueDataDirPath = _fileSystemAdapter.getFileTypePath(BdrFileType.LEAGUEDATA);
        when(_fileSystemAdapter.getFilePath(stubFilePath.toString(), leagueDataDirPath)).thenReturn(stubJsonFile.toPath());
        when(_objectMapper.readValue(stubJsonFile, LeaguePlayerModel.class)).thenReturn(stubLeagueData); // injected mapper returns stub data that we just added to test file

        LeaguePlayerModel expectedResult = stubLeagueData;

        // Act
        LeaguePlayerModel actualResult = _leagueService.getLeaguePlayer();

        // Assert
        assertEquals(expectedResult.leaguePlayerIds, actualResult.leaguePlayerIds);
    }

    @Test
    public void getLeaguePlayer_ShouldReturnNullIfNoExistingFile() throws IOException {
        // Arrange
        Path stubFilePath = Paths.get("leaguedata.json");
        Path leagueDataDirPath = _fileSystemAdapter.getFileTypePath(BdrFileType.LEAGUEDATA);
        when(_fileSystemAdapter.getFilePath(stubFilePath.toString(), leagueDataDirPath)).thenReturn(null);

        LeaguePlayerModel expectedResult = null;

        // Act
        LeaguePlayerModel actualResult = _leagueService.getLeaguePlayer();

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void validateLeaguePlayerId_ShouldThrowExceptionWhenPlayerIdIsNull() {
        // Arrange
        String id = null;
        String expectedExceptionMessage = "ID was Null. Please enter a valid ID";

        // Act & Assert
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(expectedExceptionMessage);

        _leagueService.validateLeaguePlayerId(id);
    }

    @Test
    public void validateLeaguePlayerId_ShouldThrowExceptionWhenPlayerIdIsInvalidFormat() {
        // Arrange
        String id = "Zezima is an inside man and a psyop. He is not who he says he is.";
        String expectedExceptionMessage = "Invalid format. Good Example: 68a0ae63-32a2-4e89-997b-4b26d5950112";

        // Act & Assert
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(expectedExceptionMessage);

        _leagueService.validateLeaguePlayerId(id);
    }

    @Test
    public void validateLeaguePlayerId_ShouldValidate() {
        // Arrange
        String id = UUID.randomUUID().toString();

        // Act
        _leagueService.validateLeaguePlayerId(id); // passes if no exception thrown
    }


}