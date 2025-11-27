package com.profittracker;

import com.profittracker.adapters.filesystem.FileSystemAdapter;
import com.profittracker.adapters.http.HttpAdapter;
import com.profittracker.services.leagueservice.LeagueService;
import com.profittracker.services.screenshotservice.ScreenshotService;
import com.profittracker.services.screenshotservice.ScreenshotServiceImpl;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.WorldType;
import net.runelite.client.util.ImageCapture;
import org.junit.Test;

import java.util.EnumSet;

import static net.runelite.client.RuneLite.SCREENSHOT_DIR;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ScreenshotServiceImplTest {

    ScreenshotService _screenshotService;
    ImageCapture _imageCapture = mock(ImageCapture.class);
    Client _client = mock(Client.class);
    HttpAdapter _httpAdapter = mock(HttpAdapter.class);
    FileSystemAdapter _fileSystemAdapter = mock(FileSystemAdapter.class);
    LeagueService _leagueService = mock(LeagueService.class);

    public ScreenshotServiceImplTest() {
        _screenshotService = new ScreenshotServiceImpl(_imageCapture, _client, _httpAdapter, _fileSystemAdapter, _leagueService);
    }


    @Test
    public void getScreenshotDirectory_ShouldReturnADirectoryPathString() {
        // Arrange
        Player stubbedPlayer = mock(Player.class);
        String playerName = "ninja12366";
        String expectedPlayerDir = playerName + "/" + "bdr/";
        String expectedDirectory = SCREENSHOT_DIR + "/" + expectedPlayerDir;
        when (stubbedPlayer.getName()).thenReturn(playerName);
        when(_client.getLocalPlayer()).thenReturn(stubbedPlayer);
        when(_client.getWorldType()).thenReturn(EnumSet.noneOf(WorldType.class));

        // Act
        String actualDirectory = _screenshotService.getScreenshotDirectory();

        // Assert
        assertEquals(expectedDirectory, actualDirectory);
    }
}
