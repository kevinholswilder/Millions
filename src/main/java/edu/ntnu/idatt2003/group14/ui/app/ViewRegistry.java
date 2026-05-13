package edu.ntnu.idatt2003.group14.ui.app;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.features.game.exchange.ExchangeController;
import edu.ntnu.idatt2003.group14.ui.features.game.exchange.ExchangeView;
import edu.ntnu.idatt2003.group14.ui.features.game.gamemenu.GameMenuController;
import edu.ntnu.idatt2003.group14.ui.features.game.gamemenu.GameMenuView;
import edu.ntnu.idatt2003.group14.ui.features.game.portfolio.PortfolioController;
import edu.ntnu.idatt2003.group14.ui.features.game.portfolio.PortfolioView;
import edu.ntnu.idatt2003.group14.ui.features.game.transaction.TransactionArchiveController;
import edu.ntnu.idatt2003.group14.ui.features.game.transaction.TransactionArchiveView;
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

  public View getMainMenuView(AppRouter router) {
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

  public View getOptionsView(AppRouter router) {
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

  public View getNewGameView(AppRouter router) {
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
  public View getPortfolioView() {
    return viewCache.computeIfAbsent(Route.PORTFOLIO,
        _ -> new PortfolioView(new PortfolioController()));
  }

  public View getExchangeView() {
    return viewCache.computeIfAbsent(Route.EXCHANGE,
        _ -> new ExchangeView(new ExchangeController()));
  }

  /**
   * Returns the transaction archive view, creating it on first access.
   *
   * <p>Cached so that state persist.</p>
   *
   * @return the cached {@link TransactionArchiveView}
   */
  public View getTransactionArchiveView() {
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

  public View getGameMenuView(AppRouter router) {
    return new GameMenuView(audioManager, new GameMenuController(router));
  }
}
