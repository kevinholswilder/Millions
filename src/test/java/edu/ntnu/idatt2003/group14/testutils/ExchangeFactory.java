package edu.ntnu.idatt2003.group14.testutils;

import edu.ntnu.idatt2003.group14.model.Exchange;
import java.math.BigDecimal;
import java.util.List;

/**
 * Exchange factory class.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public final class ExchangeFactory {

  private ExchangeFactory() {
  }

  /**
   * <pre>
   * Creates a new exchange with the values:
   * name: "New York Stock Exchange (NYSE)"
   * stocks: 9 stocks as defined by {@link StockFactory}.getStocks()
   *
   * The stocks are named AMCH, AMCA, AMHO, ...
   * </pre>
   *
   * @return a new exchange
   */
  public static Exchange createExchange() {
    return new Exchange(
        "New York Stock Exchange (NYSE)",
        StockFactory.createStocks(9)
    );
  }

  /**
   * <pre>
   * Creates a new exchange with non-uniform stock prices.
   *
   * name: "New York Stock Exchange (NYSE)"
   * stocks: three stocks named stk1, stk2 stk3
   *    values:
   *    stk1: {1, 2, 3}
   *    stk2: {2, 3, 4}
   *    stk3: {3, 4, 5}
   * </pre>
   *
   * @return a new exchange
   */
  public static Exchange createExchangeNonUniformPrices() {
    return new Exchange(
        "New York Stock Exchange (NYSE)",
        List.of(
            StockFactory.createStock("stk1", "stock1",
                List.of(
                    new BigDecimal("1"),
                    new BigDecimal("2"),
                    new BigDecimal("3"))
            ),
            StockFactory.createStock("stk2", "stock2",
                List.of(
                    new BigDecimal("2"),
                    new BigDecimal("3"),
                    new BigDecimal("4"))
            ),
            StockFactory.createStock("stk3", "stock3",
                List.of(
                    new BigDecimal("3"),
                    new BigDecimal("4"),
                    new BigDecimal("5")))
        )
    );
  }
}
