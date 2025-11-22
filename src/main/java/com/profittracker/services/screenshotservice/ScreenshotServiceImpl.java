package com.profittracker.services.screenshotservice;
import com.profittracker.adapters.filesystem.FileSystemAdapter;
import com.profittracker.adapters.http.HttpAdapter;
import net.runelite.api.Client;
import net.runelite.client.config.RuneScapeProfileType;
import net.runelite.client.util.ImageCapture;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.*;

import static net.runelite.client.RuneLite.SCREENSHOT_DIR;

public class ScreenshotServiceImpl implements ScreenshotService {

    private final ImageCapture _imageCapture;
    private final Client _client;
    private final HttpAdapter _httpAdapter;
    private final FileSystemAdapter _fileSystemAdapter;


    @Inject
    public ScreenshotServiceImpl(ImageCapture imageCapture, Client client, HttpAdapter httpAdapter, FileSystemAdapter fileSystemAdapter) {
        _imageCapture = imageCapture;
        _client = client;
        _httpAdapter = httpAdapter;
        _fileSystemAdapter = fileSystemAdapter;
    }

    public void takeScreenshot(String type, String player) {
        String timestamp = new Date().toString().replaceAll(" ", "-");
        String filename = type + "_" + player + "_" + timestamp; // should name files based on type, like death / bank / kill / loot etc.

        try {
            _imageCapture.takeScreenshot("bdr/", filename, true, true, false );
            String screenshotDir = this.getScreenshotDirectory();
            Path screenshotPath = _fileSystemAdapter.getFilePath(screenshotDir, filename);

            if (screenshotPath != null) {
                String encodedFile = _fileSystemAdapter.encodeFileToBase64(screenshotPath);
                Map<String, String> content = Map.ofEntries(
                        Map.entry("screenshot", encodedFile),
                        Map.entry("playerName", _client.getLocalPlayer().getName()));
                _httpAdapter.postAsync(content, "/screenshot");
            }

        } catch(Exception e) {
            System.out.print("Screenshot error: " + e.getMessage());
        }
    }

    // TODO: can probably look to refactor. If we pass Client object or even just Player object to takeScreenshot then we can pass to this method
    // meaning we eliminate a dependency on Client in this service
    public String getScreenshotDirectory() {
        if (_client.getLocalPlayer() != null && _client.getLocalPlayer().getName() != null) {
            String playerBdrFolder;
            String playerDir = _client.getLocalPlayer().getName();
            RuneScapeProfileType profileType = RuneScapeProfileType.getCurrent(_client);
            if (profileType != RuneScapeProfileType.STANDARD) {
                playerDir += "-" + Text.titleCase(profileType);
            }

            playerDir += "/" + "bdr/";

            playerBdrFolder = SCREENSHOT_DIR + "/" + playerDir;

            return playerBdrFolder;
        }

        return null;
    }
}
