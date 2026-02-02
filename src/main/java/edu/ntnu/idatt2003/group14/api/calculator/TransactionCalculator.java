package edu.ntnu.idatt2003.group14.api.calculator;

import java.math.BigDecimal;

/**
 * This interface defines the methods for calculating the cost of a transaction.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public interface TransactionCalculator {
  /**
   * Calculates the gross cost of a transaction.
   *
   * @return the gross cost
   */
  BigDecimal calculateGross();

  /**
   * Calculates the commission of a transaction.
   *
   * @return the commission amount based on the gross cost
   */
  BigDecimal calculateCommission();

  /**
   * Calculates the tax of a transaction.
   *
   * @return the calculated tax or {@code BigDecimal.ZERO} if no tax applies
   */
  default BigDecimal calculateTax() {
    return BigDecimal.ZERO;
  }

  /**
   * Calculates the total cost of a transaction.
   *
   * <p>The total cost of a transaction varies based on the type of calculator.</p>
   *
   * @return the total cost of the transaction
   */
  BigDecimal calculateTotal();
}