package edu.ntnu.idatt2003.group14.ui.components;

import edu.ntnu.idatt2003.group14.model.Stock;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
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
public class StockMoversComponent extends VBox {

  /**
   * Represents the type of stock movement to display.
   */
  public enum Type { GAINERS, LOSERS }

  /**
   * Creates a new component for displaying stock movers of a given type.
   *
   * @param stocks the list of stocks to display
   * @param type the type of stock movement to display
   */
  public StockMoversComponent(List<Stock> stocks, Type type) {
    super();

    this.setMaxSize(200, 150);
    this.setSpacing(10);
    this.setPadding(new Insets(12));
    this.getStyleClass().add("movers-component");

    for (Stock stock : stocks) {
      HBox row = new HBox();
      row.setSpacing(10);
      row.getStyleClass().add("movers-row");

      Label arrow = new Label(type == Type.GAINERS ? "▲" : "▼");
      String arrowColor = type == Type.GAINERS ? "green" : "red";
      arrow.getStyleClass().add(arrowColor + "-movers-arrow");

      Label symbol = new Label(stock.getSymbol());
      symbol.getStyleClass().add("movers-symbol");

      Label percent = new Label(String.format("+%.2f%%",
          stock.getLatestPriceChange().abs()
              .divide(
                  stock.getSalesPrice().subtract(stock.getLatestPriceChange()).abs(),
                  4,
                  RoundingMode.HALF_UP
              ).multiply(BigDecimal.valueOf(100))
              .doubleValue()));
      percent.getStyleClass().add("movers-percent");

      row.getChildren().addAll(arrow, symbol, percent);
      this.getChildren().add(row);
    }
  }
}