package edu.ntnu.idatt2003.group14.exception.csvreading;

/**
 * Enumerator to show the {@link CSVReadException} failer point.
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 1.0.0
 */
public enum CSVError {
  FILE_NOT_FOUND,
  READ_FAILED,
  EMPTY_FILE,
  PARSING
}
