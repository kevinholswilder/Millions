package edu.ntnu.idatt2003.group14.ui.transaction;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import javafx.scene.layout.HBox;

/**
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class TransactionArchiveController {

  /**
   * Creates a row for a transaction.
   *
   * @param transaction
   * @return
   */
  public HBox createTransactionRow(Transaction transaction) {
    Share share = transaction.getShare();
    Stock stock = share.getStock();

    return new HBox(16);
  }

}