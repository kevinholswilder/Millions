package edu.ntnu.idatt2003.group14.ui;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.app.AppController;
import edu.ntnu.idatt2003.group14.ui.app.AppControllerImpl;
import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;
import edu.ntnu.idatt2003.group14.ui.app.AppNavigatorImpl;
import java.util.List;

import edu.ntnu.idatt2003.group14.ui.app.ViewManager;
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
    Parameters params = getParameters();
    List<String> raw = params.getRaw();
    skipMenu = raw.contains("--skipMenu");

    if (skipMenu) {
      debugPrint("Skipping menu screen");
      debugPrint("User: \"TestUser\"");
      debugPrint("Stocks file: \"/resources/sp500.csv\"");
    }
  }

  @Override
  public void start(Stage stage) {

    stage.setTitle("Millions");

    AudioManager audioManager = new AudioManager();
    audioManager.playOfficeNoice();

    AppController appController = new AppControllerImpl(stage);
    AppNavigator appNavigator = new AppNavigatorImpl(stage, appController, audioManager);

    ViewManager viewManager = new ViewManager(stage);

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

  private static void debugPrint(String message) {
    IO.println("\u001B[33m[DEBUG]\u001B[38;5;15m " + message + "\u001B[0m");
  }
}
