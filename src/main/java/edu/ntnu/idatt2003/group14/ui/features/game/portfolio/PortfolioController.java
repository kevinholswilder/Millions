package edu.ntnu.idatt2003.group14.ui.features.game.portfolio;

import edu.ntnu.idatt2003.group14.model.GameSession;
import edu.ntnu.idatt2003.group14.model.Portfolio;
import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import java.util.List;

/**
 * Controller class for handling user input from the Portfolio scene.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class PortfolioController {
  Portfolio playerPortfolio;

  /**
   * Initializes a new PortfolioController.
   */
  public PortfolioController() {
    playerPortfolio = GameSession.getPlayer().getPortfolio();
  }

  /**
   * Returns the player's portfolio.
   *
   * @return the player's {@link Portfolio}
   */
  public Portfolio getPortfolio() {
    return playerPortfolio;
  }

  /**
   * Get all the stocks in the portfolio.
   *
   * @return the list of {@link Stock}'s
   */
  public List<Stock> getPortfolioStockList() {
    return playerPortfolio.getShares().stream()
        .map(Share::getStock)
        .toList();
  }
}
