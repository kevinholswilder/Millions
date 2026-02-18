package edu.ntnu.idatt2003.group14.model;

import edu.ntnu.idatt2003.group14.model.transaction.Purchase;
import edu.ntnu.idatt2003.group14.model.transaction.Sale;
import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an exchange for users to buy and sell stocks.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class Exchange {
  private final String name;
  private int week;
  private final Map<String, Stock> stockMap;
  private final Random random;

  /**
   * Initiates a new exchange.
   *
   * @param name the name of the exchange
   * @param stocks the list of stocks being traded on the exchange
   */
  public Exchange(String name, List<Stock> stocks) {
    this.name = name;
    this.week = 0;
    this.random = new Random();
    this.stockMap = new HashMap<>();

    for (Stock stock : stocks) {
      this.stockMap.put(stock.getSymbol(), stock);
    }
  }

  public String getName() {
    return this.name;
  }

  public int getWeek() {
    return week;
  }

  /**
   * Check to see if a stock with a given symbol exists in this exchange.
   *
   * @param symbol the symbol to check (e.g. "AAPL" for "Apple Inc.")
   * @return returns true if the stock exists, false if not
   */
  public boolean hasStock(String symbol) {
    return stockMap.containsKey(symbol);
  }

  /**
   * Returns the stock associated with a given symbol.
   *
   * @param symbol the symbol to the stock the method should return (e.g. "AAPL" for "Apple Inc.")
   * @return returns the stock
   */
  public Stock getStock(String symbol) {
    // TODO: check to see if the stock exists. Probably using: hasStock("symbol");
    return stockMap.get(symbol);
  }

  /**
   * Search through all stocks in this exchange.
   *
   * <p>The search applies to company names and symbols. The search will return
   * a {@code List} of {@link Stock}s matching the search term.<br>
   * A search for <b>go</b> would, as an example, return both
   * "<b>GO</b>OGL – Alphabet Inc" and «WFC – Wells Far<b>go</b>»</p>
   *
   * @param searchTerm the string to search for
   * @return a list of all stocks that match the search in this exchange
   */
  public List<Stock> findStock(String searchTerm) {
    // Normalize the user input, the company name and symbol of the
    // stock using private normalize method.

    List<Stock> stockList = new ArrayList<>();

    // TODO: improving the search algorithm (maybe moving to Streams?)
    for (Stock stock : this.stockMap.values()) {
      String normalSearch = normalizeString(searchTerm);
      String normalSymbol = normalizeString(stock.getSymbol());
      String normalCompany = normalizeString(stock.getCompany());

      if (normalCompany.contains(normalSearch)
          || normalSymbol.contains(normalSearch)) {
        stockList.add(stock);
      }
    }
    return stockList;
  }

  /**
   * A player purchases a share.
   *
   * @param symbol the symbol of the stock to purchase
   * @param quantity the amount of the stock to purchase
   * @param player the player who is purchasing
   * @return the transaction containing the purchase
   */
  public Transaction buy(String symbol, BigDecimal quantity, Player player) {
    // TODO: Does the stock exist in this exchange?
    // TODO: Does the player have enough funds?
    Stock stock = stockMap.get(symbol);
    BigDecimal purchasePrice = stock.getSalesPrice();
    Share share = new Share(stock, quantity, purchasePrice);
    Purchase purchase = new Purchase(share, this.week);
    purchase.commit(player);
    return purchase;
  }

  /**
   * A player shells a share.
   *
   * @param share the share to sell
   * @param player the player who is selling
   * @return the transaction containing the sold share
   */
  public Transaction sell(Share share, Player player) {
    Sale sale = new Sale(share, this.week);
    sale.commit(player);
    return sale;
  }

  /**
   * Moves the current week one forwards, resulting in new stock prices.
   */
  public void advance() {
    // TODO: add a more realistic market movement

    double volatility = 0.05; // Stock can only move 5% per week

    for (Stock stock : stockMap.values()) {
      BigDecimal price = stock.getSalesPrice();
      /*
      (random * 2 - 1) gives a random double form (-1) to 1.
      Multiplication with volatility ensures the movements are inside a given range.
      Over time, the market will decrease slowly:
      (10% gain -> 10% fall) 10 * 1.1 = 11 * 0.9 = 0.99
      */
      double change = 1 + (random.nextDouble() * 2 - 1) * volatility;
      stock.addNewSalesPrice(price.multiply(BigDecimal.valueOf(change)));
    }
    this.week++;
  }

  private String normalizeString(String string) {
    return string.toLowerCase().trim().replaceAll(" ", "");
  }
}
