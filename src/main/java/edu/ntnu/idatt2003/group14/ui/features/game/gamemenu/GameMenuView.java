package edu.ntnu.idatt2003.group14.ui.features.game.gamemenu;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.app.View;
import edu.ntnu.idatt2003.group14.ui.components.MenuButtonFactory;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX scene for the Game Menu scene.
 *
 * <p>This is intended to be displayed as a popup over the game.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class GameMenuView implements View {
  private final BorderPane root;
  private final MenuButtonFactory buttonFactory;
  private final GameMenuController controller;

  /**
   * Initializes a new GameMenuView.
   *
   * @param audioManager the audio manager
   * @param controller   the game menu controller
   */
  public GameMenuView(AudioManager audioManager, GameMenuController controller) {
    this.root = new BorderPane();
    this.controller = controller;
    this.buttonFactory = new MenuButtonFactory(audioManager);
    this.root.setCenter(centerMenu());
    this.root.getStylesheets().add(
        Objects.requireNonNull(getClass().getResource("/css/menu.css")).toExternalForm()
    );
  }

  @Override
  public Parent getRoot() {
    return this.root;
  }

  private VBox centerMenu() {
    VBox centerMenu = new VBox(50);
    centerMenu.setAlignment(Pos.CENTER);
    centerMenu.setPadding(new Insets(100, 0, 0, 0));

    Label title = new Label("Options");
    title.getStyleClass().add("menu-title");

    VBox btnBox = new VBox(15);
    btnBox.setAlignment(Pos.CENTER);

    Button resumeBtn = buttonFactory.createMenuButton("Resume Game", controller::resumeGame);
    Button optionsBtn = buttonFactory.createMenuButton("Options", controller::showOptions);
    Button exitBtn =
        buttonFactory.createMenuButton("Quit without Saving", controller::handleSaveAndQuit);

    btnBox.getChildren().addAll(resumeBtn, optionsBtn, exitBtn);
    centerMenu.getChildren().addAll(
        title,
        btnBox
    );
    return centerMenu;
  }
}
