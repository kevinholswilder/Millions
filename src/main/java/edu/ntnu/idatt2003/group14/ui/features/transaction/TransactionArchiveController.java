package edu.ntnu.idatt2003.group14.ui.features.transaction;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.model.transaction.Purchase;
import edu.ntnu.idatt2003.group14.model.transaction.Sale;
import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import java.math.BigDecimal;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller responsible for creating and styling transaction archive UI elements.
 *
 * <p>This controller generates visual rows representing transactions,
 * including its transaction type, quantity, price, total value, and week.</p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class TransactionArchiveController {
  private String currentSort = "Week";
  private boolean ascending = true;
  private String currentSearch = "";
  private List<Transaction> transactions = List.of();
  private String currentTypeFilter = "All";

  /**
   * Creates a row displaying transaction information.
   *
   * @param transaction the transaction to display
   * @param index the row index
   * @return a styled transaction row
   */
  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
  public HBox createTransactionRow(Transaction transaction, int index) {
    Share share = transaction.getShare();
    Stock stock = share.getStock();

    String transactionType = transaction instanceof Purchase ? "PURCHASE" : "SALE";

    BigDecimal quantity = share.getQuantity();
    BigDecimal price = share.getPurchasePrice();
    BigDecimal total = quantity.multiply(price);

    VBox typeBox = this.createInfoBox(
        transactionType,
        stock.getSymbol()
    );

    VBox quantityBox = this.createInfoBox(
        "Quantity",
        quantity.toPlainString() + " shares"
    );

    VBox priceBox = this.createInfoBox(
        "Price",
        "$" + price.toPlainString()
    );

    VBox totalBox = this.createInfoBox(
        "Total",
        "$" + total.toPlainString()
    );

    VBox weekBox = this.createInfoBox(
        "Week",
        String.valueOf(transaction.getWeek())
    );

    HBox row = new HBox(40);
    row.setAlignment(Pos.CENTER_LEFT);

    row.getStyleClass().add("transaction-row");
    row.getStyleClass().add(index % 2 == 0 ? "transaction-row-even" : "transaction-row-odd");

    row.getChildren().addAll(
        typeBox,
        quantityBox,
        priceBox,
        totalBox,
        weekBox
    );

    Label title = (Label) typeBox.getChildren().getFirst();

    if (transaction instanceof Purchase) {
      title.getStyleClass().add("purchase-label");
    } else {
      title.getStyleClass().add("sale-label");
    }

    return row;
  }

  /**
   * Creates a labeled info box.
   *
   * @param title the title text
   * @param value the value text
   * @return a styled info box
   */
  private VBox createInfoBox(String title, String value) {
    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("transaction-title");

    Label valueLabel = new Label(value);
    valueLabel.getStyleClass().add("transaction-value");

    VBox box = new VBox(4);
    box.getStyleClass().add("transaction-info-box");
    box.getChildren().addAll(titleLabel, valueLabel);

    return box;
  }

  /**
   * Sets the transactions managed by the controller.
   *
   * @param transactions the transactions to manage and display
   */
  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  /**
   * Sets the current search query used to filter transactions.
   *
   * @param currentSearch the search query
   */
  public void setCurrentSearch(String currentSearch) {
    this.currentSearch = currentSearch;
  }

  /**
   * Sets the current sorting criterion for displayed transactions.
   *
   * @param currentSort the sort criterion
   */
  public void setCurrentSort(String currentSort) {
    this.currentSort = currentSort;
  }

  /**
   * Toggles the sorting direction between ascending and descending order.
   */
  public void toggleAscending() {
    this.ascending = !this.ascending;
  }

  /**
   * Returns whether transactions are currently sorted in ascending order.
   *
   * @return {@code true} if sorting is ascending, otherwise {@code false}
   */
  public boolean isAscending() {
    return ascending;
  }

  /**
   * Sets the current transaction type filter.
   *
   * @param currentTypeFilter the transaction type filter
   */
  public void setCurrentTypeFilter(String currentTypeFilter) {
    this.currentTypeFilter = currentTypeFilter;
  }

  /**
   * Determines whether a transaction matches the current search
   * query and type filter based on either the {@link Stock}'s name or symbol.
   *
   * @param transaction the transaction that is evaluated
   * @return {@code true} if the transaction matches the active filters, otherwise {@code false}
   */
  private boolean matches(Transaction transaction) {
    String query = currentSearch.toLowerCase();

    Share share = transaction.getShare();
    Stock stock = share.getStock();

    boolean matchesText = stock.getSymbol().toLowerCase().contains(query)
        || stock.getCompany().toLowerCase().contains(query);

    boolean matchesType = switch (currentTypeFilter) {
      case "Purchases" -> transaction instanceof Purchase;
      case "Sales" -> transaction instanceof Sale;
      default -> true;
    };

    return matchesText && matchesType;
  }

  /**
   * Compares two transactions accordingly to the selected sorting type.
   *
   * @param a the first transaction
   * @param b the second transaction
   * @return a negative integer, zero, or a positive integer as the
   *         first transaction is less than, equal to, or greater than
   *         the second transaction
   */
  private int compare(Transaction a, Transaction b) {
    int result = switch (currentSort) {
      case "Price" -> a.getShare().getPurchasePrice()
              .compareTo(b.getShare().getPurchasePrice());

      case "Quantity" -> a.getShare().getQuantity()
              .compareTo(b.getShare().getQuantity());

      case "Total" -> a.getShare().getTotal()
              .compareTo(b.getShare().getTotal());

      default -> Integer.compare(a.getWeek(), b.getWeek());
    };

    return ascending ? result : -result;
  }

  /**
   * Returns a list of transaction objects according
   * to the currently selected type and filter.
   *
   * @return a filtered and sorted list of visible transactions
   */
  public List<Transaction> getProcessedTransactions() {
    return transactions.stream()
            .filter(this::matches)
            .sorted(this::compare)
            .toList();
  }

}
