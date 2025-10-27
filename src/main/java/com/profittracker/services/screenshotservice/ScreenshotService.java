package com.profittracker.services.screenshotservice;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ScreenshotService {
    public void takeScreenshot(String type, String player);
    public String getScreenshotPath(String filename, String directory);
}
