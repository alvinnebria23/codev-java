package com.codility;

import com.codility.event.Event;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * #2 Task requirement: Input/Output Streams on files.
 *
 * This DAO must:
 * - read all CSV file lines and return a List<Event>
 * - read a single Event by eventId
 * - write one Event object into the CSV file as a CSV line
 *
 * The file must be opened in append mode when writing so existing content is not overwritten.
 */
public class CsvFileDao {
    public CsvFileDao() {}

    /**
    * #2.1 Task requirement: read all CSV file lines and return a List of Event objects.
     */
    public List<Event> readAll() throws Exception {
        // Purpose (#2.1): resolve the configured CSV file path before reading records.
        String csvFileName = PropertiesFileHelper.csvFileNameFromProperties();
        // Purpose (#2.1): turn the configured file name into a Path for file-system access.
        Path csvPath = Paths.get(csvFileName);
        // Purpose (#2.1): create a list to hold Event objects loaded from the CSV file.
        List<Event> events = new ArrayList<>();
        // Purpose (#2.1): return an empty list if the configured CSV file does not exist yet.
        if (!Files.exists(csvPath)) {
            return events;
        }

        // Purpose (#2.1): read each file line and convert each valid CSV row into an Event.
        try (BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                // Purpose (#2.1): ignore blank lines so only CSV records are converted.
                if (!csvLine.trim().isEmpty()) {
                    events.add(CsvFileUtils.eventFromCsvLine(csvLine));
                }
            }
        }
        // Purpose (#2.1): return all Event objects loaded from the file.
        return events;
    }

    /**
    * #2.3 Task requirement: write an Event object into the CSV file as a CSV line.
     * The file must be opened in append mode so old records are preserved.
     */
    public void write(Event event) throws Exception {
        // Purpose (#2.3): retrieve the configured CSV file name from application.properties.
        String csvFileName = PropertiesFileHelper.csvFileNameFromProperties();
        // Purpose (#2.3): convert the Event into its CSV representation before writing.
        String csvLine = CsvFileUtils.csvLineFromEvent(event) + "\n";
        // Purpose (#2.3): create a Path for the file that will receive the new record.
        Path csvPath = Paths.get(csvFileName);
        // Purpose (#2.3): ensure the parent folder exists before appending a CSV line.
        Path parent = csvPath.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        // Purpose (#2.3): open the file in append mode so previously saved events stay in the file.
        try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(csvLine);
        }
    }

    /**
    * #2.2 Task requirement: read the CSV line with the given eventId and return the matching Event.
     */
    public Event read(Long eventId) throws Exception {
        // Purpose (#2.2): load CSV records before searching for the matching id.
        List<Event> events = readAll();
        // Purpose (#2.2): return the Event whose id matches the requested value.
        for (Event event : events) {
            if (Objects.equals(event.getId(), eventId)) {
                return event;
            }
        }
        // Purpose (#2.2): return null when no record matches the provided eventId.
        return null;
    }
}
