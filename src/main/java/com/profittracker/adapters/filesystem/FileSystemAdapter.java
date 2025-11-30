package com.profittracker.adapters.filesystem;

import com.profittracker.domain.BdrFileType;

import java.nio.file.Path;

public interface FileSystemAdapter {
    Path getFilePath(String filename, Path dir);
    Path getFileTypePath(BdrFileType fileType);
    String encodeFileToBase64(Path filePath);
}
