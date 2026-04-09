package edu.ntnu.idatt2003.group14.ui;

import javafx.application.Application;
import javafx.scene.Scene;
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

  /**
   * Initializes the primary stage.
   *
   * @param stage the primary stage for this application
   */
  @Override
  public void start(Stage stage) {
    stage.setTitle("Millions");
    stage.show();
  }
}
