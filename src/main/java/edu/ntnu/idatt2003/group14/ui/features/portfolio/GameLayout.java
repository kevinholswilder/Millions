package edu.ntnu.idatt2003.group14.ui.features.portfolio;

import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;
import edu.ntnu.idatt2003.group14.ui.components.sidebar.SideBar;
import edu.ntnu.idatt2003.group14.ui.components.sidebar.SideBarController;
import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * Main layout container for views.
 *
 * <p>Combines sidebar navigation with a centered area for other content.</p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class GameLayout {
  private final BorderPane root;
  private final SideBar navigation;

  /**
   * Creates a new game layout with sidebar navigation and stylesheets.
   *
   * @param appNavigator navigator used for view navigation
   */
  public GameLayout(AppNavigator appNavigator) {
    this.root = new BorderPane();

    this.navigation = new SideBar(new SideBarController(appNavigator));
    this.root.setLeft(navigation);

    String mainStyle =
        Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm();
    String gameStyle =
        Objects.requireNonNull(getClass().getResource("/css/game.css")).toExternalForm();

    this.root.getStylesheets().addAll(mainStyle, gameStyle);
  }

  /**
   * Sets the main content displayed in the layout.
   *
   * @param content the content to display
   */
  public void setContent(Parent content) {
    this.root.setCenter(content);
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