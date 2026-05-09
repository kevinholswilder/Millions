package edu.ntnu.idatt2003.group14.ui.app;

import edu.ntnu.idatt2003.group14.model.Portfolio;
import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.service.PortfolioService;
import edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu.MainMenuController;
import edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu.MainMenuView;
import edu.ntnu.idatt2003.group14.ui.features.menu.newgame.NewGameController;
import edu.ntnu.idatt2003.group14.ui.features.menu.newgame.NewGameView;
import edu.ntnu.idatt2003.group14.ui.features.portfolio.GameLayout;
import edu.ntnu.idatt2003.group14.ui.features.portfolio.PortfolioController;
import edu.ntnu.idatt2003.group14.ui.features.portfolio.PortfolioView;
import edu.ntnu.idatt2003.group14.ui.transaction.TransactionArchiveController;
import edu.ntnu.idatt2003.group14.ui.transaction.TransactionArchiveView;
import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Implementation of the AppNavigator interface.
 *
 * <p>Responsible for managing the primary stage and navigating between different views.</p>
 *
 * @author Elias Haugsbakk, Kevin Holswilder
 * @since 0.0.1
 */
public class AppNavigatorImpl implements AppNavigator {
  // 16:9 720p HD: scales nicely to all 16:9 or 16:10 displays
  private static final int DEFAULT_WIDTH = 1280;
  private static final int DEFAULT_HEIGHT = 720;
  private final Stage stage;
  private final AppController appController;
  private final AudioManager audioManager;

  private GameLayout gameLayout;

  /**
   * Initializes a new AppNavigatorImpl.
   *
   * @param stage         the primary stage
   * @param appController the application controller
   * @param audioManager  the audio manager
   */
  public AppNavigatorImpl(Stage stage, AppController appController, AudioManager audioManager) {
    this.stage = stage;
    this.appController = appController;
    this.audioManager = audioManager;
  }

  /**
   * Returns the game layout, creating it if necessary.
   *
   * @return the game layout instance
   */
  private GameLayout getGameLayout() {
    if (gameLayout == null) {
      gameLayout = new GameLayout(this);
    }
    return gameLayout;
  }

  @Override
  public void showMainMenuView() {
    MainMenuController controller = new MainMenuController(appController, this);
    navigateTo(new MainMenuView(controller, audioManager).getRoot());
  }

  @Override
  public void showNewGameView() {
    NewGameController controller = new NewGameController(this);
    navigateTo(new NewGameView(controller, appController, audioManager).getRoot());
  }

  @Override
  public void showPortfolioView() {
    // TODO: Service and portfolio should not be created here.
    //  Service should probably be provided by a ServiceRegistry
    //  class which holds the services in memory.

    PortfolioService service = new PortfolioService(new Portfolio());
    PortfolioController controller = new PortfolioController(service);

    GameLayout layout = getGameLayout();
    layout.setContent(new PortfolioView(controller).getRoot());

    navigateTo(layout.getRoot());
  }

  @Override
  public void showTransactionArchiveView() {
    GameLayout layout = getGameLayout();

    TransactionArchiveController controller = new TransactionArchiveController();
    layout.setContent(new TransactionArchiveView(controller).getRoot());

    navigateTo(layout.getRoot());
  }

  private void navigateTo(Parent root) {
    // First time setup
    if (stage.getScene() == null) {
      Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
      String css =
          Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm();
      scene.getStylesheets().add(css);
      stage.setScene(scene);
    }
    stage.getScene().setRoot(root);
  }
}


