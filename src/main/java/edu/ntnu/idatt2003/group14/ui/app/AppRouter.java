package edu.ntnu.idatt2003.group14.ui.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Handles navigation logic between different views.
 *
 * <p>Handles the mapping of routes to view, manages navigation history for back
 * functionality, and tracks the state of active popups.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class AppRouter {
  private final Map<Route, Supplier<View>> routes = new HashMap<>();
  private final AppNavigator navigator;
  private final ViewRegistry viewRegistry;
  private Route previousRoute;
  private Route currentRoute;
  private boolean activePopup;

  /**
   * Initializes a new AppRouter and registers all available routes.
   *
   * @param viewRegistry the registry for obtaining view instances
   * @param navigator    the navigator for updating the UI
   */
  public AppRouter(ViewRegistry viewRegistry, AppNavigator navigator) {
    this.navigator = navigator;
    this.viewRegistry = viewRegistry;

    // Menu views
    routes.put(Route.MAIN_MENU, () -> viewRegistry.getMainMenuView(this));
    routes.put(Route.NEW_GAME, () -> viewRegistry.getNewGameView(this));
    routes.put(Route.OPTIONS, () -> viewRegistry.getOptionsView(this));

    // Game views
    routes.put(Route.PORTFOLIO, viewRegistry::getPortfolioView);
    routes.put(Route.TRANSACTION_ARCHIVE, viewRegistry::getTransactionArchiveView);
    routes.put(Route.EXCHANGE, () -> viewRegistry.getExchangeView(this));
    routes.put(Route.GAME_MENU, () -> viewRegistry.getGameMenuView(this));
    routes.put(Route.PURCHASE_STOCK, () -> viewRegistry.getPurchaseStockView(this));
    routes.put(Route.TRANSACTION_RECEIPT, () -> viewRegistry.getTransactionReceiptView(this));
  }

  /**
   * Navigates to a specified route.
   *
   * <p>Updates the navigation history and current route state if the route is not a popup.
   * Determines the appropriate navigator method based on the route type.</p>
   *
   * @param route the route to navigate to
   */
  public void navigate(Route route) {
    if (!route.isPopupRoute()) {
      previousRoute = currentRoute;
      currentRoute = route;
    }
    View view = routes.get(route).get();
    if (route.isPopupRoute()) {
      activePopup = true;
      navigator.showPopup(view, this);
    } else if (route.isGameRoute()) {
      navigator.showGameView(view, this);
    } else {
      navigator.showView(view);
    }
  }

  /**
   * Hides the currently active popup and updates the state.
   */
  public void hidePopup() {
    activePopup = false;
    navigator.hidePopup(this);
  }

  /**
   * Navigates back to the previous route, or to the main menu if no history exists.
   */
  public void back() {
    navigate(Objects.requireNonNullElse(previousRoute, Route.MAIN_MENU));
  }

  /**
   * Handles the escape key press logic.
   *
   * <p>Hides an active popup if one exists, otherwise opens the game menu.</p>
   */
  public void handleEscape() {
    if (activePopup) {
      hidePopup();
    } else {
      navigate(Route.GAME_MENU);
    }
  }

  /**
   * Clears the cache of all stored views by calling viewRegistry to clear its cache and
   * resets the routing.
   */
  public void clearCache() {
    this.hidePopup();
    this.previousRoute = null;
    this.activePopup = false;
    this.viewRegistry.clearCache();
  }
}
