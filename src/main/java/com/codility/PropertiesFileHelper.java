package com.codility;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

/**
 * #1 Task requirement: reading configuration from an application.properties file using the Java Properties class.
 *
 * The assignment says the CSV file name/path must be read from application.properties.
 * If an exception is thrown, the code must catch it and return DEFAULT_FILE_NAME.
 * This helper also keeps a static Properties object for reuse and checks common property names such as
 * csvFileName, csv.file.name, csv.filename, csvFilePath, and similar variants.
 */
public class PropertiesFileHelper {
    public static final String DEFAULT_FILE_NAME = "events.csv";
    /**
     * The task expects this static field to be accessible from tests so it can be reset to null.
     * This allows lazy reloading of application.properties when needed.
     */
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

    /**
     * Required by the task: return the CSV file name from application.properties.
     * If the file is missing, unreadable, or the configured property is absent, return DEFAULT_FILE_NAME.
     */
    public static String csvFileNameFromProperties() {
        try {
            if (APPLICATION_PROPERTIES == null) {
                Properties properties = new Properties();
                try (InputStream input = Files.newInputStream(Paths.get(PROPERTIES_FILE_NAME))) {
                    properties.load(input);
                }
                APPLICATION_PROPERTIES = properties;
            }
            
            // Purpose: reuse the cached properties object so the CSV file name can be resolved once and reused.
            Properties properties = APPLICATION_PROPERTIES;
            // Purpose: check the common property names for the CSV file name/path and return the first valid value.
            for (String key : CSV_FILE_NAME_KEYS) {
                String value = properties.getProperty(key);
                if (value != null && !value.trim().isEmpty()) {
                    return value.trim();
                }
            }

            // Purpose: inspect remaining property entries for any alias that still matches the CSV file configuration.
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
