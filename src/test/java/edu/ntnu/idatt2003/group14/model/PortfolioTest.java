package edu.ntnu.idatt2003.group14.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class responsible for testing the {@link Portfolio} class.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class PortfolioTest {
  private Portfolio portfolio;

  @BeforeEach
  public void instantiate_portfolio() {
    portfolio = new Portfolio();
  }

  @Test
  public void verify_portfolio_is_empty() {
    Assertions.assertEquals(0, portfolio.getShares().size());
  }

  // TODO: Add more tests.
}
