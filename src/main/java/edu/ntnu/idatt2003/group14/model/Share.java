package edu.ntnu.idatt2003.group14.model;

import java.math.BigDecimal;

/**
 * Represents a purchase of stocks.
 *
 * <p>Class holds: one stock, quantity of the stock and the stock price at purchase.</p>
 *
 * @author Elias Haugsbakk, Kevin Holswilder
 * @since 0.0.1
 */
public final class Share {
  private final Stock stock;
  private final BigDecimal quantity;
  private final BigDecimal purchasePrice;

  /**
   * Defines a new share of stocks.
   *
   * @param stock a stock
   * @param quantity the number of stocks
   * @param purchasePrice the price per stock at purchase
   */
  public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
    this.stock = stock;
    this.quantity = quantity;
    this.purchasePrice = purchasePrice;
  }

  public Stock getStock() {
    return this.stock;
  }

  public BigDecimal getQuantity() {
    return this.quantity;
  }

  public BigDecimal getPurchasePrice() {
    return this.purchasePrice;
  }

  /**
   * Returns the total purchase value of a share.
   *
   * @return the total purchase value
   */
  public BigDecimal getTotal() {
    return this.getQuantity().multiply(this.getPurchasePrice());
  }

  /**
   * Returns the current total value of a share.
   *
   * @return the total share value
   */
  public BigDecimal getCurrentValue() {
    return this.getQuantity().multiply(this.getStock().getSalesPrice());
  }
}
