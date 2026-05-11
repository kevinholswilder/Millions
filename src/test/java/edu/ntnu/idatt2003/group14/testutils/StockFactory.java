package edu.ntnu.idatt2003.group14.testutils;

import edu.ntnu.idatt2003.group14.model.Stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Stock factory class.
 * <p>Returns either one stock or a list of stocks.<br>
 * <b>NOTE</b>: The methods generates the same stocks every time</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public final class StockFactory {
  private static final String[] WORD_ONE = {"Amazing", "Nordic", "Luxurious"};
  private static final String[] WORD_TWO  = {"Chairs", "Cars", "Homes"};

  private static final List<BigDecimal> PRICES = List.of(
      new BigDecimal("1"),
      new BigDecimal("2"),
      new BigDecimal("3"),
      new BigDecimal("4"),
      new BigDecimal("5")
  );

  private StockFactory() {}

  /**
   * <pre>
   * Creates a new stock with the values:
   * symbol: "AMCH"
   * company: "Amazing Chairs"
   * prices: {1, 2, 3, 4, 5}
   * </pre>
   *
   * @return a new stock
   */
  public static Stock createStock() {
    return createStock(0);
  }

  /**
   * <pre>
   * Creates a list of n new stocks.
   * Names cycle through combinations of two lists of words:
   * 0: AMCH – Amazing Chairs
   * 1: AMCA – Amazing Cars
   * 2: AMHO – Amazing Homes
   * 3: CHCH – Nordic Chairs
   * ...
   * prices: {1, 2, 3, 4, 5}
   * </pre>
   *
   * @param count the number of stocks in the list (max 9)
   * @return a list of new stocks
   */
  public static List<Stock> createStocks(int count) {
    List<Stock> stocks = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      stocks.add(createStock(i));
    }
    return stocks;
  }

  /**
   * Creates a new stock.
   *
   * @param symbol the stock symbol
   * @param name the stock name
   * @param prices the prices of the stock
   * @return a new stock
   */
  public static Stock createStock(String symbol, String name, List<BigDecimal> prices) {
    return new Stock(symbol, name, new ArrayList<>(prices));
  }

  private static Stock createStock(int i) {
    String w1 = WORD_ONE[i / WORD_TWO.length % WORD_ONE.length];
    String w2 = WORD_TWO[i % WORD_TWO.length];
    String symbol = w1.substring(0, 2).toUpperCase() + w2.substring(0, 2).toUpperCase();
    return new Stock(symbol, w1 + " " + w2, new ArrayList<>(PRICES));
  }
}
