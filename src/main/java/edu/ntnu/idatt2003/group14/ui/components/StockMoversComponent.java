package edu.ntnu.idatt2003.group14.ui.components;

import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.model.plottable.PlottableChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Component which displays a list of stock movers, such as top gainers or top losers.
 *
 * <p>Each stock is presented in a horizontal row containing the following:</p>
 * <ul>
 *   <li>an array indicating whether the stock is a gainer or loser</li>
 *   <li>the stock symbol</li>
 *   <li>the percentage change in price</li>
 * </ul>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class StockMoversComponent extends VBox implements PlottableChangeListener {

  /**
   * Represents the type of stock movement to display.
   */
  public enum Type { GAINERS, LOSERS }

  private final List<Stock> stocks;
  private final Type type;

  /**
   * Creates a new component for displaying stock movers of a given type.
   *
   * @param stocks the list of stocks to display
   * @param type   the type of stock movement to display
   */
  public StockMoversComponent(List<Stock> stocks, Type type) {
    super();

    this.stocks = stocks;
    this.type = type;

    this.setSpacing(10);
    this.setPadding(new Insets(12));
    this.getStyleClass().add("movers-component");

    updateUI();
  }

  private void updateUI() {
    this.getChildren().clear();

    List<Stock> sortedStocks = stocks.stream()
        .filter(stock -> {
          BigDecimal change = calculatePercentChange(stock);
          if (type == Type.GAINERS) {
            return change.compareTo(BigDecimal.ZERO) > 0;
          } else {
            return change.compareTo(BigDecimal.ZERO) < 0;
          }
        })
        .sorted((s1, s2) -> {
          BigDecimal p1 = calculatePercentChange(s1);
          BigDecimal p2 = calculatePercentChange(s2);
          return type == Type.GAINERS ? p2.compareTo(p1) : p1.compareTo(p2);
        })
        .limit(5)
        .toList();

    for (Stock stock : sortedStocks) {
      this.getChildren().add(createMoverRow(stock));
    }
  }

  private HBox createMoverRow(Stock stock) {
    HBox row = new HBox(10);
    row.getStyleClass().add("movers-row");

    Label arrow = new Label(type == Type.GAINERS ? "▲" : "▼");
    arrow.getStyleClass().add((type == Type.GAINERS ? "green" : "red") + "-movers-arrow");

    Label symbol = new Label(stock.getSymbol());
    symbol.getStyleClass().add("movers-symbol");

    BigDecimal percentChange = calculatePercentChange(stock);
    Label percent = new Label(String.format("%.2f%%", percentChange.doubleValue()));
    percent.getStyleClass().add("movers-percent");

    row.getChildren().addAll(arrow, symbol, percent);
    return row;
  }

  private BigDecimal calculatePercentChange(Stock stock) {
    BigDecimal latestChange = stock.getLatestPriceChange();
    BigDecimal currentPrice = stock.getSalesPrice();
    BigDecimal previousPrice = currentPrice.subtract(latestChange);

    if (previousPrice.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }

    return latestChange.divide(previousPrice, 4, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(100));
  }

  @Override
  public void plottableChanged(BigDecimal value) {
    Platform.runLater(this::updateUI);
  }
}
