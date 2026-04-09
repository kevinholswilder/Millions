package edu.ntnu.idatt2003.group14.model.transaction;

/**
 * Represents the type of transaction.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public enum TransactionType {
  PURCHASE,
  SALE;

  /**
   * Returns the string representation of the transaction type.
   *
   * @param type the transaction type
   * @return the string representation of the transaction type
   */
  public static String toString(TransactionType type) {
    return type.name();
  }

  /**
   * Returns the transaction type from the string representation.
   *
   * @param type the string representation of the transaction type
   * @return the transaction type
   */
  public static TransactionType fromString(String type) {
    return TransactionType.valueOf(type.toUpperCase());
  }
}