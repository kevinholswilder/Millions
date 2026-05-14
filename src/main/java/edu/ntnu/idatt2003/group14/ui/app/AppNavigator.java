package edu.ntnu.idatt2003.group14.ui.app;

import edu.ntnu.idatt2003.group14.service.GameService;
import edu.ntnu.idatt2003.group14.ui.features.game.GameLayout;
import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Responsible for managing the primary stage and navigating between different views.
 *
 * @author Elias Haugsbakk, Kevin Holswilder
 * @since 0.0.1
 */
public class AppNavigator {
  // 16:9 720p HD: scales nicely to all 16:9 or 16:10 displays
  private static final int DEFAULT_WIDTH = 1280;
  private static final int DEFAULT_HEIGHT = 720;
  private final Stage stage;
  private final GameService gameService;

  private GameLayout gameLayout;

  /**
   * Initializes a new AppNavigator.
   *
   * @param stage the primary stage
   */
  public AppNavigator(Stage stage, GameService gameService) {
    this.stage = stage;
    this.gameService = gameService;
  }

  /**
   * Returns the game layout, creating it if necessary.
   *
   * @param router the application router
   * @return the game layout instance
   */
  private GameLayout getGameLayout(AppRouter router) {
    if (gameLayout == null) {
      gameLayout = new GameLayout(gameService, router);
    }
    return gameLayout;
  }

  /**
   * Displays a standard view by setting it as the scene root.
   *
   * @param view the view to display
   */
  void showView(View view) {
    navigateTo(view.getRoot());
  }

  /**
   * Displays a view within the game layout.
   *
   * @param view   the view to display
   * @param router the application router
   */
  void showGameView(View view, AppRouter router) {
    getGameLayout(router).setContent(view.getRoot());
    navigateTo(getGameLayout(router).getRoot());
  }

  /**
   * Displays a popup overlay over the current game view.
   *
   * @param view   the view to display as a popup
   * @param router the application router
   */
  void showPopup(View view, AppRouter router) {
    getGameLayout(router).showPopup(view.getRoot());
  }

  /**
   * Hides any active popup displayed in the game view.
   *
   * @param router the application router
   */
  void hidePopup(AppRouter router) {
    getGameLayout(router).hidePopup();
  }

  private void navigateTo(Parent root) {
    // First time setup
    if (stage.getScene() == null) {
      Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
      String css =
          Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm();
      scene.getStylesheets().add(css);
      stage.setScene(scene);
    }
    stage.getScene().setRoot(root);
  }
}
