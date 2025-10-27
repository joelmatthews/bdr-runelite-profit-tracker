package com.profittracker.adapters.cloudadapter;

public interface FileSystemAdapter {
    public String findFilePath(String filename, String directory);
    public String getDirectoryPath(String first, String... more);
}
