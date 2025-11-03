package com.profittracker.services.screenshotservice;

public interface ScreenshotService {
    void takeScreenshot(String type, String player);
    String getScreenshotDirectory();
}
