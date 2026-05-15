package edu.ntnu.idatt2003.group14.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Global configuration and metadata for the Millions application.
 *
 * @author Elias Haugsbakk, Kevin Holswilder
 * @version 1.0.0
 * @since 0.0.1
 */
public final class GameConfig {

  // Prevent Instantiation
  private GameConfig() {}

  public static final String AUTHOR = "Elias Haugsbakk & Kevin Holswilder";
  public static final String VERSION = "1.0.0";

  public static final Gson GSON = new GsonBuilder()
          .setPrettyPrinting()
          .disableHtmlEscaping()
          .create();

  /**
   * {@code true} sets the logging level to {@code Level.FINE}, {@code false}
   *      to {@code Level.WARNING}.
   */
  public static final boolean DEBUG = true;
}
