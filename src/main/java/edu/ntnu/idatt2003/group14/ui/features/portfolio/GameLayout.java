package edu.ntnu.idatt2003.group14.ui.features.portfolio;

import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;
import edu.ntnu.idatt2003.group14.ui.components.sidebar.SideBar;
import edu.ntnu.idatt2003.group14.ui.components.sidebar.SideBarController;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
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
public class GameLayout {
  private final StackPane root;
  private final BorderPane layout;

  /**
   * Creates a new game layout with sidebar navigation and stylesheets.
   *
   * @param appNavigator navigator used for view navigation
   */
  public GameLayout(AppNavigator appNavigator) {
    this.layout = new BorderPane();
    this.root = new StackPane(this.layout);
    SideBar navigation = new SideBar(new SideBarController(appNavigator));
    this.layout.setLeft(navigation);

    String mainStyle =
        Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm();
    String gameStyle =
        Objects.requireNonNull(getClass().getResource("/css/game.css")).toExternalForm();

    this.layout.getStylesheets().addAll(mainStyle, gameStyle);
    this.root.getStylesheets().add(gameStyle);
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

  /**
   * Returns the root node of the layout.
   *
   * @return the root layout node
   */
  public Parent getRoot() {
    return this.root;
  }
}
