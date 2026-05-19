package edu.ntnu.idatt2003.group14.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for handling monetary values.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public final class Money {

  private Money() {}

  /**
   * Normalizes a monetary amount to two decimal places.
   *
   * <p>Example usage:
   * <pre>
   * BigDecimal amount = new BigDecimal("9.456");
   * BigDecimal normalized = Money.normalize(amount);
   * // Result: 9.46
   * </pre>
   *
   * @param amount the amount to normalize
   * @return the normalized amount
   */
  public static BigDecimal normalize(BigDecimal amount) {
    return amount.setScale(2, RoundingMode.HALF_UP);
  }

}