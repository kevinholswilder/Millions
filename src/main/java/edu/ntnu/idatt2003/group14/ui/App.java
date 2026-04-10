package edu.ntnu.idatt2003.group14.ui;

import edu.ntnu.idatt2003.group14.ui.mainmenu.MainMenuView;
import edu.ntnu.idatt2003.group14.ui.newgame.NewGameView;
import java.util.Objects;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * The JavaFX Application class for the Millions project.
 *
 * <p>This class is responsible solely for managing the primary {@link Stage} and
 * handling the transition between different view components. Following the
 * principle of clean separation, this class does not initialize non-ui classes, these are handled
 * by the Launcher entry point or injected.</p>
 *
 * <p>It manages only the active {@link Scene}.
 * Note that the application entry point is located
 * in the Launcher class, not within this class.</p>
 *
 * @author Elias Haugsbakk
 * @version 0.0.1
 */
public final class App extends Application {
  // 16:9 720p HD: scales nicely to all 16:9 or 16:10 displays
  private static final int DEFAULT_WIDTH = 1280;
  private static final int DEFAULT_HEIGHT = 720;

  private Stage stage;

  /**
   * Initializes the primary stage.
   *
   * @param stage the primary stage for this application
   */
  @Override
  public void start(Stage stage) {
    this.stage = stage;
    stage.setTitle("Millions");
    showMainMenuView();
    stage.show();
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

  /**
   * Switches the stage to the Main Menu view.
   */
  public void showMainMenuView() {
    navigateTo(new MainMenuView(this).getRoot());
  }

  /**
   * Switches the stage to the New Game view.
   */
  public void showNewGameView() {
    navigateTo(new NewGameView(this).getRoot());
  }

  /**
   * Toggles fullscreen on and off.
   */
  public void toggleFullScreen() {
    stage.setFullScreenExitHint("");
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    stage.setFullScreen(!stage.isFullScreen());
  }
}
