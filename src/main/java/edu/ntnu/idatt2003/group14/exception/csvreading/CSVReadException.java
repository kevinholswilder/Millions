package edu.ntnu.idatt2003.group14.exception.csvreading;

/**
 * Exception thrown when the reading or parsing of a CSV file fails.
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 1.0.0
 */
public final class CSVReadException extends Exception {

  private final CSVError error;
  private ParsingError parsingError;

  /**
   * Constructor.
   *
   * @param message the message of the exception
   * @param error   the {@link CSVError} describing the type of error
   */
  public CSVReadException(String message, CSVError error) {
    super(message);
    this.error = error;
  }

  /**
   * Constructor.
   *
   * @param message the message of the exception
   * @param cause   the {@link Throwable} which caused the exception
   * @param error   the {@link CSVError} describing the type of error
   */
  public CSVReadException(String message, Throwable cause, CSVError error) {
    super(message, cause);
    this.error = error;
  }

  /**
   * Constructor.
   *
   * @param message      the message of the exception
   * @param error        the {@link CSVError} describing the type of error
   * @param parsingError the {@link ParsingError} which defines the failer point of the parsing
   */
  public CSVReadException(String message, CSVError error, ParsingError parsingError) {
    super(message);
    this.error = error;
    this.parsingError = parsingError;
  }

  /**
   * Fetch the error type.
   *
   * @return the {@link CSVError} describing the type of error
   */
  public CSVError getError() {
    return this.error;
  }

  /**
   * Fetch the parsing error.
   *
   * @return the {@link ParsingError} which defines the failer point of the parsing
   */
  public ParsingError getParsingError() {
    return this.parsingError;
  }
}
