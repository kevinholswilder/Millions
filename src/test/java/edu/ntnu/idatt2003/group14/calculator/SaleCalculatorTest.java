package edu.ntnu.idatt2003.group14.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class SaleCalculatorTest {

  private final BigDecimal testStockPrice = BigDecimal.valueOf(20);
  private final BigDecimal purchasePrice = BigDecimal.valueOf(1000);
  private final BigDecimal quantity = BigDecimal.valueOf(10);
  private SaleCalculator saleCalculator;

  @BeforeEach
  void set_up_tests() {
    Stock testStock = new Stock("tst", "test", List.of(testStockPrice));
    Share testShare = new Share(testStock, quantity, purchasePrice);
    saleCalculator = new SaleCalculator(testShare);
  }

  @Test
  void calculate_gross_calculates_the_expected_gross() {
    assertEquals(BigDecimal.valueOf(200), saleCalculator.calculateGross());
  }

  @Test
  void calculate_commission_calculates_the_expected_commission() {
    BigDecimal actualTotal = saleCalculator.calculateCommission();
    BigDecimal expectedTotal = BigDecimal.valueOf(2);
    assertEquals(expectedTotal.stripTrailingZeros(), actualTotal.stripTrailingZeros());
  }

  @Test
  void calculate_tax_calculates_the_expected_tax() {
    final BigDecimal taxRate = BigDecimal.valueOf(0.3); // 30% tax rate
    final BigDecimal profit = saleCalculator.calculateGross()
        .subtract(saleCalculator.calculateCommission())
        .subtract(purchasePrice.multiply(quantity));

    final BigDecimal expectedTax = profit.multiply(taxRate);
    assertEquals(expectedTax, saleCalculator.calculateTax());
  }

  @Test
  void calculate_total_calculates_the_expected_total() {
    BigDecimal actualTotal = saleCalculator.calculateTotal();
    BigDecimal expectedTotal = saleCalculator.calculateGross()
        .subtract(saleCalculator.calculateCommission())
        .subtract(saleCalculator.calculateTax());
    assertEquals(expectedTotal.stripTrailingZeros(), actualTotal.stripTrailingZeros());
  }
}
