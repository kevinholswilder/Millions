package edu.ntnu.idatt2003.group14.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Represents a stock of a company.
 *
 * <p>Holds Symbol, company name and price history.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class Stock {
  private final String symbol;
  private final String company;
  private final List<BigDecimal> prices;

  /**
   * Defines a new stock.
   *
   * @param symbol the company symbol (e.g. "AAPL" for "Apple Inc.")
   * @param company the name of the company
   * @param prices a list of price history
   */
  public Stock(String symbol, String company, List<BigDecimal> prices) {
    this.symbol = symbol;
    this.company = company;
    this.prices = prices;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public String getCompany() {
    return this.company;
  }

  public BigDecimal getSalesPrice() {
    return this.prices.getLast();
  }

  public List<BigDecimal> getHistoricalPrices() {
    return this.prices;
  }

  public BigDecimal getHighestPrice() {
    return Collections.max(this.prices);
  }

  public BigDecimal getLowestPrice() {
    return Collections.min(this.prices);
  }

  /**
   * Returns the difference between the latest price and the second to last price.
   *
   * @return 0 if there are less than 2 prices, otherwise the difference
   */
  public BigDecimal getLatestPriceChange() {
    if (this.prices.size() < 2) {
      return BigDecimal.ZERO;
    }
    return this.prices.getLast().subtract(this.prices.get(this.prices.size() - 2));
  }

  /**
   * Adds the updated price to the end of prices list.
   *
   * @param price the updated price
   */
  public void addNewSalesPrice(BigDecimal price) {
    this.prices.add(price);
  }
}
