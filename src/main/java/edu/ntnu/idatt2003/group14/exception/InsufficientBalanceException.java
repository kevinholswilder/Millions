package edu.ntnu.idatt2003.group14.exception;

/**
 * Exception thrown when a player tries to buy shares with insufficient funds.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public final class InsufficientBalanceException extends Exception {

  /**
   * Constructor for InsufficientBalanceException.
   *
   * @param message the message
   */
  public InsufficientBalanceException(String message) {
    super(message);
  }

}
