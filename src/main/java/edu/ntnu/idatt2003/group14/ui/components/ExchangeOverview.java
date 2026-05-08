package edu.ntnu.idatt2003.group14.ui.components;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * A vertical list of exchange buttons.
 *
 * @author Kevin Holswilder
 */
public class ExchangeOverview extends VBox {

  /**
   * Creates the exchange overview.
   */
  public ExchangeOverview() {
    for (int i = 0; i < 10; i++) {
      Button exchangeButton = new Button("S&P 500");
      exchangeButton.setMaxWidth(Double.MAX_VALUE);
      exchangeButton.setPrefHeight(100);
      this.getChildren().add(exchangeButton);
    }
  }
}