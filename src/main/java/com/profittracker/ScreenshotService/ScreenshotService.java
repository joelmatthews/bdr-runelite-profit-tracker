package com.profittracker.ScreenshotService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ScreenshotService {
    public void takeScreenshot();
    public List<Path> getScreenshotPaths() throws IOException;
}
