package edu.ntnu.idatt2003.group14.ui.app;

import java.io.File;

/**
 * Application and window utilities.
 */
public interface AppController {
  /**
   * If the application is windowed, it gets full-screened.
   * If the application is fullscreen, it gets windowed.
   */
  void toggleFullScreen();

  /**
   * Exits the game gracefully.
   */
  void exitGame();

  /**
   * Opens a file picker dialog to choose a CSV-file.
   *
   * @return the {@link File} to the CSV-file
   */
  File openStockFileDialog();
}
