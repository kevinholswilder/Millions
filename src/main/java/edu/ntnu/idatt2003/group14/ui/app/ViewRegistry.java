package edu.ntnu.idatt2003.group14.ui.app;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu.MainMenuController;
import edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu.MainMenuView;
import edu.ntnu.idatt2003.group14.ui.features.menu.newgame.NewGameController;
import edu.ntnu.idatt2003.group14.ui.features.menu.newgame.NewGameView;
import edu.ntnu.idatt2003.group14.ui.features.menu.options.OptionsController;
import edu.ntnu.idatt2003.group14.ui.features.menu.options.OptionsView;
import edu.ntnu.idatt2003.group14.ui.features.portfolio.PortfolioController;
import edu.ntnu.idatt2003.group14.ui.features.portfolio.PortfolioView;
import edu.ntnu.idatt2003.group14.ui.features.transaction.TransactionArchiveController;
import edu.ntnu.idatt2003.group14.ui.features.transaction.TransactionArchiveView;
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
  private final Map<String, View> viewCache;

  /**
   * Initiates a new ViewRegistry.
   *
   * @param appController to interact with the application
   * @param audioManager to interact with the application audio
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
   * @param appNavigator the navigator passed to the controller
   * @return a new {@link MainMenuView}
   */
  public View getMainMenuView(AppNavigator appNavigator) {
    return new MainMenuView(new MainMenuController(appController, appNavigator), audioManager);
  }

  /**
   * Returns a new instance of the options view.
   *
   * <p>Reconstructed on every call.</p>
   *
   * @param appNavigator the navigator passed to the controller
   * @return a new {@link OptionsView}
   */
  public View getOptionsView(AppNavigator appNavigator) {
    return new OptionsView(
        new OptionsController(appController, audioManager, appNavigator), audioManager);
  }

  /**
   * Returns a new instance of the new game view.
   *
   * <p>Reconstructed on every call.</p>
   *
   * @param appNavigator the navigator passed to the controller
   * @return a new {@link NewGameView}
   */
  public View getNewGameView(AppNavigator appNavigator) {
    return new NewGameView(
        new NewGameController(appNavigator), appController, audioManager);
  }

  /**
   * Returns the portfolio view, creating it on first access.
   *
   * <p>Cached so that portfolio state persists.</p>
   *
   * @return the cached {@link PortfolioView}
   */
  public View getPortfolioView() {
    return viewCache.computeIfAbsent("portfolioView",
        _ -> new PortfolioView(new PortfolioController()));
  }

  /**
   * Returns the transaction archive view, creating it on first access.
   *
   * <p>Cached so that state persist.</p>
   *
   * @return the cached {@link TransactionArchiveView}
   */
  public View getTransactionArchiveView() {
    return viewCache.computeIfAbsent("transactionArchiveView",
        _ -> new TransactionArchiveView(new TransactionArchiveController()));
  }
}
