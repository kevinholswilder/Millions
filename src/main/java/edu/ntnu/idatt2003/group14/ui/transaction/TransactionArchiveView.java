package edu.ntnu.idatt2003.group14.ui.transaction;

import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import edu.ntnu.idatt2003.group14.model.transaction.TransactionArchive;
import edu.ntnu.idatt2003.group14.ui.App;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class TransactionArchiveView {
  private final StackPane root;
  private final TransactionArchiveController controller;

  /**
   *
   */
  public TransactionArchiveView(App app) {
    root = new StackPane();
    controller = new TransactionArchiveController();

    root.setPadding(new Insets(20));

    TransactionArchive archive = new TransactionArchive();
    List<Transaction> transactions = archive.getTransactions(1);

    VBox list = new VBox(10);
    for (Transaction transaction : transactions) {
      list.getChildren().add(controller.createTransactionRow(transaction));
    }
  }

  public Parent getRoot() {
    return root;
  }
}
