package edu.ntnu.idatt2003.group14.ui.menu;

import javafx.scene.control.Button;

/**
 * Factory class to create menu buttons.
 *
 * <p>Menu buttons get consistent styling and functionality</p>
 */
public class ButtonFactory {

  /**
   * Creates a new button.
   *
   * @param text the displayed text on the button
   * @param onAction what to run when button is clicked
   * @return the newly crated {@link Button} object
   */
  public static Button createMenuButton(String text, Runnable onAction) {
    Button btn = new Button(text);
    btn.getStyleClass().add("menu-button");
    btn.setOnAction(e -> onAction.run());
    return btn;
  }
}
