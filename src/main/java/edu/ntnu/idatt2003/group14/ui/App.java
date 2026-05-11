package edu.ntnu.idatt2003.group14.ui;

import edu.ntnu.idatt2003.group14.io.reader.stock.StockReader;
import edu.ntnu.idatt2003.group14.logging.AppLogger;
import edu.ntnu.idatt2003.group14.model.Exchange;
import edu.ntnu.idatt2003.group14.model.GameSession;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.app.AppController;
import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;
import edu.ntnu.idatt2003.group14.ui.app.ViewRegistry;
import edu.ntnu.idatt2003.group14.utils.AppDataPathUtil;
import java.io.IOException;
import java.math.BigDecimal;
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
      AppLogger.fine("User: \"TestPlayer\"");
      AppLogger.fine("Stocks file: \"/resources/sp500.csv\"");
    }
  }

  @Override
  public void start(Stage stage) {

    stage.setTitle("Millions");

    AudioManager audioManager = new AudioManager();
    audioManager.playOfficeNoice();

    AppController appController = new AppController(stage);
    ViewRegistry viewRegistry = new ViewRegistry(appController, audioManager);
    AppNavigator appNavigator = new AppNavigator(stage, viewRegistry);

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
    try {
      GameSession.setPlayer(new Player("TestPlayer", new BigDecimal("100000")));
      Exchange exchange = new Exchange(
          "sp500",
          new StockReader().read("sp500.csv")
      );
      GameSession.setExchange(exchange);
    } catch (IOException e) {
      AppLogger.error("Could not load /resources/sp500.csv", e);
      throw new RuntimeException(e);
    }
    appNavigator.showPortfolioView();
  }
}
