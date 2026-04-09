package edu.ntnu.idatt2003.group14.model.transaction;

import edu.ntnu.idatt2003.group14.exception.transaction.UnknownTransactionException;
import edu.ntnu.idatt2003.group14.model.Share;

/**
 * Represents the class which is responsible for creating new transactions.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class TransactionFactory {

  /**
   * Returns a new instance of the specified transaction type.
   *
   * @param type the type of transaction
   * @param week the week of the transaction
   * @return a new instance of the specified transaction type
   */
  public static Transaction get(TransactionType type, Share share, int week) {
    return switch (type) {
      case TransactionType.PURCHASE -> new Purchase(share, week);
      case TransactionType.SALE -> new Sale(share, week);
    };
  }

  /**
   * Returns a new instance of the specified transaction type.
   *
   * @param type the type of transaction
   * @param week the week of the transaction
   * @return a new instance of the specified transaction type
   */
  public static Transaction get(
      String type,
      Share share,
      int week
  ) throws UnknownTransactionException {
    TransactionType transactionType = TransactionType.fromString(type);
    if (transactionType == null) {
      throw new UnknownTransactionException("Invalid transaction type:" + type);
    }
    return switch (transactionType) {
      case TransactionType.PURCHASE -> new Purchase(share, week);
      case TransactionType.SALE -> new Sale(share, week);
    };
  }

}