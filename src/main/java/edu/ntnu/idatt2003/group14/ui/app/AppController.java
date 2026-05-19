package edu.ntnu.idatt2003.group14.ui.app;

import java.io.File;
import javafx.application.Platform;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Provides core application functionality such as exit and full screen management.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public final class AppController {
  private final Stage stage;

  /**
   * Initializes a new AppController.
   *
   * @param stage the primary stage
   */
  public AppController(Stage stage) {
    this.stage = stage;
  }

  /**
   * Exit the game gracefully.
   */
  public void exitGame() {
    Platform.exit();
  }

  /**
   * Toggles the application between full screen and floating.
   */
  public void toggleFullScreen() {
    stage.setFullScreenExitHint("");
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    stage.setFullScreen(!stage.isFullScreen());
  }

  /**
   * Requests the OS to open the file picker dialog.
   *
   * @return {@link File} of the Stock File
   */
  public File openStockFileDialog() {
    File stockDataFile;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV-file with stock data");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("CSV files", "*.csv"));

    stockDataFile = fileChooser.showOpenDialog(this.stage);
    return stockDataFile;
  }
}
