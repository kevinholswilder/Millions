package edu.ntnu.idatt2003.group14.ui.features.game.gamemenu;

import edu.ntnu.idatt2003.group14.service.GameService;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import edu.ntnu.idatt2003.group14.ui.app.Route;

/**
 * Controller class for handling user input from the Game Menu scene.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class GameMenuController {
  private final AppRouter router;
  private final GameService gameService;

  /**
   * Initializes a new GameMenuController.
   *
   * @param router the application view router
   */
  public GameMenuController(AppRouter router, GameService gameService) {
    this.router = router;
    this.gameService = gameService;
  }

  /**
   * Switch to the options view.
   */
  public void showOptions() {
    router.navigate(Route.OPTIONS);
  }

  /**
   * User requests to save and quit to main menu.
   */
  public void handleSaveAndQuit() {
    gameService.quitGame();
    router.navigate(Route.MAIN_MENU);
  }

  /**
   * User requests to resume the game.
   */
  public void resumeGame() {
    router.hidePopup();
  }
}
