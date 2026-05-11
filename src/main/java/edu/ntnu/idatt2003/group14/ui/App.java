package edu.ntnu.idatt2003.group14.ui;

import edu.ntnu.idatt2003.group14.logging.AppLogger;
import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.app.AppController;
import edu.ntnu.idatt2003.group14.ui.app.AppControllerImpl;
import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;
import edu.ntnu.idatt2003.group14.ui.app.AppNavigatorImpl;
import edu.ntnu.idatt2003.group14.utils.AppDataPathUtil;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The JavaFx entry point. Responsible for initializing
 * the necessary classes and starting the application.
 */
public final class App extends Application {
  private boolean skipMenu = false;

  @Override
  public void init() {
    try {
      AppDataPathUtil.initializeDataDirectory();
    } catch (IOException e) {
      AppLogger.error("Failed to initialize data directory.", e);
      throw new RuntimeException("Cannot start application.", e);
    }

    Parameters params = getParameters();
    List<String> raw = params.getRaw();
    skipMenu = raw.contains("--skipMenu");

    if (skipMenu) {
      AppLogger.fine("Skipping menu screen");
      AppLogger.fine("User: \"TestUser\"");
      AppLogger.fine("Stocks file: \"/resources/sp500.csv\"");
    }
  }

  @Override
  public void start(Stage stage) {

    stage.setTitle("Millions");

    AudioManager audioManager = new AudioManager();
    audioManager.playOfficeNoice();

    AppController appController = new AppControllerImpl(stage);
    AppNavigator appNavigator = new AppNavigatorImpl(stage, appController, audioManager);

    if (skipMenu) {
      skipMenu(appNavigator);
    } else {
      appNavigator.showMainMenuView();
    }
    stage.show();
  }

  private void skipMenu(AppNavigator appNavigator) {
    // enable by running with:
    // javafx:run -Djavafx.args=--skipMenu
    // java -Djavafx.args="--skipMenu" -jar Millions.jar
    appNavigator.showPortfolioView();
  }
}
