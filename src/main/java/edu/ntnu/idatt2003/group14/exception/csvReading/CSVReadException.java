package edu.ntnu.idatt2003.group14.exception.csvReading;

public class CSVReadException extends Exception {

  private final CSVError error;
  private ParsingError parsingError;

  public CSVReadException(String message, CSVError error) {
    super(message);
    this.error = error;
  }

  public CSVReadException(String message, Throwable cause, CSVError error) {
    super(message, cause);
    this.error = error;
  }

  public CSVReadException(String message, CSVError error, ParsingError parsingError) {
    super(message);
    this.error = error;
    this.parsingError = parsingError;
  }

  public CSVError getError() {
    return this.error;
  }

  public ParsingError getParsingError() {
    return this.parsingError;
  }
}
