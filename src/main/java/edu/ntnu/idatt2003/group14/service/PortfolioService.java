package edu.ntnu.idatt2003.group14.service;

import edu.ntnu.idatt2003.group14.model.Portfolio;

// TODO: this service class should maybe be reworked to expand above just the
//  needs of the portfolio.
// A general GameService could be considered. Should probably wrap GameSession in some way and be
//  an access point for the controller classes; keeping the controllers thin.

/**
 * Gives the portfolio controller a way to interact with the rest of the application.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
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
