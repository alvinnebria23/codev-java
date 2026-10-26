package com.codility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.codility.event.Event;

public class CsvFileUtils {
    private static final String CSV_SEPARATOR = ",";
    public static String csvLineFromEvent(Event event) {
        Objects.requireNonNull(event, "event");
        return String.join(CSV_SEPARATOR,
                escapeCsvField(Objects.requireNonNull(event.getId(), "event.id").toString()),
                escapeCsvField(Objects.requireNonNull(event.getName(), "event.name")),
                escapeCsvField(Objects.requireNonNull(event.getDescription(), "event.description")),
                escapeCsvField(Objects.requireNonNull(event.getStart(), "event.start").toString()),
                escapeCsvField(Objects.requireNonNull(event.getEnd(), "event.end").toString()));
    }

    public static Event eventFromCsvLine(String csvLine) {
        Objects.requireNonNull(csvLine, "csvLine");
        List<String> fields = parseCsvFields(csvLine);
        if (fields.size() != 5) {
            throw new IllegalArgumentException("Expected 5 CSV fields, got " + fields.size());
        }

        return new Event(
                Long.valueOf(fields.get(0)),
                fields.get(1),
                fields.get(2),
                LocalDateTime.parse(fields.get(3)),
                LocalDateTime.parse(fields.get(4)));
    }

    private static String escapeCsvField(String value) {
        if (value.contains(CSV_SEPARATOR) || value.contains("\"")
                || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private static List<String> parseCsvFields(String csvLine) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;
        boolean quotedField = false;

        for (int index = 0; index < csvLine.length(); index++) {
            char character = csvLine.charAt(index);
            if (inQuotes) {
                if (character == '"') {
                    if (index + 1 < csvLine.length() && csvLine.charAt(index + 1) == '"') {
                        field.append('"');
                        index++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    field.append(character);
                }
            } else if (character == CSV_SEPARATOR.charAt(0)) {
                fields.add(quotedField ? field.toString() : field.toString().trim());
                field.setLength(0);
                quotedField = false;
            } else if (character == '"' && field.toString().trim().isEmpty()) {
                field.setLength(0);
                quotedField = true;
                inQuotes = true;
            } else {
                field.append(character);
            }
        }

        if (inQuotes) {
            throw new IllegalArgumentException("Unterminated quoted CSV field");
        }
        fields.add(quotedField ? field.toString() : field.toString().trim());
        return fields;
    }
}
