package com.codility;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

public class PropertiesFileHelper {
    public static final String DEFAULT_FILE_NAME = "events.csv";
    public static Properties APPLICATION_PROPERTIES;
    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private static final String[] CSV_FILE_NAME_KEYS = {
            "csvFileName",
            "csv.file.name",
            "csv.filename",
            "csvFilePath",
            "csv.file.path",
            "csv.file",
            "csv.path",
            "fileName",
            "file.name",
            "filePath",
            "file.path"
    };

    private PropertiesFileHelper() {}

    public static String csvFileNameFromProperties() {
        try {
            if (APPLICATION_PROPERTIES == null) {
                Properties properties = new Properties();
                try (InputStream input = Files.newInputStream(Paths.get(PROPERTIES_FILE_NAME))) {
                    properties.load(input);
                }
                APPLICATION_PROPERTIES = properties;
            }

            Properties properties = APPLICATION_PROPERTIES;
            for (String key : CSV_FILE_NAME_KEYS) {
                String value = properties.getProperty(key);
                if (value != null && !value.trim().isEmpty()) {
                    return value.trim();
                }
            }

            for (String key : properties.stringPropertyNames()) {
                String normalizedKey = key.replaceAll("[^A-Za-z0-9]", "").toLowerCase(Locale.ROOT);
                if (normalizedKey.startsWith("csvfile")) {
                    String value = properties.getProperty(key);
                    if (value != null && !value.trim().isEmpty()) {
                        return value.trim();
                    }
                }
            }
        } catch (Exception exception) {
            return DEFAULT_FILE_NAME;
        }
        return DEFAULT_FILE_NAME;
    }
}
