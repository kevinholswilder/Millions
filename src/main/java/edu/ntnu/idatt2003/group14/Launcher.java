package edu.ntnu.idatt2003.group14;

import edu.ntnu.idatt2003.group14.ui.App;
import javafx.application.Application;

/**
 * Launcher class: the entrypoint of the program.
 *
 * @author Elias Haugsbakk, Kevin Holswilder
 * @since 0.0.1
 */
public class Launcher {

  /**
   * Entrypoint for the application.
   *
   * <p>Initializes the data directory path for Millions and starts the JavaFX application.</p>
   */
  void main(String[] args) {
    Application.launch(App.class, args);
  }
}
