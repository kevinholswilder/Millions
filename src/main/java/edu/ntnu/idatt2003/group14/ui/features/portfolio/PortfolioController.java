package edu.ntnu.idatt2003.group14.ui.features.portfolio;

import edu.ntnu.idatt2003.group14.model.Portfolio;
import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.service.PortfolioService;
import java.util.List;

/**
 * Controller class for handling user input from the Portfolio scene.
 *
 * <p>Relies on {@link PortfolioService} to interact with the other parts of the application.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class PortfolioController {
  private final PortfolioService portfolioService;

  /**
   * Initializes a new PortfolioController.
   *
   * @param portfolioService the service class to interact with the rest of the application
   */
  public PortfolioController(PortfolioService portfolioService) {
    this.portfolioService = portfolioService;
  }

  public Portfolio getPortfolio() {
    return portfolioService.getPortfolio();
  }

  /**
   * Get all the stocks in the portfolio.
   *
   * @return the list of {@link Stock}'s
   */
  public List<Stock> getPortfolioStockList() {
    return portfolioService.getPortfolio().getShares().stream()
        .map(Share::getStock)
        .toList();
  }
}
