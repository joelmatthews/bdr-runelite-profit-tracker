package com.profittracker;

import com.profittracker.adapters.filesystem.FileSystemAdapter;
import com.profittracker.adapters.filesystem.FileSystemAdapterImpl;
import com.profittracker.adapters.http.HttpAdapter;
import com.profittracker.services.screenshotservice.ScreenshotService;
import com.profittracker.services.screenshotservice.ScreenshotServiceImpl;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.WorldType;
import net.runelite.client.util.ImageCapture;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static net.runelite.client.RuneLite.SCREENSHOT_DIR;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FileSystemAdapterImplTest {

    private FileSystemAdapter _fileSystemAdapter;

    public FileSystemAdapterImplTest() {
        this._fileSystemAdapter = new FileSystemAdapterImpl();
    }

    @Rule
    public TemporaryFolder stubFolder = new TemporaryFolder();

    @Test
    public void getFilePath_ShouldReturnAPathToTheFile() throws IOException {
        // Arrange
        String filename = "death_ninja12366_Sat-Nov-15-16:24:56-GMT-2025";
        File stubScreenshotDir = stubFolder.newFolder("screenshots", "ninja12366", "bdr");
        File stubScreenshot = new File(stubScreenshotDir, filename);
        stubScreenshot.createNewFile();

        Path expectedPath = stubScreenshot.toPath();

        // Act
        Path actualPath = _fileSystemAdapter.getFilePath(filename, stubScreenshotDir.toString());


        // Assert
        assertEquals(expectedPath.toString(), actualPath.toString());
    }

    @Test
    public void encodeFileToBase64_ShouldReturnAnEncodedString() throws IOException {
        // Arrange
        List<String> lines = Arrays.asList("The first line", "The second line");
        String filename = "death_ninja12366_Sat-Nov-15-16:24:56-GMT-2025";
        File stubScreenshotDir = stubFolder.newFolder("screenshots", "ninja12366", "bdr");
        File stubScreenshot = new File(stubScreenshotDir, filename);
        stubScreenshot.createNewFile();
        Files.write(Paths.get(stubScreenshot.toString()), lines, StandardCharsets.UTF_8);

        // Act
        String actualBase64String = _fileSystemAdapter.encodeFileToBase64(stubScreenshot.toPath());

        // Assert
        assertNotNull(actualBase64String);
        assertFalse(actualBase64String.isEmpty());
    }
}
