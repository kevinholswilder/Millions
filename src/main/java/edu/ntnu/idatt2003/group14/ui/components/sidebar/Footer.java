package edu.ntnu.idatt2003.group14.ui.components.sidebar;

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
    this.setText("Settings");
    this.setMaxWidth(Double.MAX_VALUE);
    this.setPrefHeight(100.0);
  }

}