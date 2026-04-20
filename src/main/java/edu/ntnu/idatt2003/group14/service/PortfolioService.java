package edu.ntnu.idatt2003.group14.service;

import edu.ntnu.idatt2003.group14.model.portfolio.Portfolio;

/**
 * Gives the portfolio controller a way to interact with the rest of the application.
 */
public class PortfolioService {
  private final Portfolio portfolio;

  /**
   * Initializes a new PortfolioService.
   *
   * @param portfolio the portfolio object the PortfolioService handles
   */
  public PortfolioService(Portfolio portfolio) {
    this.portfolio = portfolio;
  }

  public Portfolio getPortfolio() {
    return this.portfolio;
  }
}
