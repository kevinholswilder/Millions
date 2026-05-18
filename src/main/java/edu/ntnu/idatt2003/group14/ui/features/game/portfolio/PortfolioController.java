package edu.ntnu.idatt2003.group14.ui.features.game.portfolio;

import edu.ntnu.idatt2003.group14.config.lang.LangConfig;
import edu.ntnu.idatt2003.group14.logging.AppLogger;
import edu.ntnu.idatt2003.group14.model.Money;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.model.PlayerStatus;
import edu.ntnu.idatt2003.group14.model.Portfolio;
import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.model.plottable.PlottableChangeListener;
import edu.ntnu.idatt2003.group14.service.GameService;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import edu.ntnu.idatt2003.group14.ui.app.Route;
import edu.ntnu.idatt2003.group14.ui.features.game.exchange.stock.PurchaseStockController;
import edu.ntnu.idatt2003.group14.ui.features.game.portfolio.stock.SellShareController;
import java.math.BigDecimal;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller class for {@link PortfolioView}. Provides live updated JavaFX components to display.
 *
 * <p>This class implements {@link PlottableChangeListener} and should be assigned to the
 * {@link Player}s portfolio when a player is assigned.</p>
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
 */
public class PortfolioController implements PlottableChangeListener {
  private final GameService gameService;
  private final AppRouter appRouter;
  private final ObservableList<Share> shares = FXCollections.observableArrayList();
  private final Portfolio portfolio;

  private ImageView statusImageView;
  private Label netWorthLabel;
  private Label cashLabel;
  private Label portfolioValueLabel;

  private PlayerStatus previousStatus;

  /**
   * Initiates a new PortfolioController.
   *
   * @param gameService the game service providing player and portfolio data
   * @param appRouter the application router used for navigation
   */
  public PortfolioController(GameService gameService, AppRouter appRouter) {
    this.gameService = gameService;
    this.portfolio = gameService.getPlayer().getPortfolio();
    this.appRouter = appRouter;
    this.portfolio.addListener(this);
    this.plottableChanged(null);
    this.previousStatus = gameService.getPlayer().getStatus();
  }

  @Override
  public void plottableChanged(BigDecimal newValue) {
    Platform.runLater(() -> {
      shares.setAll(List.copyOf(portfolio.getShares()));

      BigDecimal cash = gameService.getPlayer().getMoney();
      BigDecimal portfolioValue = portfolio.getValueForWeek(portfolio.getWeek());
      BigDecimal netWorth = cash.add(portfolioValue);

      if (gameService.getPlayer().getStatus() != this.previousStatus) {
        this.previousStatus = gameService.getPlayer().getStatus();
        updateStatusImage();
      }

      if (netWorthLabel != null) {
        netWorthLabel.setText(
                LangConfig.getInstance().lang("portfolio-menu.net_worth")
                        + Money.normalize(netWorth)
        );
      }

      if (cashLabel != null) {
        cashLabel.setText(
                LangConfig.getInstance().lang("portfolio-menu.cash")
                        + Money.normalize(cash)
        );
      }

      if (portfolioValueLabel != null) {
        portfolioValueLabel.setText(
                LangConfig.getInstance().lang("portfolio-menu.portfolio")
                        + Money.normalize(portfolioValue)
        );
      }
    });
  }

  /**
   * Returns the player's portfolio.
   *
   * @return the player's {@link Portfolio}
   */
  public Portfolio getPortfolio() {
    return this.portfolio;
  }

  /**
   * Get all the stocks in the portfolio.
   *
   * @return the list of {@link Stock}'s
   */
  public List<Stock> getPortfolioStockList() {
    return this.portfolio.getShares().stream()
        .map(Share::getStock)
        .toList();
  }

  public ObservableList<Share> getShares() {
    return this.shares;
  }

  /**
   * Sets the labels in the topbar.
   *
   * <p>Called by the view to let the controller update the data in the labels.</p>
   *
   * @param netWorthLabel displaying the players net worth
   * @param cashLabel displaying the players cash
   * @param portfolioLabel displaying the value of the portfolio
   */
  public void setTopBarLabels(Label netWorthLabel, Label cashLabel, Label portfolioLabel) {
    this.netWorthLabel = netWorthLabel;
    this.cashLabel = cashLabel;
    this.portfolioValueLabel = portfolioLabel;
  }

  /**
   * Sets the image in the topbar.
   *
   * <p>Called by the view to let the controller update the image.</p>
   *
   * @param imageView the image
   */
  public void setStatusImageView(ImageView imageView) {
    this.statusImageView = imageView;
    updateStatusImage();
  }

  private void updateStatusImage() {
    if (statusImageView == null) {
      return;
    }
    // Image naming must match NOVICE.png, INVESTOR.png, SPECULATOR.png
    String path = "/images/icons/" + gameService.getPlayer().getStatus().name() + ".png";

    var resource = getClass().getResourceAsStream(path);
    if (resource != null) {
      statusImageView.setImage(new Image(resource));
    } else {
      AppLogger.error("Could not find image: " + path);
    }
  }

  /**
   * Opens the purchase stock dialog.
   *
   * @param stock the stock to purchase
   */
  public void buyStock(Stock stock) {
    PurchaseStockController.getInstance().setStock(stock);
    appRouter.navigate(Route.PURCHASE_STOCK);
  }

  /**
   * Opens the sell share dialog.
   *
   * @param share the share to sell
   */
  public void sellShare(Share share) {
    SellShareController.getInstance().setShare(share);
    appRouter.navigate(Route.SELL_STOCK);
  }
}
