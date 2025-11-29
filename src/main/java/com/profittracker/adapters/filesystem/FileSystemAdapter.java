package com.profittracker.adapters.filesystem;

import java.nio.file.Path;

public interface FileSystemAdapter<T> {
    Path getFilePath(String filename, T fileType);
    String encodeFileToBase64(Path filePath);
}
