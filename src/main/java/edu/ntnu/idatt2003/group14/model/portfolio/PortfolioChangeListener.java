package edu.ntnu.idatt2003.group14.model.portfolio;

import java.math.BigDecimal;

/**
 * Interface for objects that listen to changes in a {@link Portfolio}.
 * Implementing classes will be notified whenever the portfolio's net worth changes.
 */
public interface PortfolioChangeListener {

  /**
   * Called when the portfolio's net worth changes.
   * Implementing classes should update their state or UI accordingly.
   *
   * @param netWorth the new net worth of the portfolio
   */
  void portfolioChanged(BigDecimal netWorth);
}
