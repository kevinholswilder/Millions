package edu.ntnu.idatt2003.group14.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class for accessing the path to the data directory.
 *
 * <p>This class is mainly responsible for accessing the data directory which is
 * used for saving files locally to the user's computer.
 * <br>
 * It determines the data directory depending on the user's OS system.
 * </p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public final class AppDataPathUtil {

  private static final String APP_FOLDER_NAME = "Millions";
  private static final String DATA_FOLDER_NAME = "data";

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

    return base.resolve(DATA_FOLDER_NAME);
  }

  /**
   * Ensures that the data directory exists.
   *
   * <p>If the data directory does not exist, it will be created.</p>
   */
  public static void ensureDataDirectoryExists() throws IOException {
    Files.createDirectories(getDataDirectory());
  }

}