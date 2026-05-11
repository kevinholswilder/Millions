package edu.ntnu.idatt2003.group14.ui.features.game.gamemenu;

import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;

/**
 * Controller class for handling user input from the Game Menu scene.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class GameMenuController {
  private final AppNavigator appNavigator;

  /**
   * Initializes a new GameMenuController.
   *
   * @param appNavigator the application navigator
   */
  public GameMenuController(AppNavigator appNavigator) {
    this.appNavigator = appNavigator;
  }

  /**
   * Switch to the options view.
   */
  public void showOptions() {
    this.appNavigator.showOptionsView();
  }

  /**
   * User requests to save and quit to main menu.
   */
  public void handleSaveAndQuit() {
    // TODO: implement saving
    this.appNavigator.showMainMenuView();
  }

  /**
   * User requests to resume the game.
   */
  public void resumeGame() {
    this.appNavigator.hidePopup();
  }
}
