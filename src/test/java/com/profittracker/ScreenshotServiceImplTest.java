package com.profittracker;

import com.profittracker.services.screenshotservice.ScreenshotService;
import com.profittracker.services.screenshotservice.ScreenshotServiceImpl;
import net.runelite.api.Client;
import net.runelite.client.util.ImageCapture;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ScreenshotServiceImplTest {

    ScreenshotService _screenshotService;
    ImageCapture _imageCapture = mock(ImageCapture.class);
    Client _client = mock(Client.class);


    @Test
    public void TakeScreenshotShouldTakeAScreenshotAndStoreInBdrSubDir() {
        // Arrange
        String type = "bank";
        String player = "dragonslayer123";

        _screenshotService = new ScreenshotServiceImpl(_imageCapture, _client); // needs dependecies, mock them

        // Act
        _screenshotService.takeScreenshot(type, player);

        // Assert
        verify(_imageCapture).takeScreenshot(
                eq("bdr/"),
                contains("bank_dragonslayer123"), // partial match because of timestamp
                eq(true),
                eq(true),
                eq(false)
        );
    }

    @Test
    public void getScreenshotPathsShouldReturnListofPaths() {
        // Arrange
        _screenshotService = new ScreenshotServiceImpl(_imageCapture, _client, filesystem);
        List<path> expectedPaths = ["bank_pussycrusher69_timestamp", "death_weedfiend420_timestamp", "loot_goblin55_timestamp"];

        mockFilesystem.Setup(fs => fs.find("bdr/")).Returns(expectedPaths);

        // Act
        List<path> actualPaths = _screenshotService.getScreenshotPaths(); // this needs refactoring because the file system etc is tightly coupled and we can't mock/stub it

        // Assert
        assert that expectedPaths[0] is the same as actualPaths[0]
        assert that expectedPaths[1] is the same as actualPaths[1]
        assert that expectedPaths[2] is the same as actualPaths[2]
    }
}
