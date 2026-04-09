package edu.ntnu.idatt2003.group14.exception.transaction;

/**
 * Represents an exception that occurs when an unknown transaction type is encountered.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class UnknownTransactionException extends Exception {

  /**
   * Constructor for UnknownTransactionException.
   *
   * @param message the message
   */
  public UnknownTransactionException(String message) {
    super(message);
  }

}