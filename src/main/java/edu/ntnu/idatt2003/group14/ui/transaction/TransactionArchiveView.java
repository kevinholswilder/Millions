package edu.ntnu.idatt2003.group14.ui.transaction;

import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * View for displaying archived transactions.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class TransactionArchiveView {
  private final BorderPane root;
  private final TransactionArchiveController controller;

  /**
   * Creates a transaction archive view.
   *
   * @param controller the controller used to create transaction rows
   */
  public TransactionArchiveView(TransactionArchiveController controller) {
    this.root = new BorderPane();
    this.controller = controller;

    root.setPadding(new Insets(0, 20, 20, 20));
  }

  /**
   * Displays the given transactions in the view.
   *
   * @param transactions the transactions to display
   */
  public void setTransactions(List<Transaction> transactions) {
    VBox transactionList = createTransactionList(transactions);
    root.setCenter(transactionList);
  }

  private VBox createTransactionList(List<Transaction> transactions) {
    VBox list = new VBox(10);

    for (Transaction transaction : transactions) {
      list.getChildren().add(controller.createTransactionRow(transaction));
    }

    return list;
  }

  public Parent getRoot() {
    return root;
  }
}