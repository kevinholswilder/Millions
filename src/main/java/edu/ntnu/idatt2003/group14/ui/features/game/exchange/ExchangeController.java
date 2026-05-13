package edu.ntnu.idatt2003.group14.ui.features.game.exchange;

import edu.ntnu.idatt2003.group14.model.Money;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import edu.ntnu.idatt2003.group14.ui.app.Route;
import edu.ntnu.idatt2003.group14.ui.features.game.exchange.stock.PurchaseStockController;
import java.math.BigDecimal;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller responsible for creating and managing the exchange stock view.
 *
 * <p>The controller stores the current stock list, search query, sorting option,
 * and sorting direction.</p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class ExchangeController {
  private String currentSort = "Price";
  private boolean ascending = true;
  private String currentSearch = "";
  private List<Stock> stocks = List.of();
  private final AppRouter router;

  /**
   * Creates a new exchange controller.
   *
   * @param router the application router used for navigation
   */
  public ExchangeController(AppRouter router) {
    this.router = router;
  }

  /**
   * Creates a row displaying information about a {@link Stock}.
   *
   * <p>The row contains the stock symbol, company name, current price, latest
   * price change, highest price, lowest price, week number, and a purchase
   * button. Additionally, the row is styled differently depending on whether the index is even
   * or odd.</p>
   *
   * @param stock the stock to display
   * @param index the index of the stock row, used for alternating row styling
   * @return an {@link HBox} containing the formatted stock row
   */
  public HBox createStockRow(Stock stock, int index) {
    BigDecimal latestPrice = stock.getSalesPrice();
    BigDecimal highestPrice = stock.getHighestPrice();
    BigDecimal lowestPrice = stock.getLowestPrice();
    BigDecimal priceChange = stock.getLatestPriceChange();

    VBox stockBox = this.createInfoBox(
            stock.getSymbol(),
            stock.getCompany()
    );

    VBox priceBox = this.createInfoBox(
            "Price",
            "$" + Money.normalize(latestPrice).toPlainString()
    );

    VBox changeBox = this.createInfoBox(
            "Change",
            (priceChange.compareTo(BigDecimal.ZERO) >= 0 ? "+$" : "-$")
                    + Money.normalize(priceChange).abs().toPlainString()
    );

    VBox highestBox = this.createInfoBox(
            "High",
            "$" + Money.normalize(highestPrice).toPlainString()
    );

    VBox lowestBox = this.createInfoBox(
            "Low",
            "$" + Money.normalize(lowestPrice).toPlainString()
    );

    VBox weeksBox = this.createInfoBox(
            "Week",
            String.valueOf(stock.getWeek())
    );

    stockBox.getStyleClass().add("stock-company-column");
    priceBox.getStyleClass().add("stock-column");
    changeBox.getStyleClass().add("stock-column");
    highestBox.getStyleClass().add("stock-column");
    lowestBox.getStyleClass().add("stock-column");
    weeksBox.getStyleClass().add("stock-column");

    Button purchaseButton = this.createPurchaseButton(stock, router);

    HBox row = new HBox(24);

    row.getStyleClass().add("stock-row");
    row.getStyleClass().add(index % 2 == 0 ? "stock-row-even" : "stock-row-odd");

    row.getChildren().addAll(
            stockBox,
            priceBox,
            changeBox,
            highestBox,
            lowestBox,
            weeksBox,
            purchaseButton
    );

    return row;
  }

  /**
   * Creates an information box with a title and value.
   *
   * @param title the label title to display
   * @param value the value to display below the title
   * @return a styled {@link VBox} containing the title and value labels
   */
  private VBox createInfoBox(String title, String value) {
    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("stock-info-title");

    Label valueLabel = new Label(value);
    valueLabel.getStyleClass().add("stock-info-value");

    VBox box = new VBox(6);

    box.getStyleClass().add("stock-info-box");

    box.getChildren().addAll(titleLabel, valueLabel);

    return box;
  }

  /**
   * Creates a purchase button for the given stock.
   *
   * @param stock the stock associated with the purchase button
   * @return a styled {@link Button} for purchasing the stock
   */
  private Button createPurchaseButton(Stock stock, AppRouter router) {
    Button button = new Button("Purchase");

    button.getStyleClass().add("purchase-button");

    button.setOnAction(e -> {
      PurchaseStockController.getInstance().setStock(stock);
      router.navigate(Route.PURCHASE_STOCK);
    });

    return button;
  }

  public void setStocks(List<Stock> stocks) {
    this.stocks = stocks;
  }

  public void setCurrentSearch(String currentSearch) {
    this.currentSearch = currentSearch;
  }

  /**
   * Sets the current sorting option.
   *
   * @param currentSort the sorting option to use
   */
  public void setCurrentSort(String currentSort) {
    this.currentSort = currentSort;
  }

  /**
   * Toggles the current sorting direction between ascending and descending.
   */
  public void toggleAscending() {
    this.ascending = !this.ascending;
  }

  /**
   * Returns whether the current sorting direction is ascending.
   *
   * @return {@code true} if sorting is ascending, otherwise {@code false}
   */
  public boolean isAscending() {
    return ascending;
  }

  /**
   * Checks whether a stock matches the current search query.
   *
   * @param stock the stock to check
   * @return {@code true} if the stock symbol or company name contains the search query
   */
  private boolean matches(Stock stock) {
    String query = currentSearch.toLowerCase();

    return stock.getSymbol().toLowerCase().contains(query)
            || stock.getCompany().toLowerCase().contains(query);
  }

  /**
   * Compares two stocks using the current sorting option and sorting direction.
   *
   * @param a the first stock
   * @param b the second stock
   * @return a negative value, zero, or a positive value depending on the sort order
   */
  private int compare(Stock a, Stock b) {
    int result = switch (currentSort) {
      case "Price" -> a.getSalesPrice()
              .compareTo(b.getSalesPrice());

      case "A-Z" -> a.getSymbol()
              .compareToIgnoreCase(b.getSymbol());

      default -> Integer.compare(a.getWeek(), b.getWeek());
    };
    return ascending ? result : -result;
  }

  /**
   * Returns the stocks after applying the current search filter and sorting option.
   *
   * @return a filtered and sorted list of stocks
   */
  public List<Stock> getProcessedStocks() {
    return this.stocks.stream()
            .filter(this::matches)
            .sorted(this::compare)
            .toList();
  }

}
