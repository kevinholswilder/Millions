package edu.ntnu.idatt2003.group14.ui.menu.mainmenu;

import edu.ntnu.idatt2003.group14.GameConfig;
import edu.ntnu.idatt2003.group14.ui.App;
import edu.ntnu.idatt2003.group14.ui.AudioManager;
import edu.ntnu.idatt2003.group14.ui.menu.ButtonFactory;
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
public class MainMenuView {
  private final BorderPane root;
  private final MainMenuController controller;
  private final ButtonFactory buttonFactory;

  /**
   * Initializes a new MainMenuView.
   *
   * @param app the main application controller used for navigation
   */
  public MainMenuView(App app, AudioManager audioManager) {
    this.controller = new MainMenuController(app);
    this.buttonFactory = new ButtonFactory(audioManager);
    this.root = new BorderPane();
    this.root.getStyleClass().add("main-menu-root-container");
    this.root.setCenter(centerMenu());
    this.root.setBottom(bottomBar());
  }

  public Parent getRoot() {
    return root;
  }

  private VBox centerMenu() {

    VBox centerMenu = new VBox(50);
    centerMenu.setAlignment(Pos.CENTER);
    centerMenu.setPadding(new Insets(100, 0, 0, 0));

    Label title = new Label("Millions");
    title.getStyleClass().add("menu-title");

    VBox btnBox = new VBox(15);
    btnBox.setAlignment(Pos.CENTER);

    Button startBtn = buttonFactory.createMenuButton("New Game", controller::handleNewGame);
    Button fullScreenBtn = buttonFactory
        .createMenuButton("Full Screen", controller::handleFullScreen);
    Button exitBtn = buttonFactory.createMenuButton("Exit", controller::handleExitGame);

    btnBox.getChildren().addAll(startBtn, fullScreenBtn, exitBtn);
    centerMenu.getChildren().addAll(
        title,
        btnBox
    );

    return centerMenu;
  }

  private HBox bottomBar() {

    HBox bottomBar = new HBox();
    bottomBar.setPadding(new Insets(20));

    Label leftLabel = new Label("version " + GameConfig.VERSION);
    leftLabel.getStyleClass().add("menu-text");

    Label rightLabel = new Label("Developed by " + GameConfig.AUTHOR);
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
