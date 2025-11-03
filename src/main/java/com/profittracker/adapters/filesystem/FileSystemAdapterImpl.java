package com.profittracker.adapters.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Stream;

public class FileSystemAdapterImpl implements FileSystemAdapter {

    public Path getFilePath(String filename, String directory) {
        try {
            Path dir =  Paths.get(directory);
            Stream<Path> stream = Files.find(dir, 1,
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
