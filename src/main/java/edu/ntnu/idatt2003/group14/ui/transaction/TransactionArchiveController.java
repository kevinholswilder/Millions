package edu.ntnu.idatt2003.group14.ui.transaction;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import javafx.scene.layout.HBox;

/**
 * Controller for transaction archive UI.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class TransactionArchiveController {

  /**
   * Creates a row representing a transaction.
   *
   * @param transaction the transaction to display
   * @return a row containing transaction information
   */
  public HBox createTransactionRow(Transaction transaction) {
    Share share = transaction.getShare();
    Stock stock = share.getStock();

    return new HBox(16);
  }
}