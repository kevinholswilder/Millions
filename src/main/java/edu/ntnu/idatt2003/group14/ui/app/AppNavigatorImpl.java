package edu.ntnu.idatt2003.group14.ui.app;

import edu.ntnu.idatt2003.group14.ui.exchange.ExchangeController;
import edu.ntnu.idatt2003.group14.ui.exchange.ExchangeView;
import edu.ntnu.idatt2003.group14.ui.features.portfolio.GameLayout;
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
  private final ViewRegistry viewRegistry;

  private GameLayout gameLayout;

  /**
   * Initializes a new AppNavigatorImpl.
   *
   * @param stage         the primary stage
   */
  public AppNavigatorImpl(Stage stage, ViewRegistry viewRegistry) {
    this.stage = stage;
    this.viewRegistry = viewRegistry;
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
    navigateTo(viewRegistry.getMainMenuView(this).getRoot());
  }

  @Override
  public void showOptionsView() {
    navigateTo(viewRegistry.getOptionsView(this).getRoot());
  }

  @Override
  public void showNewGameView() {
    navigateTo(viewRegistry.getNewGameView(this).getRoot());
  }

  @Override
  public void showPortfolioView() {
    GameLayout layout = getGameLayout();
    layout.setContent(viewRegistry.getPortfolioView().getRoot());
    navigateTo(layout.getRoot());
  }

  @Override
  public void showTransactionArchiveView() {
    GameLayout layout = getGameLayout();
    layout.setContent(viewRegistry.getTransactionArchiveView().getRoot());
    navigateTo(layout.getRoot());
  }

  @Override
  public void showExchangeView() {
    GameLayout layout = getGameLayout();

    ExchangeController controller = new ExchangeController();
    layout.setContent(new ExchangeView(controller).getRoot());

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


