package com.dev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;


public class TextController {
    private static final Logger logger = LogManager.getLogger(TextController.class);

    public String readTextFromFile(String pathToFile) {
        URL resource = getClass().getClassLoader().getResource(pathToFile);
        if (resource == null) {
            logger.error("There's no resource {}", pathToFile);
            return null;
        }
        File file = new File(resource.getFile());
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
