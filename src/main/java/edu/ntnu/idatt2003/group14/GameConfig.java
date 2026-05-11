package edu.ntnu.idatt2003.group14;

/**
 * Global configuration and metadata for the Millions application.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public final class GameConfig {
  // prevents initialization
  private GameConfig() {
  }

  public static final String AUTHOR = "Elias Haugsbakk & Kevin Holswilder";
  public static final String VERSION = "0.0.1";

  /**
   * {@code true} sets the logging level to {@code Level.FINE}, {@code false}
   *      to {@code Level.WARNING}.
   */
  public static final boolean DEBUG = true;
}
