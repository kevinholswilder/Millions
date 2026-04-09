package edu.ntnu.idatt2003.group14.ui.mainmenu;

import edu.ntnu.idatt2003.group14.ui.App;

/**
 * Controller class for handling user input from the Main Menu.
 */
public class MainMenuController {
  private final App app;

  /**
   * Initializes a new MainMenuController.
   *
   * @param app the main application controller used for navigation
   * */
  public MainMenuController(App app) {
    this.app = app;
  }

  /**
   * User requests new game.
   *
   * @author Elias Haugsbakk
   * @since 0.0.1
   */
  public void handleNewGame() {
    IO.println("New Game hit");
  }

  /**
   * User requests to exit game.
   */
  public void handleExitGame() {
    System.exit(0);
  }

  /**
   * User requests to toggle fullscreen.
   */
  public void handleFullScreen() {
    app.toggleFullScreen();
  }
}
