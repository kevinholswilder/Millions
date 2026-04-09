package edu.ntnu.idatt2003.group14;

import edu.ntnu.idatt2003.group14.ui.App;
import edu.ntnu.idatt2003.group14.utils.AppDataPathUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;

/**
 * Launcher class: the entrypoint of the program.
 *
 * @author Elias Haugsbakk, Kevin Holswilder
 * @since 0.0.1
 */
public class Launcher {

  public static final Logger LOGGER = Logger.getLogger("Millions");

  /**
   * Entrypoint for the application.
   *
   * <p>Initializes the data directory path for Millions and starts the JavaFX application.</p>
   */
  void main(String[] args) {
    try {
      AppDataPathUtil.initializeDataDirectory();
      Application.launch(App.class, args);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Failed to initialize data directory.", e);
    }
  }
}
