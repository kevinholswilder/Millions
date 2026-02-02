package edu.ntnu.idatt2003.group14.calculator;

import edu.ntnu.idatt2003.group14.api.calculator.TransactionCalculator;
import edu.ntnu.idatt2003.group14.model.Share;
import java.math.BigDecimal;

/**
 * Represents the class which is responsible for calculating the
 * total cost of a purchase based on the share's purchase price and quantity.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public final class PurchaseCalculator implements TransactionCalculator {
  private final BigDecimal commissionRate = BigDecimal.valueOf(0.05); // 5% commission rate

  private final BigDecimal purchasePrice;
  private final BigDecimal quantity;

  /**
   * Instantiates a new purchase calculator.
   *
   * @param share the share to calculate the purchase cost for
   */
  public PurchaseCalculator(Share share) {
    this.purchasePrice = share.getPurchasePrice();
    this.quantity = share.getQuantity();
  }

  @Override
  public BigDecimal calculateGross() {
    return this.purchasePrice.multiply(this.quantity);
  }

  @Override
  public BigDecimal calculateCommission() {
    return this.calculateGross().multiply(this.commissionRate);
  }

  /**
   * Calculates the total cost of a purchase.
   *
   * <p>The total cost is defined as:</p>
   * <pre>
   *   total = gross + commission + tax
   * </pre>
   *
   * @return the total cost of the purchase
   */
  @Override
  public BigDecimal calculateTotal() {
    return this.calculateGross()
        .add(this.calculateCommission())
        .add(this.calculateTax());
  }
}