package edu.ntnu.idatt2003.group14.ui.app;

/**
 * Handles switching between different scenes.
 */
public interface AppNavigator {
  /**
   * Switch the view to show the main menu.
   */
  void showMainMenuView();

  /**
   * Switch the view to show the new game screen.
   */
  void showNewGameView();
}

