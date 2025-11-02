package com.profittracker.services.screenshotservice;
import com.profittracker.adapters.cloudadapter.CloudAdapter;
import com.profittracker.adapters.filesystem.FileSystemAdapter;
import com.profittracker.adapters.http.HttpAdapter;
import net.runelite.api.Client;
import net.runelite.client.config.RuneScapeProfileType;
import net.runelite.client.util.ImageCapture;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.util.Date;

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
            String pathToScreenshot = this.getScreenshotPath(screenshotDir, filename);

            if (pathToScreenshot != null) {
                _httpAdapter.postAsync(pathToScreenshot); // revisit this, can't just send path to screenshot now. Will probably have to look at sending  base64 encoded string or multipart data
            }

        } catch(Exception e) {
            e.printStackTrace();
            System.out.print("Screenshot error: " + e.getMessage());
        }
    }

    public String getScreenshotPath(String directory, String filename) {
        try {
            String pathToScreenshot = _fileSystemAdapter.findFilePath(filename, directory);

            return pathToScreenshot;
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.print("Failed to get screenshot for " + filename);
            return null;
        }
    }

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
