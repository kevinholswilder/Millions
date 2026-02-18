package edu.ntnu.idatt2003.group14.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class PurchaseCalculatorTest {

  private PurchaseCalculator purchaseCalculator;

  @BeforeEach
  void set_up_tests() {
    Stock testStock = new Stock("tst", "test", List.of(BigDecimal.valueOf(10)));
    Share testShare = new Share(testStock, BigDecimal.valueOf(10), BigDecimal.valueOf(1000));
    purchaseCalculator = new PurchaseCalculator(testShare);
  }

  @Test
  void calculate_gross_calculates_the_expected_gross() {
    assertEquals(BigDecimal.valueOf(10000), purchaseCalculator.calculateGross());
  }

  @Test
  void calculate_commission_calculates_the_expected_commission() {
    BigDecimal actualTotal = purchaseCalculator.calculateCommission();
    BigDecimal expectedTotal = BigDecimal.valueOf(500);
    assertEquals(expectedTotal.stripTrailingZeros(), actualTotal.stripTrailingZeros());
  }

  @Test
  void calculate_tax_calculates_the_expected_tax() {
    assertEquals(BigDecimal.valueOf(0), purchaseCalculator.calculateTax());
  }

  @Test
  void calculate_total_calculates_the_expected_total() {
    BigDecimal actualTotal = purchaseCalculator.calculateTotal();
    BigDecimal expectedTotal = BigDecimal.valueOf(10500.00);
    assertEquals(expectedTotal.stripTrailingZeros(), actualTotal.stripTrailingZeros());
  }
}
