package edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu;

import edu.ntnu.idatt2003.group14.ui.app.AppController;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import edu.ntnu.idatt2003.group14.ui.app.Route;

/**
 * Controller class for handling user input from the Main Menu.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class MainMenuController {
  private final AppController appController;
  private final AppRouter router;

  /**
   * Initializes a new MainMenuController.
   *
   * @param appController the application controller
   * @param router        the application router
   */
  public MainMenuController(AppController appController, AppRouter router) {
    this.appController = appController;
    this.router = router;
  }

  /**
   * User requests new game.
   */
  public void handleNewGame() {
    router.navigate(Route.NEW_GAME);
  }

  /**
   * User requests to show the options view.
   */
  public void showOptions() {
    router.navigate(Route.OPTIONS);
  }

  /**
   * User requests to exit game.
   */
  public void handleExitGame() {
    appController.exitGame();
  }
}
