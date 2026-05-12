package edu.ntnu.idatt2003.group14.ui.components.sidebar;

import java.util.Objects;
import javafx.scene.layout.BorderPane;

/**
 * Sidebar component containing navigation, exchange overview, and footer.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class SideBar extends BorderPane {

  /**
   * Creates a new sidebar component.
   *
   * @param controller controller used for sidebar navigation
   */
  public SideBar(SideBarController controller) {
    Header header = new Header(controller);
    Footer footer = new Footer();

    this.getStylesheets().add(
        Objects.requireNonNull(getClass().getResource("/css/sidebar/sidebar.css")).toExternalForm()
    );

    this.getStyleClass().add("sidebar-container");

    this.setBottom(footer);
    this.setTop(header);

    sceneProperty().addListener((_, _, newScene) -> {
      prefWidthProperty().bind(newScene.widthProperty().multiply(0.15));
    });
  }
}