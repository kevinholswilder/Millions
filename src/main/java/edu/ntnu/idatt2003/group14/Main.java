package edu.ntnu.idatt2003.group14;

import edu.ntnu.idatt2003.group14.utils.AppDataPathUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class: the entrypoint of the program.
 *
 * @author Elias Haugsbakk, Kevin Holswilder
 * @since 0.0.1
 */
public class Main {

  public static final Logger LOGGER = Logger.getLogger("Millions");

  /**
   * Entrypoint for the application.
   *
   * <p>Initializes the data directory path for Millions.</p>
   */
  static void main() {
    try {
      AppDataPathUtil.initializeDataDirectory();
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Failed to initialize data directory.", e);
    }
  }
}
