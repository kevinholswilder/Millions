package edu.ntnu.idatt2003.group14.ui;

import edu.ntnu.idatt2003.group14.config.lang.LangConfig;
import edu.ntnu.idatt2003.group14.exception.csvreading.CSVReadException;
import edu.ntnu.idatt2003.group14.io.reader.stock.StockReader;
import edu.ntnu.idatt2003.group14.logging.AppLogger;
import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.service.GameService;
import edu.ntnu.idatt2003.group14.ui.app.AppController;
import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import edu.ntnu.idatt2003.group14.ui.app.Route;
import edu.ntnu.idatt2003.group14.ui.app.ViewRegistry;
import edu.ntnu.idatt2003.group14.utils.AppDataPathUtil;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The JavaFx entry point. Responsible for initializing
 * the necessary classes and starting the application.
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
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

    LangConfig.getInstance().load("/lang/en_us.json");

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
    GameService gameService = new GameService(new StockReader());
    ViewRegistry registry = new ViewRegistry(appController, audioManager, gameService);
    AppNavigator navigator = new AppNavigator(stage, gameService);
    AppRouter router = new AppRouter(registry, navigator);

    if (skipMenu) {
      skipMenu(router, gameService);
    } else {
      router.navigate(Route.MAIN_MENU);
    }
    stage.show();
  }

  private void skipMenu(AppRouter router, GameService gameService) {
    // enable by running with:
    // javafx:run -Djavafx.args=--skipMenu
    // java -Djavafx.args="--skipMenu" -jar Millions.jar
    try {
      gameService.startGame("TestPlayer", new BigDecimal("10000"), new File("sp500.csv"));
    } catch (CSVReadException e) {
      AppLogger.error("Could not load /resources/sp500.csv", e);
      throw new RuntimeException(e);
    }
    router.navigate(Route.PORTFOLIO);
  }
}
