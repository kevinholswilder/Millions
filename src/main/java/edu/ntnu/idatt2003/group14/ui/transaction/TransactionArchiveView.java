package edu.ntnu.idatt2003.group14.ui.transaction;

import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import edu.ntnu.idatt2003.group14.model.transaction.TransactionArchive;
import java.util.List;
import java.util.stream.IntStream;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * View responsible for displaying the {@link TransactionArchive} of a {@link Player} object.
 *
 * <p>The view uses a {@link TransactionArchiveController} to create
 * visual rows for each transaction and displays them in a vertically
 * arranged list.</p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class TransactionArchiveView {
  private final BorderPane root;
  private final TransactionArchiveController controller;

  /**
   * Instantiates a new transaction archive view.
   *
   * @param controller the controller used to create transaction rows
   */
  public TransactionArchiveView(TransactionArchiveController controller) {
    this.root = new BorderPane();
    this.controller = controller;

    this.root.getStylesheets().add(
        getClass().getResource("/css/transaction-row.css").toExternalForm()
    );

    // TODO: Retrieve the transactions from a Player object.
    // this.setTransactions(transactions);
  }

  /**
   * Displays the given transactions in the view.
   *
   * @param transactions the transactions to display
   */
  public void setTransactions(List<Transaction> transactions) {
    VBox transactionList = this.createTransactionList(transactions);
    this.root.setCenter(transactionList);
  }

  /**
   * Creates a vertical list containing rows for each transaction.
   *
   * @param transactions the transactions to include in the list
   * @return a {@link VBox} containing transaction rows
   */
  private VBox createTransactionList(List<Transaction> transactions) {
    VBox list = new VBox(10);

    list.setPadding(new Insets(20));

    IntStream.range(0, transactions.size()).forEach(i -> list.getChildren().add(
        controller.createTransactionRow(transactions.get(i),
            i
        )
    ));

    return list;
  }

  /**
   * Returns the root node of the layout.
   *
   * @return the root layout node
   */
  public Parent getRoot() {
    return root;
  }

}