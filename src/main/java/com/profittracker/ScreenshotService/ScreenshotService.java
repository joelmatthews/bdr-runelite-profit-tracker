package com.profittracker.ScreenshotService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ScreenshotService {
    public void takeScreenshot(String type, String player);
    public List<Path> getScreenshotPaths() throws IOException;
}
