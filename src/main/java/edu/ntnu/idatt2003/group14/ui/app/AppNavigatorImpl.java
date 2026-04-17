package edu.ntnu.idatt2003.group14.ui.app;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu.MainMenuController;
import edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu.MainMenuView;
import edu.ntnu.idatt2003.group14.ui.features.menu.newgame.NewGameController;
import edu.ntnu.idatt2003.group14.ui.features.menu.newgame.NewGameView;
import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Implementation of the AppNavigator interface.
 *
 * <p>Responsible for managing the primary stage and navigating between different views.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class AppNavigatorImpl implements AppNavigator {
  // 16:9 720p HD: scales nicely to all 16:9 or 16:10 displays
  private static final int DEFAULT_WIDTH = 1280;
  private static final int DEFAULT_HEIGHT = 720;
  private final Stage stage;
  private final AppController appController;
  private final AudioManager audioManager;

  /**
   * Initializes a new AppNavigatorImpl.
   *
   * @param stage the primary stage
   * @param appController the application controller
   * @param audioManager the audio manager
   */
  public AppNavigatorImpl(Stage stage, AppController appController, AudioManager audioManager) {
    this.stage = stage;
    this.appController = appController;
    this.audioManager = audioManager;
  }

  @Override
  public void showMainMenuView() {
    MainMenuController controller = new MainMenuController(appController, this);
    navigateTo(new MainMenuView(controller, audioManager).getRoot());
  }

  @Override
  public void showNewGameView() {
    NewGameController controller = new NewGameController(this);
    navigateTo(new NewGameView(controller, appController, audioManager).getRoot());
  }

  private void navigateTo(Parent root) {
    if (stage.getScene() == null) {
      // First time setup
      Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
      String css = Objects.requireNonNull(getClass()
          .getResource("/css/style.css")).toExternalForm();
      scene.getStylesheets().add(css);
      stage.setScene(scene);
    } else {
      stage.getScene().setRoot(root);
    }
  }
}


