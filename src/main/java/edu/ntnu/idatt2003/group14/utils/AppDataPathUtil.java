package edu.ntnu.idatt2003.group14.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class for accessing the path to the applications storage directory.
 *
 * <p>This class is responsible for accessing the directory which is
 * used for saving files locally to the user's computer.
 * <br>
 * It determines the data directory depending on the user's OS.
 * Data is put under Millions/data/. Logs under Millions/logs/.
 * </p>
 *
 * @author Kevin Holswilder, Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
 */
public final class AppDataPathUtil {

  private static final String APP_FOLDER_NAME = "Millions";
  private static final String DATA_DIRECTORY_NAME = "data";
  private static final String LOGS_DIRECTORY_NAME = "logs";
  private static final String LOGS_FILE_NAME = "application.log";

  // Prevent instantiation
  private AppDataPathUtil() {}

  /**
   * Accesses the path to the data directory.
   *
   * <p>Retrieves the path depending on the user's OS system.</p>
   *
   * @return the path to the data directory
   */
  public static Path getDataDirectory() {
    return getPath(DATA_DIRECTORY_NAME);
  }

  public static Path getLogsFile() {
    return getPath(LOGS_DIRECTORY_NAME + "/" + LOGS_FILE_NAME);
  }

  /**
   * Accesses the path to the log file.
   *
   * <p>Retrieves the path depending on the user's OS system.</p>
   *
   * @return the path to the log file
   */
  public static Path getLogsDirectory() {
    return getPath(LOGS_DIRECTORY_NAME);
  }

  private static Path getPath(String logsFolderName) {
    String os = System.getProperty("os.name").toLowerCase();
    String userHome = System.getProperty("user.home");

    String osKey = os.contains("win") ? "windows"
        : os.contains("mac") ? "mac"
          : "linux";

    Path base;

    if (osKey.equalsIgnoreCase("windows")) {
      String appData = System.getenv("APPDATA");
      if (appData != null) {
        base = Paths.get(appData, APP_FOLDER_NAME);
      } else {
        base = Paths.get(userHome, APP_FOLDER_NAME);
      }
    } else if (osKey.equalsIgnoreCase("mac")) {
      base = Paths.get(userHome, "Library", "Application Support", APP_FOLDER_NAME);
    } else {
      base = Paths.get(userHome, ".local", "share", APP_FOLDER_NAME);
    }

    return base.resolve(logsFolderName);
  }

  /**
   * Ensures that the data directory exists.
   *
   * <p>If the data directory does not exist, it will be created.</p>
   */
  public static void initializeDataDirectory() throws IOException {
    Files.createDirectories(getDataDirectory());
    Files.createDirectories(getLogsDirectory());
  }
}

