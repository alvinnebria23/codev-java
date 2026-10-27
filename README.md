Your goal is to implement methods to read, write and parse CSV files.
We provide the templates for these methods, the application.properties file and the Event L class.
Requirements
You have to implement the following methods in three classes:
1. PropertiesFileHelper - reading configuration from an application.properties file using the Java Properties class
• NOTE: the following method is used in other classes, so please implement this method first.
• String
csvFileNameFromProperties()
- returns the CSV file name from application.properties

2. CsvFileDao - Input/Output Streams on files • List<Event› readAlL)
一
reads all CSV file lines and returns a List • of the Event objects;
• Event read(Long eventId)
- reads CSV file line with the given eventId L
and returns an Event
object;
• void write(Event event)
-
writes an Event
object into the
CSV file as a CSV line.
3. CsvFileUtils "parsing" CSV file,
using String class
• String csvLineFromEvent(Event
event)
- creates a CSV line
from an Event object;
• Event eventFromCsvLine(String csvRow)
- creates an Event
object from a CSV line.
• NOTE: all data in a CSV line and an
Event
• object have to be filled in, otherwise the task will not be accepted.
Java version and available librariesCSV line example
Event
class based on CSV line:
package com.codility.event;
import Lombok.AllArgsConstructor;
import lombok.Builder;
import Lombok.Data;
import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor public class Event f
private Long id;
private String name;
private String description; private LocalDateTime start; private LocalDateTime end; }
The example content of the CSV file is presented below:
1, Java Future, Conference about future of the Java programming Language, 2020-02-20T12:00, 2020-02-20T18:00
There is no header line in the CSV file, the below table is just a visual guide through the CSV file content.
id
name
escript start ion
end

Java
Future
Confere
2020-
2020-
nce
02-
02-
about
20T12:0
20T18:0
future of the Java
progra mming languag
e

#### You sent

Hints
• While working with the
osvFileNameFromProperties()
method an exception may be thrown.
Please catch any exceptions and return com.codility.PropertiesFileHelper
. DEFAULT_FILE_NAME
after the
exception was caught.
• The file path should be taken from the application.properties / file.
• The file has tabe opened in "append" mode; please don't overwrite the content, otherwise the task will not be accepted.
• To solve the task, choose one of three different I/O Streams solutions:
• Raw Input/Output Streams;
• Wrapper for Input/Output Streams;
• The NIO classes from Java 1.7.
If you would like to access CSV data sets locally you can download zipped files.