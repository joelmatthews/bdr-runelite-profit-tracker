package com.profittracker.adapters.filesystem;

import com.profittracker.domain.BdrFileType;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Stream;

import static net.runelite.client.RuneLite.RUNELITE_DIR;
import static net.runelite.client.RuneLite.SCREENSHOT_DIR;

public class  FileSystemAdapterImpl implements FileSystemAdapter {

    private final File _runeliteDir;
    private final File _screenshotDir;

    @Inject
    public FileSystemAdapterImpl() {
        this(RUNELITE_DIR, SCREENSHOT_DIR);
    }

    public FileSystemAdapterImpl(File runeliteDir, File screenshotDir) {
        this._runeliteDir = runeliteDir;
        this._screenshotDir = screenshotDir;
    }

    @Override
    public Path getFilePath(String filename, Path dir) {
        try {
//            Path dir = this.getFileTypePath(fileType);
            Stream<Path> stream = Files.find(dir, dir.getNameCount(),
                    (path, basicFileAttributes) -> path.getFileName().toString().equalsIgnoreCase(filename));

            Path foundFile = stream.findFirst().orElse(null);

            if (foundFile == null) {
                throw new IOException();
            }

            return foundFile;
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.print("Failed to get file for " + filename);
            return null;
        }
    }

    @Override
    public Path getFileTypePath(BdrFileType fileType) {
        switch  (fileType) {
            case SCREENSHOT:
                String bdrScreenshotDir = _screenshotDir.toString() + "/bdr/"; // ~/zejmat/runelite/screenshots/dragonslayer365/bdr/screenshot.png
                return Paths.get(bdrScreenshotDir);
            case LEAGUEDATA:
                String bdrLeagueDataDir = _runeliteDir.toString() + "/bdr/"; // ~/zejmat/runelite/.runeltie/bdr/leaguedata.json
                return Paths.get(bdrLeagueDataDir);
            default:
                return null;
        }
    }

    public Path getFileTypePath(BdrFileType fileType, String playerName) {
        switch (fileType) {
            case SCREENSHOT:
                String bdrScreenshotDir = _screenshotDir.toString() + "/bdr/" + playerName;
                return Paths.get(bdrScreenshotDir);
            case LEAGUEDATA:
                String bdrLeagueDataDir = _runeliteDir.toString() + "/bdr/" + playerName;
                return Paths.get(bdrLeagueDataDir);
            default:
                return null;
        }
    }

   @Override
    public String encodeFileToBase64(Path filePath) {
        try {
            byte[] fileContent = Files.readAllBytes(filePath);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Failed to encode file for " + filePath.toString());
            return null;
        }
    }
}
