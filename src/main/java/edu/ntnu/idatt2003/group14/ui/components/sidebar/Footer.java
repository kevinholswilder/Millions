package edu.ntnu.idatt2003.group14.ui.components.sidebar;

import edu.ntnu.idatt2003.group14.config.lang.LangConfig;
import edu.ntnu.idatt2003.group14.service.GameService;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * A footer button component for the sidebar.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public final class Footer extends VBox {

  /**
   * Creates a new footer button with predefined styling and layout settings.
   */
  public Footer(GameService gameService, SideBarController controller) {

    Button nextWeekBtn = new Button(LangConfig.getInstance().lang("sidebar-footer.advance"));
    nextWeekBtn.setMaxWidth(Double.MAX_VALUE);
    nextWeekBtn.setPrefHeight(100.0);
    nextWeekBtn.setOnAction(_ -> gameService.advanceWeek());

    Button menuBtn = new Button(LangConfig.getInstance().lang("sidebar-footer.menu"));
    menuBtn.setMaxWidth(Double.MAX_VALUE);
    menuBtn.setPrefHeight(100.0);
    menuBtn.setOnAction(_ -> controller.gameMenu());

    this.getChildren().addAll(menuBtn, nextWeekBtn);
  }
}
