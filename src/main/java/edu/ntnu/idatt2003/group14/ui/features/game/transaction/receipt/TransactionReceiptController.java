package edu.ntnu.idatt2003.group14.ui.features.game.transaction.receipt;

import edu.ntnu.idatt2003.group14.model.transaction.Transaction;

/**
 * Controller for storing the transaction shown in the receipt view.
 *
 * <p>This controller uses a singleton instance so the current transaction
 * can be accessed when opening the receipt popup.</p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class TransactionReceiptController {

  private static TransactionReceiptController instance;

  private Transaction transaction;

  /**
   * Prevents instantiation.
   */
  private TransactionReceiptController() {}

  /**
   * Returns the singleton instance of the controller.
   *
   * @return the shared transaction receipt controller instance
   */
  public static TransactionReceiptController getInstance() {
    if (instance == null) {
      instance = new TransactionReceiptController();
    }
    return instance;
  }

  /**
   * Returns the transaction currently shown in the receipt.
   *
   * @return the current transaction
   */
  public Transaction getTransaction() {
    return transaction;
  }

  /**
   * Sets the transaction to show in the receipt.
   *
   * @param transaction the transaction to display
   */
  public void setTransaction(Transaction transaction) {
    this.transaction = transaction;
  }
}