package com.profittracker.adapters.filesystem;

import java.nio.file.Path;

public interface FileSystemAdapter {
    Path getFilePath(String filename, String directory);
    String encodeFileToBase64(Path filePath);
}
