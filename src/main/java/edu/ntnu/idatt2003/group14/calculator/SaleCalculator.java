package edu.ntnu.idatt2003.group14.calculator;

import edu.ntnu.idatt2003.group14.api.calculator.TransactionCalculator;
import edu.ntnu.idatt2003.group14.model.Share;
import java.math.BigDecimal;

/**
 * Represents the class which is responsible for calculating the
 * total cost of a sale based on the share's purchase price, sales price and quantity.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public final class SaleCalculator implements TransactionCalculator {
  private final BigDecimal commissionRate = BigDecimal.valueOf(0.01); // 1% commission rate
  private final BigDecimal taxRate = BigDecimal.valueOf(0.3); // 30% tax rate

  private final BigDecimal purchasePrice;
  private final BigDecimal salesPrice;
  private final BigDecimal quantity;

  /**
   * Instantiates a new sale calculator.
   *
   * @param share the share to calculate the sale cost for
   */
  public SaleCalculator(Share share) {
    this.purchasePrice = share.getPurchasePrice();
    this.salesPrice = share.getStock().getSalesPrice();
    this.quantity = share.getQuantity();
  }

  @Override
  public BigDecimal calculateGross() {
    return this.salesPrice.multiply(this.quantity);
  }

  @Override
  public BigDecimal calculateCommission() {
    return this.calculateGross().multiply(this.commissionRate);
  }

  /**
   * Calculates the tax of a sale.
   *
   * <p>The profit is defined as:</p>
   * <pre>
   *   profit = gross - commission - purchaseCost
   *   purchaseCost = purchasePrice * quantity
   * </pre>
   *
   * <p>The tax is calculated as:</p>
   * <pre>
   *   tax = profit * taxRate
   * </pre>
   *
   * @return the tax amount based on the profit
   */
  @Override
  public BigDecimal calculateTax() {
    BigDecimal profit = this.calculateGross()
        .subtract(this.calculateCommission())
        .subtract(this.purchasePrice.multiply(this.quantity));
    return profit.multiply(this.taxRate);
  }

  /**
   * Calculates the total cost of a sale.
   *
   * <p>The total cost is defined as:</p>
   * <pre>
   *   total = gross - commission - tax
   * </pre>
   *
   * @return the total cost of the sale
   */
  @Override
  public BigDecimal calculateTotal() {
    return this.calculateGross()
        .subtract(this.calculateCommission())
        .subtract(this.calculateTax());
  }
}
