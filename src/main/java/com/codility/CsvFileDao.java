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

public class CsvFileDao {
    public CsvFileDao() {}

    public List<Event> readAll() throws Exception {
        String csvFileName = PropertiesFileHelper.csvFileNameFromProperties();
        Path csvPath = Paths.get(csvFileName);
        List<Event> events = new ArrayList<>();
        if (!Files.exists(csvPath)) {
            return events;
        }

        try (BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                if (!csvLine.trim().isEmpty()) {
                    events.add(CsvFileUtils.eventFromCsvLine(csvLine));
                }
            }
        }
        return events;
    }

    public void write(Event event) throws Exception {
        String csvFileName = PropertiesFileHelper.csvFileNameFromProperties();
        String csvLine = CsvFileUtils.csvLineFromEvent(event) + "\n";
        Path csvPath = Paths.get(csvFileName);
        Path parent = csvPath.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(csvLine);
        }
    }

    public Event read(Long eventId) throws Exception {
        List<Event> events = readAll();
        for (Event event : events) {
            if (Objects.equals(event.getId(), eventId)) {
                return event;
            }
        }
        return null;
    }
}
