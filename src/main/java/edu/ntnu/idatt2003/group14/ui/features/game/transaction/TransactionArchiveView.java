package edu.ntnu.idatt2003.group14.ui.features.game.transaction;

import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import edu.ntnu.idatt2003.group14.model.transaction.TransactionArchive;
import edu.ntnu.idatt2003.group14.ui.app.View;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
public class TransactionArchiveView implements View {
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

    Parent topBar = this.createTopBar();
    BorderPane.setMargin(topBar, new Insets(20, 20, 0, 20));
    this.root.setTop(topBar);

    this.root.getStylesheets().addAll(
        Objects.requireNonNull(
            getClass().getResource("/css/transaction_archive/transaction-row.css")
        ).toExternalForm(),
        Objects.requireNonNull(
            getClass().getResource("/css/transaction_archive/topbar.css")
        ).toExternalForm()
    );

    // TODO: Retrieve the transactions from a Player object.
    // this.setTransactions(transactions);
  }

  /**
   * Refreshes the transaction list currently displayed in the view.
   */
  private void refreshTransactions() {
    VBox transactionList = this.createTransactionList(this.controller.getProcessedTransactions());
    this.root.setCenter(transactionList);
  }

  /**
   * Displays the given transactions in the view.
   *
   * @param transactions the transactions to display
   */
  public void setTransactions(List<Transaction> transactions) {
    this.controller.setTransactions(transactions);
    this.refreshTransactions();
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
        this.controller.createTransactionRow(transactions.get(i),
            i
        )
    ));

    return list;
  }

  /**
   * Creates the top bar containing search, filter, sort, and direction controls.
   *
   * @return the top bar node
   */
  private Parent createTopBar() {
    TextField searchField = new TextField();
    searchField.setPromptText("Search transactions...");
    searchField.getStyleClass().add("transaction-search");

    searchField.textProperty().addListener((_, _, query) -> {
      this.controller.setCurrentSearch(query);
      this.refreshTransactions();
    });

    Label sortLabel = new Label("Sort by:");
    sortLabel.getStyleClass().add("sort-label");

    Button directionButton = new Button("↑");
    directionButton.getStyleClass().add("transaction-direction");

    directionButton.setOnAction(_ -> {
      this.controller.toggleAscending();
      directionButton.setText(this.controller.isAscending() ? "↑" : "↓");
      this.refreshTransactions();
    });

    HBox topBar = new HBox(12);
    topBar.setAlignment(Pos.CENTER_LEFT);
    topBar.getStyleClass().add("transaction-topbar");

    topBar.getChildren().addAll(
        searchField,
        this.createFilterButton("All"),
        this.createFilterButton("Purchases"),
        this.createFilterButton("Sales"),
        sortLabel,
        this.createSortButton("Week"),
        this.createSortButton("Price"),
        this.createSortButton("Quantity"),
        this.createSortButton("Total"),
        directionButton
    );

    return topBar;
  }

  /**
   * Creates a button used to sort the displayed transactions.
   *
   * @param text the sort criterion shown on the button
   * @return a sort button
   */
  private Button createSortButton(String text) {
    Button button = new Button(text);
    button.getStyleClass().add("transaction-sort-button");

    button.setOnAction(_ -> {
      this.controller.setCurrentSort(text);
      this.refreshTransactions();
    });

    return button;
  }

  /**
   * Creates a button used to filter the displayed transactions by type.
   *
   * @param text the transaction type filter shown on the button
   * @return a filter button
   */
  private Button createFilterButton(String text) {
    Button button = new Button(text);
    button.getStyleClass().add("transaction-sort-button");

    button.setOnAction(_ -> {
      this.controller.setCurrentTypeFilter(text);
      this.refreshTransactions();
    });

    return button;
  }

  /**
   * Returns the root node of the layout.
   *
   * @return the root layout node
   */
  @Override
  public Parent getRoot() {
    return root;
  }

}
