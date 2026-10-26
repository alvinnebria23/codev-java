package com.codility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.codility.event.Event;

/**
 * #2 Task requirement: parsing CSV files using the String class.
 *
 * The assignment requires these methods:
 * - String csvLineFromEvent(Event event)
 * - Event eventFromCsvLine(String csvRow)
 *
 * The CSV row must have all fields filled in: id, name, description, start, end.
 * Example:
 * 1,Java Future,Conference about future of the Java programming language,2020-02-20T12:00,2020-02-20T18:00
 */
public class CsvFileUtils {
    private static final String CSV_SEPARATOR = ",";
    /**
     * #2.1 Task requirement: create a CSV line from an Event object.
     * All required fields are converted to String values and escaped if they contain commas, quotes,
     * or line breaks, so the generated CSV row remains valid.
     */
    public static String csvLineFromEvent(Event event) {
        // Purpose: validate that the Event object is not null before converting it to CSV.
        Objects.requireNonNull(event, "event");
        // Purpose: build the CSV row in the required order: id, name, description, start, end.
        return String.join(CSV_SEPARATOR,
                escapeCsvField(Objects.requireNonNull(event.getId(), "event.id").toString()),
                escapeCsvField(Objects.requireNonNull(event.getName(), "event.name")),
                escapeCsvField(Objects.requireNonNull(event.getDescription(), "event.description")),
                escapeCsvField(Objects.requireNonNull(event.getStart(), "event.start").toString()),
                escapeCsvField(Objects.requireNonNull(event.getEnd(), "event.end").toString()));
    }

    /**
     * #2.2 Task requirement: create an Event object from a CSV line.
     * The CSV row must contain all required values: id, name, description, start, end.
     */
    public static Event eventFromCsvLine(String csvLine) {
        // Purpose: reject null CSV input before parsing it into an Event.
        Objects.requireNonNull(csvLine, "csvLine");
        // Purpose: split the CSV row into the five required fields.
        List<String> fields = parseCsvFields(csvLine);
        // Purpose: validate that the row contains exactly the expected number of columns.
        if (fields.size() != 5) {
            throw new IllegalArgumentException("Expected 5 CSV fields, got " + fields.size());
        }

        // Purpose: rebuild the Event object using the CSV values in id, name, description, start, end order.
        return new Event(
                Long.valueOf(fields.get(0)),
                fields.get(1),
                fields.get(2),
                LocalDateTime.parse(fields.get(3)),
                LocalDateTime.parse(fields.get(4)));
    }

    private static String escapeCsvField(String value) {
        // Purpose: wrap fields in quotes when commas or quotes would otherwise break the CSV format.
        if (value.contains(CSV_SEPARATOR) || value.contains("\"")
                || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private static List<String> parseCsvFields(String csvLine) {
        // Purpose: accumulate the data for one CSV field while tracking whether it is quoted.
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
