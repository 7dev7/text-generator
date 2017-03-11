package com.dev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;


public class FileTextController {
    private static final Logger logger = LogManager.getLogger(FileTextController.class);

    public String readTextFromFile(String pathToFile) {
        URL resource = getClass().getClassLoader().getResource(pathToFile);
        if (resource == null) {
            logger.error("There's no resource {}", pathToFile);
            throw new IllegalArgumentException("There's no resource " + pathToFile);
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("Error during file reading: ", e);
        }
        return sb.toString();
    }
}
