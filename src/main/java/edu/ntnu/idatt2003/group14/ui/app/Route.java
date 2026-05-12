package edu.ntnu.idatt2003.group14.ui.app;

/**
 * Describes a route to a new view and the type of route.
 */
public enum Route {
  // Menu routes
  MAIN_MENU(RouteType.MENU),
  NEW_GAME(RouteType.MENU),
  OPTIONS(RouteType.MENU),

  // Game routes
  PORTFOLIO(RouteType.GAME),
  TRANSACTION_ARCHIVE(RouteType.GAME),

  // Popup routes
  GAME_MENU(RouteType.POPUP);

  private final RouteType type;

  Route(RouteType type) {
    this.type = type;
  }

  public boolean isGameRoute() {
    return type == RouteType.GAME;
  }

  public boolean isPopupRoute() {
    return type == RouteType.POPUP;
  }

  private enum RouteType {
    MENU, GAME, POPUP
  }
}
