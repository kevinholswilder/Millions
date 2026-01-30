package edu.ntnu.idatt2003.group14.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a stock of a company.
 *
 * <p>Holds Symbol, company name and price history.</p>
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

  /**
   * Adds the updated price to the end of prices list.
   *
   * @param price the updated price
   */
  public void addNewSalesPrice(BigDecimal price) {
    this.prices.add(price);
  }
}
