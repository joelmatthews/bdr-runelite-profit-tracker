package com.profittracker.domain;

import java.nio.file.Path;
import java.nio.file.Paths;

import static net.runelite.client.RuneLite.RUNELITE_DIR;
import static net.runelite.client.RuneLite.SCREENSHOT_DIR;

public enum BdrFileType {
    SCREENSHOT,
    LEAGUEDATA;

    public Path getPath() {
        switch  (this) {
            case SCREENSHOT:
                String bdrScreenshotDir = SCREENSHOT_DIR.toString() + "/bdr/";
                return Paths.get(bdrScreenshotDir);
            case LEAGUEDATA:
                String bdrLeagueDataDir = RUNELITE_DIR.toString() + "/bdr/";
                return Paths.get(bdrLeagueDataDir);
            default:
                return null;
        }
    }

    public Path getPath(String playerName) {
        switch (this) {
            case SCREENSHOT:
                String bdrScreenshotDir = SCREENSHOT_DIR.toString() + "/bdr/" + playerName;
                return Paths.get(bdrScreenshotDir);
            case LEAGUEDATA:
                String bdrLeagueDataDir = RUNELITE_DIR.toString() + "/bdr/" + playerName;
                return Paths.get(bdrLeagueDataDir);
            default:
                return null;
        }
    }
}
