package edu.ntnu.idatt2003.group14.ui.features.menu.mainmenu;

import edu.ntnu.idatt2003.group14.config.GameConfig;
import edu.ntnu.idatt2003.group14.config.lang.LangConfig;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * JavaFX scene for the Main Menu of the application.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class MainMenuView implements View {
  private final BorderPane root;
  private final MainMenuController controller;
  private final MenuButtonFactory buttonFactory;

  /**
   * Initializes a new MainMenuView.
   *
   * @param controller   the controller for this view
   * @param audioManager the audio manager
   */
  public MainMenuView(MainMenuController controller, AudioManager audioManager) {
    this.controller = controller;
    this.buttonFactory = new MenuButtonFactory(audioManager);
    this.root = new BorderPane();
    this.root.getStylesheets().add(
        Objects.requireNonNull(getClass().getResource("/css/menu.css")).toExternalForm()
    );
    this.root.getStyleClass().add("main-menu-root-container");
    this.root.setCenter(centerMenu());
    this.root.setBottom(bottomBar());

    // Prevent autofocus
    this.root.sceneProperty().addListener((_, _, newScene) -> {
      if (newScene != null) {
        this.root.requestFocus();
      }
    });
  }

  @Override
  public Parent getRoot() {
    return this.root;
  }

  private VBox centerMenu() {

    VBox centerMenu = new VBox(50);
    centerMenu.setAlignment(Pos.CENTER);
    centerMenu.setPadding(new Insets(100, 0, 0, 0));

    Label title = new Label("Millions");
    title.getStyleClass().add("menu-title");

    VBox btnBox = new VBox(15);
    btnBox.setAlignment(Pos.CENTER);

    Button startBtn = buttonFactory
            .createMenuButton(
                LangConfig.getInstance().lang("main-menu.new_game"),
                controller::handleNewGame
            );
    Button optionsBtn = buttonFactory
        .createMenuButton(
            LangConfig.getInstance().lang("main-menu.options"),
            controller::showOptions
        );
    Button exitBtn = buttonFactory
            .createMenuButton(
                LangConfig.getInstance().lang("main-menu.exit"),
                controller::handleExitGame
            );

    btnBox.getChildren().addAll(startBtn, optionsBtn, exitBtn);
    centerMenu.getChildren().addAll(
        title,
        btnBox
    );

    return centerMenu;
  }

  private HBox bottomBar() {

    HBox bottomBar = new HBox();
    bottomBar.setPadding(new Insets(20));

    Label leftLabel = new Label(
        LangConfig.getInstance().lang("main-menu.version") + " " + GameConfig.VERSION
    );
    leftLabel.getStyleClass().add("menu-text");

    Label rightLabel = new Label(
        LangConfig.getInstance().lang("main-menu.authors") +  " " + GameConfig.AUTHOR
    );
    rightLabel.getStyleClass().add("menu-text");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    bottomBar.getChildren().addAll(
        leftLabel,
        spacer,
        rightLabel
    );

    return bottomBar;
  }
}
