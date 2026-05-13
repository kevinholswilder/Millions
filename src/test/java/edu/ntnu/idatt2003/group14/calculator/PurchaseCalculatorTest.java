package edu.ntnu.idatt2003.group14.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.testutils.StockFactory;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class PurchaseCalculatorTest {
  private final BigDecimal quantity = new BigDecimal("3");
  private PurchaseCalculator purchaseCalculator;

  @BeforeEach
  void set_up_tests() {
    Stock stock = StockFactory.createStock();
    Share share = new Share(stock, quantity, stock.getSalesPrice());
    purchaseCalculator = new PurchaseCalculator(share);
  }

  @Test
  void calculate_gross_calculates_the_expected_gross() {
    // 5 × 3 = 15
    assertEquals(new BigDecimal("15"), purchaseCalculator.calculateGross());
  }

  @Test
  void calculate_commission_calculates_the_expected_commission() {
    // 0.5% of 15 = 0.075
    BigDecimal expected = new BigDecimal("0.075");
    assertEquals(expected.stripTrailingZeros(),
        purchaseCalculator.calculateCommission().stripTrailingZeros());
  }

  @Test
  void calculate_tax_calculates_the_expected_tax() {
    assertEquals(BigDecimal.ZERO, purchaseCalculator.calculateTax());
  }

  @Test
  void calculate_total_calculates_the_expected_total() {
    // commission 0.5%
    // 0 tax
    // 15 + 15 * 0.005 = 15.075
    BigDecimal expected = new BigDecimal("15.075");
    assertEquals(expected.stripTrailingZeros(),
        purchaseCalculator.calculateTotal().stripTrailingZeros());
  }
}
