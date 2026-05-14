package edu.ntnu.idatt2003.group14.exception.csvreading;

/**
 * Record holding information about a parsing error during CSV reading.
 *
 * @param errorLineNumber the line number of the error
 * @param errorLineString the contents of the line causing the error
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 1.0.0
 */
public record ParsingError(
    int errorLineNumber,
    String errorLineString
) {
}
