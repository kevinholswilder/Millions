package edu.ntnu.idatt2003.group14.ui.app;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.features.game.exchange.ExchangeController;
import edu.ntnu.idatt2003.group14.ui.features.game.exchange.ExchangeView;
import edu.ntnu.idatt2003.group14.ui.features.game.exchange.stock.PurchaseStockView;
import edu.ntnu.idatt2003.group14.ui.features.game.gamemenu.GameMenuController;
import edu.ntnu.idatt2003.group14.ui.features.game.gamemenu.GameMenuView;
import edu.ntnu.idatt2003.group14.ui.features.game.portfolio.PortfolioController;
import edu.ntnu.idatt2003.group14.ui.features.game.portfolio.PortfolioView;
import edu.ntnu.idatt2003.group14.ui.features.game.transaction.TransactionArchiveController;
import edu.ntnu.idatt2003.group14.ui.features.game.transaction.TransactionArchiveView;
import edu.ntnu.idatt2003.group14.ui.features.game.transaction.receipt.TransactionReceiptView;
import edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu.MainMenuController;
import edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu.MainMenuView;
import edu.ntnu.idatt2003.group14.ui.features.menu.newgame.NewGameController;
import edu.ntnu.idatt2003.group14.ui.features.menu.newgame.NewGameView;
import edu.ntnu.idatt2003.group14.ui.features.menu.options.OptionsController;
import edu.ntnu.idatt2003.group14.ui.features.menu.options.OptionsView;
import java.util.HashMap;
import java.util.Map;

/**
 * Registry responsible for constructing and managing the lifecycle of views.
 *
 * <p>Centralizes caching for the application's views. Stateless views does not need caching.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class ViewRegistry {
  private final AppController appController;
  private final AudioManager audioManager;
  private final Map<Route, View> viewCache;

  /**
   * Initiates a new ViewRegistry.
   *
   * @param appController to interact with the application
   * @param audioManager  to interact with the application audio
   */
  public ViewRegistry(AppController appController, AudioManager audioManager) {
    this.appController = appController;
    this.audioManager = audioManager;
    viewCache = new HashMap<>();
  }

  /**
   * Returns a new instance of the main menu view.
   *
   * <p>Reconstructed on every call.</p>
   *
   * @param router the application router
   * @return a new {@link MainMenuView}
   */

  View getMainMenuView(AppRouter router) {
    return new MainMenuView(new MainMenuController(appController, router), audioManager);
  }

  /**
   * Returns a new instance of the options view.
   *
   * <p>Reconstructed on every call.</p>
   *
   * @param router the application router
   * @return a new {@link OptionsView}
   */

  View getOptionsView(AppRouter router) {
    return new OptionsView(
        new OptionsController(appController, router, audioManager), audioManager);
  }

  /**
   * Returns a new instance of the new game view.
   *
   * <p>Reconstructed on every call.</p>
   *
   * @param router the application router
   * @return a new {@link NewGameView}
   */

  View getNewGameView(AppRouter router) {
    return new NewGameView(
        new NewGameController(router), appController, audioManager);
  }

  /**
   * Returns the portfolio view, creating it on first access.
   *
   * <p>Cached so that portfolio state persists.</p>
   *
   * @return the cached {@link PortfolioView}
   */
  View getPortfolioView() {
    return viewCache.computeIfAbsent(Route.PORTFOLIO,
        _ -> new PortfolioView(new PortfolioController()));
  }

  /**
   * Returns the exchange view, instantiating it on first access.
   *
   * <p>Cached to persist the view.</p>
   *
   * @return the cached {@link ExchangeView}
   */
  View getExchangeView(AppRouter router) {
    return viewCache.computeIfAbsent(Route.EXCHANGE,
        _ -> new ExchangeView(new ExchangeController(router)));
  }

  /**
   * Returns the transaction archive view, creating it on first access.
   *
   * <p>Cached so that state persist.</p>
   *
   * @return the cached {@link TransactionArchiveView}
   */
  View getTransactionArchiveView() {
    return viewCache.computeIfAbsent(Route.TRANSACTION_ARCHIVE,
        _ -> new TransactionArchiveView(new TransactionArchiveController()));
  }

  /**
   * Returns a new instance of the GameMenuView.
   *
   * <p>Reconstructed on every call.</p>
   *
   * @param router the application router
   * @return a new {@link GameMenuView}
   */

  View getGameMenuView(AppRouter router) {
    return new GameMenuView(audioManager, new GameMenuController(router));
  }

  /**
   * Returns a new instance of the Purchase Stock Menu.
   *
   * <p>Reconstructed on every call.</p>
   *
   * @return a new {@link PurchaseStockView}
   */
  View getPurchaseStockView(AppRouter router) {
    return new PurchaseStockView(router);
  }

  /**
   * Returns a new instance of the Transaction Receipt modal.
   *
   * <p>Reconstructed on every call.</p>
   *
   * @return a new {@link TransactionReceiptView}
   */
  View getTransactionReceiptView(AppRouter router) {
    return new TransactionReceiptView(router);
  }

  /**
   * Clears the cache of all stored views.
   */
  void clearCache() {
    this.viewCache.clear();
  }
}
