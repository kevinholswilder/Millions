package edu.ntnu.idatt2003.group14.exception.csvReading;

public record ParsingError(
    int errorLineNumber,
    String errorLineString
) {
}
