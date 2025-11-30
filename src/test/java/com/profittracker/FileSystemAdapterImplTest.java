package com.profittracker;

import com.profittracker.adapters.filesystem.FileSystemAdapter;
import com.profittracker.adapters.filesystem.FileSystemAdapterImpl;
import com.profittracker.domain.BdrFileType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileSystemAdapterImplTest {

    private FileSystemAdapter _fileSystemAdapter;

    @Rule
    public TemporaryFolder stubFolder = new TemporaryFolder();

    @Test
    public void getFilePath_ShouldReturnAPathToTheFile() throws IOException {
        // Arrange
        File stubScreenshotDir = stubFolder.newFolder("screenshots", "ninja12366");
        FileSystemAdapter _fileSystemAdapter = new FileSystemAdapterImpl(null, stubScreenshotDir);
        String filename = "death_ninja12366_Sat-Nov-15-16:24:56-GMT-2025";

        File stubScreenshot = new File(stubScreenshotDir, filename);
        stubScreenshot.createNewFile(); // ~/zejmat/runelite/screenshots/dragonslayer365/screenshot.png/bdr/

        Path expectedPath = stubScreenshot.toPath();

        // Act
        Path actualPath = _fileSystemAdapter.getFilePath(filename, stubScreenshotDir.toPath());


        // Assert
        assertEquals(expectedPath.toString(), actualPath.toString());

    }

    // screenshots/ninja12366/screenshot.png + /bdr/ <-- how it currently is
    // screenshots/ninja12366/bdr/screenshot.png <-- want this

    @Test
    public void encodeFileToBase64_ShouldReturnAnEncodedString() throws IOException {
        // Arrange
        List<String> lines = Arrays.asList("The first line", "The second line");
        String filename = "death_ninja12366_Sat-Nov-15-16:24:56-GMT-2025";
        File stubScreenshotDir = stubFolder.newFolder("screenshots", "ninja12366", "bdr");
        FileSystemAdapter _fileSystemAdapter = new FileSystemAdapterImpl(null, stubScreenshotDir);
        File stubScreenshot = new File(stubScreenshotDir, filename);
        stubScreenshot.createNewFile();
        Files.write(Paths.get(stubScreenshot.toString()), lines, StandardCharsets.UTF_8); // adds lines to the stub file

        // Act
        String actualBase64String = _fileSystemAdapter.encodeFileToBase64(stubScreenshot.toPath());

        // Assert
        assertNotNull(actualBase64String);
        assertFalse(actualBase64String.isEmpty());
    }
}
