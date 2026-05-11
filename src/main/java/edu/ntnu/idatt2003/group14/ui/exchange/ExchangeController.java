package edu.ntnu.idatt2003.group14.ui.exchange;

import edu.ntnu.idatt2003.group14.model.Stock;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class ExchangeController {
  private String currentSort = "Price";
  private boolean ascending = true;
  private String currentSearch = "";
  private List<Stock> stocks = List.of();

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
            "$" + latestPrice.setScale(2, RoundingMode.HALF_UP).toPlainString()
    );

    VBox changeBox = this.createInfoBox(
            "Change",
            (priceChange.compareTo(BigDecimal.ZERO) >= 0 ? "+$" : "-$")
                    + priceChange.abs().setScale(2, RoundingMode.HALF_UP).toPlainString()
    );

    VBox highestBox = this.createInfoBox(
            "High",
            "$" + highestPrice.setScale(2, RoundingMode.HALF_UP).toPlainString()
    );

    VBox lowestBox = this.createInfoBox(
            "Low",
            "$" + lowestPrice.setScale(2, RoundingMode.HALF_UP).toPlainString()
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

    Button purchaseButton = this.createPurchaseButton(stock);

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

  private Button createPurchaseButton(Stock stock) {
    Button button = new Button("Purchase");

    button.getStyleClass().add("purchase-button");

    return button;
  }

  public void setStocks(List<Stock> stocks) {
    this.stocks = stocks;
  }

  public void setCurrentSearch(String currentSearch) {
    this.currentSearch = currentSearch;
  }

  public void setCurrentSort(String currentSort) {
    this.currentSort = currentSort;
  }

  public void toggleAscending() {
    this.ascending = !this.ascending;
  }

  public boolean isAscending() {
    return ascending;
  }

  private boolean matches(Stock stock) {
    String query = currentSearch.toLowerCase();

    return stock.getSymbol().toLowerCase().contains(query)
            || stock.getCompany().toLowerCase().contains(query);
  }

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


  public List<Stock> getProcessedStocks() {
    return this.stocks.stream()
            .filter(this::matches)
            .sorted(this::compare)
            .toList();
  }

}