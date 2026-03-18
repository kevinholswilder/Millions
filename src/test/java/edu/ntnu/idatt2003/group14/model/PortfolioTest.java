package edu.ntnu.idatt2003.group14.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class responsible for testing the {@link Portfolio} class.
 *
 * @author Kevin Holswilder, Elias Haugsbakk
 * @since 0.0.1
 */
public class PortfolioTest {
  Portfolio portfolio;
  @BeforeEach
  public void instantiate_portfolio() {
    portfolio = new Portfolio();
  }

  @Test
  void removeShare_removes_the_share() {
    List<BigDecimal> stockValues = new ArrayList<>();
    stockValues.add(new BigDecimal("150"));

    Stock testStock = new Stock("tst", "Test Inc.", stockValues);
    Share share = new Share(testStock, new BigDecimal("1"), new BigDecimal("100"));
    portfolio.addShare(share);

    assertEquals(1, portfolio.getShares().size());

    portfolio.removeShare(share);

    assertEquals(0, portfolio.getShares().size());

  }

  @Test
  void getShares_returns_the_shares() {
    List<BigDecimal> stockValues = new ArrayList<>();
    stockValues.add(new BigDecimal("150"));

    Stock testStock = new Stock("tst", "Test Inc.", stockValues);
    Share share1 = new Share(testStock, new BigDecimal("1"), new BigDecimal("100"));
    Share share2 = new Share(testStock, new BigDecimal("2"), new BigDecimal("100"));
    portfolio.addShare(share1);
    portfolio.addShare(share2);

    assertEquals(2, portfolio.getShares().size());
    assertEquals(share1, portfolio.getShares("tst").get(0));
    assertEquals(share2, portfolio.getShares("tst").get(1));
  }

  @Test
  void contains_check_if_the_share_is_in_the_portfolio() {
    List<BigDecimal> stockValues = new ArrayList<>();
    stockValues.add(new BigDecimal("150"));

    Stock testStock = new Stock("tst", "Test Inc.", stockValues);
    Share share = new Share(testStock, new BigDecimal("1"), new BigDecimal("100"));
    Share share2 = new Share(testStock, new BigDecimal("1"), new BigDecimal("100"));
    portfolio.addShare(share);

    assertTrue(portfolio.contains(share));
    assertFalse(portfolio.contains(share2));
  }

  @Test
  void getNetWorth_returns_the_sales_value_of_all_shares_in_the_portfolio() {
    List<BigDecimal> stockValues = new ArrayList<>();
    stockValues.add(new BigDecimal("150"));

    Stock testStock = new Stock("tst", "Test Inc.", stockValues);
    portfolio.addShare(new Share(testStock, new BigDecimal("1"), new BigDecimal("100")));

    /*
   gross = 150 × 1 = 150
   commission = 1% of gross = 150 × 0.01 = 1.5
   profit = 150 − 1.5 − 100 = 48.5
   tax = 30% of profit = 48.5 × 0.30 = 14.55
   total = 150 − 1.5 − 14.55 = 133.95
     */
    BigDecimal expected = new BigDecimal("133.95");

    BigDecimal actual = portfolio.getNetWorth();

    assertEquals(0, expected.compareTo(actual));
  }
}
