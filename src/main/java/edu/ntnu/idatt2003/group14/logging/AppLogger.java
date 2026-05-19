package edu.ntnu.idatt2003.group14.logging;

import edu.ntnu.idatt2003.group14.config.GameConfig;
import edu.ntnu.idatt2003.group14.utils.AppDataPathUtil;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logging utility containing static methods for different logging levels.
 *
 * <p>Contains three different logging levels: error, warn and fine. {@link GameConfig} holds a
 * logging level deciding if only errors should be logged, or everything.</p>
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
 */
public final class AppLogger {
  private static final Logger LOGGER = Logger.getLogger("Millions");

  // no initialization
  private AppLogger() {
  }

  static {
    try {
      // log to console -- white text with System.out
      ConsoleHandler consoleHandler = new ConsoleHandler() {
        {
          setOutputStream(System.out);
        }
      };
      consoleHandler.setLevel(Level.ALL);

      // severe errors to disk
      Path millionsData = AppDataPathUtil.getLogsFile();
      FileHandler fileHandler = new FileHandler(millionsData.toFile().getAbsolutePath(), true);
      fileHandler.setFormatter(new SimpleFormatter());
      fileHandler.setLevel(Level.SEVERE);

      LOGGER.addHandler(consoleHandler);
      LOGGER.addHandler(fileHandler);
      LOGGER.setUseParentHandlers(false);
      LOGGER.setLevel(GameConfig.DEBUG ? Level.FINE : Level.WARNING);
    } catch (IOException e) {
      LOGGER.severe("Failed to initialize file logger");
    }
  }

  /**
   * Log a debug message for development purposes.
   *
   * @param msg the associated message
   */
  public static void fine(String msg) {
    LOGGER.fine(msg);
  }

  /**
   * Logging something which should not happen, but is not breaking.
   *
   * @param msg the associated message
   */
  public static void warn(String msg) {
    LOGGER.warning(msg);
  }

  /**
   * Logging a severe error. Usually an unhandled exception.
   *
   * @param msg the associated message
   */
  public static void error(String msg) {
    LOGGER.severe(msg);
  }

  /**
   * Logging a severe error. Usually an unhandled exception.
   *
   * @param msg the associated message
   * @param e   a throwable
   */
  public static void error(String msg, Throwable e) {
    LOGGER.log(Level.SEVERE, msg, e);
  }

  /**
   * Silences the file logger.
   * Useful for tests to avoid cluttering the log file.
   */
  public static void silence() {
    for (var handler : AppLogger.LOGGER.getHandlers()) {
      handler.close();
    }
  }
}
