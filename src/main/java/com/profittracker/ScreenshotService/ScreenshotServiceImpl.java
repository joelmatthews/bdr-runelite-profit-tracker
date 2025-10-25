package com.profittracker.ScreenshotService;
import net.runelite.api.Client;
import net.runelite.client.config.RuneScapeProfileType;
import net.runelite.client.util.ImageCapture;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Date;

import static net.runelite.client.RuneLite.SCREENSHOT_DIR;

public class ScreenshotServiceImpl implements ScreenshotService {

    private final ImageCapture _imageCapture;
    private final Client _client;


    @Inject
    public ScreenshotServiceImpl(ImageCapture imageCapture, Client client) {
        _imageCapture = imageCapture;
        _client = client;
    }



    // takes and stores a screenshot using client apis (avoid using RuneLiteScreenshotPlugin for now)
    public void takeScreenshot(String type, String player) {
        Date timestamp = new Date();
        String filename = "type_player_timestamp"; // should name files based on type, like death / bank / kill / loot etc.
        _imageCapture.takeScreenshot("bdr/", "test", true, true, false );
    }

    // returns list of all screenshotPaths
    // might need to sort them, idk yet
    public List<Path> getScreenshotPaths() throws IOException {
        Path playerBdrFolder;

        if (_client.getLocalPlayer() != null && _client.getLocalPlayer().getName() != null)
        {
            String playerDir = _client.getLocalPlayer().getName();
            RuneScapeProfileType profileType = RuneScapeProfileType.getCurrent(_client);
            if (profileType != RuneScapeProfileType.STANDARD)
            {
                playerDir += "-" + Text.titleCase(profileType);
            }

            playerDir += File.separator + "bdr/";

            playerBdrFolder = Paths.get(SCREENSHOT_DIR + File.separator + playerDir);
        }
        else
        {
            playerBdrFolder = Paths.get(SCREENSHOT_DIR + "bdr/");
        }

        List<Path> screenshotPaths = new ArrayList<Path>();

        try {
            Stream<Path> paths = Files.walk(playerBdrFolder)
                    .filter(Files::isRegularFile);

            paths.forEach(screenshotPaths::add);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }

        return screenshotPaths;
    }

}
