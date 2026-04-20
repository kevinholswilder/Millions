package edu.ntnu.idatt2003.group14.ui.app;

import java.io.File;
import javafx.application.Platform;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Implementation of the AppController interface.
 *
 * <p>Provides core application functionality such as exit and full screen management.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class AppControllerImpl implements AppController {
  private final Stage stage;

  /**
   * Initializes a new AppControllerImpl.
   *
   * @param stage the primary stage
   */
  public AppControllerImpl(Stage stage) {
    this.stage = stage;
  }

  @Override
  public void exitGame() {
    Platform.exit();
  }

  @Override
  public void toggleFullScreen() {
    stage.setFullScreenExitHint("");
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    stage.setFullScreen(!stage.isFullScreen());
  }

  @Override
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
