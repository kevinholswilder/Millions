package edu.ntnu.idatt2003.group14.ui.features.game;

import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import edu.ntnu.idatt2003.group14.ui.app.View;
import edu.ntnu.idatt2003.group14.ui.components.sidebar.SideBar;
import edu.ntnu.idatt2003.group14.ui.components.sidebar.SideBarController;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Main layout container for views.
 *
 * <p>Combines sidebar navigation with a centered area for other content.</p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class GameLayout implements View {
  private final StackPane root;
  private final BorderPane layout;
  private final AppRouter router;

  /**
   * Creates a new game layout with sidebar navigation and stylesheets.
   *
   * @param router the application router
   */
  public GameLayout(AppRouter router) {
    this.router = router;
    this.layout = new BorderPane();
    this.root = new StackPane(this.layout);
    SideBar navigation = new SideBar(new SideBarController(router));
    this.layout.setLeft(navigation);

    String mainStyle =
        Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm();
    String gameStyle =
        Objects.requireNonNull(getClass().getResource("/css/game.css")).toExternalForm();

    this.layout.getStylesheets().addAll(mainStyle, gameStyle);
    this.root.getStylesheets().add(gameStyle);

    this.root.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        handleEscapePress();
      }
    });
  }

  /**
   * Sets the main content displayed in the layout.
   *
   * @param content the content to display
   */
  public void setContent(Parent content) {
    this.layout.setCenter(content);
  }

  /**
   * Displays a popup window over the game view.
   *
   * @param content the contents of the popup window
   */
  public void showPopup(Parent content) {
    layout.setEffect(new GaussianBlur(10));

    StackPane popup = new StackPane(content);
    popup.setMaxSize(800, 600);
    StackPane.setAlignment(popup, Pos.CENTER);
    popup.getStyleClass().add("popup-window");

    Region overlay = new Region();
    overlay.getStyleClass().add("popup-overlay");

    root.getChildren().addAll(overlay, popup);
  }

  /**
   * Removes the popup window.
   */
  public void hidePopup() {
    layout.setEffect(null);
    root.getChildren().removeIf(n -> n != layout);
  }

  @Override
  public Parent getRoot() {
    return this.root;
  }

  private void handleEscapePress() {
    router.handleEscape();
  }
}
