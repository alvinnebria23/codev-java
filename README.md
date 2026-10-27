# CSV File Task

Your goal is to implement methods to read, write, and parse CSV files. Templates for these methods, the `application.properties` file, and the `Event` class are provided.

## Requirements

Implement the following methods in three classes:

### 1. PropertiesFileHelper: read configuration from an application.properties file using the Java Properties class

- Note: This method is used by other classes, so implement it first.
- `String csvFileNameFromProperties()` returns the CSV file name from `application.properties`.

### 2. CsvFileDao: input/output streams on files

- `List<Event> readAll()` reads all CSV file lines and returns a list of `Event` objects.
- `Event read(Long eventId)` reads the CSV file line with the given `eventId` and returns an `Event` object.
- `void write(Event event)` writes an `Event` object into the CSV file as a CSV line.

### 3. CsvFileUtils: parse CSV files using the String class

- `String csvLineFromEvent(Event event)` creates a CSV line from an `Event` object.
- `Event eventFromCsvLine(String csvRow)` creates an `Event` object from a CSV line.
- Note: All data in a CSV line and an `Event` object must be filled in; otherwise, the task will not be accepted.

## Java version and available libraries

## CSV line example

The `Event` class based on a CSV line:

```java
package com.codility.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Event {
		private Long id;
		private String name;
		private String description;
		private LocalDateTime start;
		private LocalDateTime end;
}
```

Example content of the CSV file:

```text
1, Java Future, Conference about future of the Java programming Language, 2020-02-20T12:00, 2020-02-20T18:00
```

There is no header line in the CSV file. The table below is only a visual guide to the CSV file content.

| id | name | description | start | end |
| --- | --- | --- | --- | --- |
| 1 | Java Future | Conference about future of the Java programming Language | 2020-02-20T12:00 | 2020-02-20T18:00 |

## Hints

- While working with `csvFileNameFromProperties()`, an exception may be thrown. Catch any exceptions and return `com.codility.PropertiesFileHelper.DEFAULT_FILE_NAME` after the exception is caught.
- The file path should be taken from the `application.properties` file.
- The file must be opened in append mode. Do not overwrite the content, or the task will not be accepted.
- Choose one of these three I/O stream solutions:
	- Raw input/output streams
	- Wrappers for input/output streams
	- The NIO classes from Java 1.7
- If you would like to access CSV datasets locally, you can download zipped files.
