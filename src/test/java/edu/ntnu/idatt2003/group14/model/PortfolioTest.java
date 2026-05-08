package edu.ntnu.idatt2003.group14.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2003.group14.model.portfolio.Portfolio;
import edu.ntnu.idatt2003.group14.testutils.ShareFactory;
import java.math.BigDecimal;
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
  Share share;

  @BeforeEach
  public void instantiate_portfolio() {
    portfolio = new Portfolio();
    share = ShareFactory.createShare();
    portfolio.addShare(share);
  }

  @Test
  void removeShare_removes_the_share() {
    assertEquals(1, portfolio.getShares().size());

    portfolio.removeShare(share);

    assertEquals(0, portfolio.getShares().size());
  }

  @Test
  void getShares_returns_the_shares() {
    var shares = ShareFactory.createShares(4);
    shares.removeFirst();

    for (Share s : shares) {
      portfolio.addShare(s);
    }

    assertEquals(4, portfolio.getShares().size());

    assertEquals(1, portfolio.getShares("AMCH").size());
  }

  @Test
  void is_the_share_in_the_portfolio() {
    var shares = ShareFactory.createShares(4);

      portfolio.addShare(shares.getFirst());

    assertTrue(portfolio.contains(share));
    assertTrue(portfolio.contains(shares.getFirst()));
    assertFalse(portfolio.contains(shares.getLast()));
  }

  // TODO: Should the net worth return the sales value (including fees), or just the raw value
  @Test
  void getNetWorth_returns_the_sales_value_of_all_shares_in_the_portfolio() {

    /*
     stock: Amazing Chairs, latest price = 5
     quantity: 3
     purchasePrice (per share): 5

     gross      = 5 × 3 = 15
     commission = 1% of 15 = 0.15
     profit     = 15 − 0.15 − (5 × 3) = −0.15  // no profit -> no tax
     total      = 15 − 0.15 − 0 = 14.85
    */
    BigDecimal expected = new BigDecimal("14.85");

    BigDecimal actual = portfolio.getNetWorth();

    assertEquals(0, expected.compareTo(actual));
  }
}
