package edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu;

import edu.ntnu.idatt2003.group14.ui.app.AppController;
import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;

/**
 * Controller class for handling user input from the Main Menu.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class MainMenuController {
  private final AppController appController;
  private final AppNavigator appNavigator;

  /**
   * Initializes a new MainMenuController.
   *
   * @param appController the application controller
   * @param appNavigator the application navigator
   */
  public MainMenuController(AppController appController, AppNavigator appNavigator) {
    this.appController = appController;
    this.appNavigator = appNavigator;
  }

  /**
   * User requests new game.
   */
  public void handleNewGame() {
    appNavigator.showNewGameView();
  }

  /**
   * User requests to exit game.
   */
  public void handleExitGame() {
    appController.exitGame();
  }

  /**
   * User requests to toggle fullscreen.
   */
  public void handleFullScreen() {
    appController.toggleFullScreen();
  }
}
