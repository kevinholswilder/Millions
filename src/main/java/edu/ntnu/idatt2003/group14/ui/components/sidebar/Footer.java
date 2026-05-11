package edu.ntnu.idatt2003.group14.ui.components.sidebar;

import edu.ntnu.idatt2003.group14.model.Exchange;
import edu.ntnu.idatt2003.group14.model.GameSession;
import javafx.scene.control.Button;

/**
 * A footer button component for the sidebar.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class Footer extends Button {

  /**
   * Creates a new footer button with predefined styling and layout settings.
   */
  public Footer() {
    this.setText("Advance");
    this.setMaxWidth(Double.MAX_VALUE);
    this.setPrefHeight(100.0);

    this.setOnAction(_ -> {
      if (GameSession.getExchange().isPresent()) {
        Exchange exchange = GameSession.getExchange().get();
        exchange.advance();
      }
    });
  }

}