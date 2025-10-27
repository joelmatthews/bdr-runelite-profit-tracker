package com.profittracker.adapters.cloudadapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileSystemAdapterImpl implements FileSystemAdapter {

    public String findFilePath(String filename, String directory) {
        try {
            Path dir =  Paths.get(directory);
            Stream<Path> stream = Files.find(dir, 1,
                    (path, basicFileAttributes) -> path.getFileName().toString().equalsIgnoreCase(filename));

            Path foundFile = stream.findFirst().orElse(null);

            if (foundFile == null) {
                throw new IOException();
            }

            return foundFile.toString();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.print("Failed to get screenshot for " + filename);
            return null;
        }
    }

    public String getDirectoryPath(String first, String... more) {
        Path directory = Paths.get(first, more);

        return directory.toString();
    }
}
