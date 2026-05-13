package edu.ntnu.idatt2003.group14.ui.components;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import javafx.scene.control.Button;

/**
 * Factory class to create menu buttons.
 *
 * <p>Menu buttons get consistent styling and functionality</p>
 */
public class MenuButtonFactory {
  private final AudioManager audioManager;

  /**
   * Initializes a new MenuButtonFactory.
   *
   * @param audioManager for plating button sound effects
   */
  public MenuButtonFactory(AudioManager audioManager) {
    this.audioManager = audioManager;
  }

  /**
   * Creates a new button.
   *
   * @param text     the displayed text on the button
   * @param onAction what to run when button is clicked
   * @return the newly crated {@link Button} object
   */
  public Button createMenuButton(String text, Runnable onAction) {
    Button btn = new Button(text);
    btn.getStyleClass().add("menu-button");
    btn.setOnMouseEntered(_ -> audioManager.playButtonHoverSound());
    btn.setOnAction(_ -> {
      audioManager.playButtonClickSound();
      onAction.run();
    });
    return btn;
  }
}
