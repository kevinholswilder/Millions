package edu.ntnu.idatt2003.group14.testutils;

import edu.ntnu.idatt2003.group14.model.Exchange;

/**
 * Exchange factory class.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public final class ExchangeFactory {

  private ExchangeFactory() {}

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
}
