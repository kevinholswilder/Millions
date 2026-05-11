package edu.ntnu.idatt2003.group14.ui.app;

import edu.ntnu.idatt2003.group14.ui.features.game.GameLayout;
import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Responsible for managing the primary stage and navigating between different views.
 *
 * @author Elias Haugsbakk, Kevin Holswilder
 * @since 0.0.1
 */
public class AppNavigator {
  // 16:9 720p HD: scales nicely to all 16:9 or 16:10 displays
  private static final int DEFAULT_WIDTH = 1280;
  private static final int DEFAULT_HEIGHT = 720;
  private final Stage stage;
  private final ViewRegistry viewRegistry;

  private Parent previousView;
  private GameLayout gameLayout;

  /**
   * Initializes a new AppNavigator.
   *
   * @param stage        the primary stage
   * @param viewRegistry to fetch cached or constructed views
   */
  public AppNavigator(Stage stage, ViewRegistry viewRegistry) {
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

  /**
   * Display the previous view displayed before Options was opened.
   */
  public void showPreviousView() {
    if (this.previousView == null) {
      showMainMenuView();
    }
    navigateTo(this.previousView);
  }

  /**
   * Display the main menu view.
   */
  public void showMainMenuView() {
    navigateTo(viewRegistry.getMainMenuView(this).getRoot());
  }

  /**
   * Display the options view.
   */
  public void showOptionsView() {
    previousView = stage.getScene().getRoot();
    navigateTo(viewRegistry.getOptionsView(this).getRoot());
  }

  /**
   * Display the new game view.
   */
  public void showNewGameView() {
    navigateTo(viewRegistry.getNewGameView(this).getRoot());
  }

  /**
   * Display portfolio view.
   */
  public void showPortfolioView() {
    getGameLayout().setContent(viewRegistry.getPortfolioView().getRoot());
    navigateTo(getGameLayout().getRoot());
  }

  /**
   * Display transaction archive view.
   */
  public void showTransactionArchiveView() {
    getGameLayout().setContent(viewRegistry.getTransactionArchiveView().getRoot());
    navigateTo(getGameLayout().getRoot());
  }

  /**
   * Display game menu.
   */
  public void showGameMenu() {
    getGameLayout().showPopup(viewRegistry.getGameMenuView(this));
  }

  /**
   * Hides any popup displayed in the game view.
   */
  public void hidePopup() {
    getGameLayout().hidePopup();
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

