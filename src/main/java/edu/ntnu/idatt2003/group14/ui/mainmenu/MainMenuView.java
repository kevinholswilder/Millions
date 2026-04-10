package edu.ntnu.idatt2003.group14.ui.mainmenu;

import edu.ntnu.idatt2003.group14.GameConfig;
import edu.ntnu.idatt2003.group14.ui.App;
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

  /**
   * Initializes a new MainMenuView.
   *
   * @param app the main application controller used for navigation
   */
  public MainMenuView(App app) {
    this.controller = new MainMenuController(app);
    this.root = new BorderPane();
    this.root.getStyleClass().add("main-menu-root-container");

    // Game title and buttons
    VBox centerMenu = new VBox(50);
    centerMenu.setAlignment(Pos.CENTER);
    centerMenu.setPadding(new Insets(100, 0, 0, 0));

    Label title = new Label("Millions");
    title.getStyleClass().add("menu-title");

    VBox btnBox = new VBox(15);
    btnBox.setAlignment(Pos.CENTER);

    // New Game button
    Button startBtn = new Button("New Game");
    startBtn.getStyleClass().add("menu-button");
    startBtn.setOnAction(e -> controller.handleNewGame());

    // Fullscreen game
    Button fullScreenBtn = new Button("Full Screen");
    fullScreenBtn.getStyleClass().add("menu-button");
    fullScreenBtn.setOnAction(e -> controller.handleFullScreen());

    // Exit Game button
    Button exitBtn = new Button("Exit");
    exitBtn.getStyleClass().add("menu-button");
    exitBtn.setOnAction(e -> controller.handleExitGame());

    btnBox.getChildren().addAll(startBtn, fullScreenBtn, exitBtn);
    centerMenu.getChildren().addAll(title, btnBox);

    // Game version and author
    HBox bottomBar = new HBox();
    bottomBar.setPadding(new Insets(20));

    Label leftLabel = new Label("version " + GameConfig.VERSION);
    leftLabel.getStyleClass().add("menu-text");

    Label rightLabel = new Label("Developed by " + GameConfig.AUTHOR);
    rightLabel.getStyleClass().add("menu-text");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    bottomBar.getChildren().addAll(leftLabel, spacer, rightLabel);

    root.setCenter(centerMenu);
    root.setBottom(bottomBar);
  }

  public Parent getRoot() {
    return root;
  }
}
