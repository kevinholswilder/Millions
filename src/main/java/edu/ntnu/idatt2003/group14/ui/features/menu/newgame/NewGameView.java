package edu.ntnu.idatt2003.group14.ui.features.menu.newgame;

import edu.ntnu.idatt2003.group14.logging.AppLogger;
import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.app.AppController;
import edu.ntnu.idatt2003.group14.ui.features.menu.MenuButtonFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX scene for the New Game scene of the application.
 *
 * <p>Prompts the user for a username, starting money
 * and stock file and lets them start the game.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class NewGameView {
  private final AppController appController;
  private final StackPane root;
  private final NewGameController controller;
  private final AudioManager audioManager;
  private final MenuButtonFactory buttonFactory;
  private TextField usernameField;
  private TextField startingMoneyField;
  private Button fileChooserBtn;
  private File stockDataFile;
  private Label errorLabel;

  /**
   * Initializes a new NewGameView.
   *
   * @param controller    the controller for this view
   * @param appController the application controller
   * @param audioManager  the audio manager
   */
  public NewGameView(
      NewGameController controller,
      AppController appController,
      AudioManager audioManager) {
    this.controller = controller;
    this.appController = appController;
    this.audioManager = audioManager;
    this.buttonFactory = new MenuButtonFactory(audioManager);
    this.root = new StackPane();
    this.root.getStylesheets().add(
        Objects.requireNonNull(getClass().getResource("/css/menu.css")).toExternalForm()
    );
    this.root.getChildren().addAll(backgroundView(), centerMenu());
  }

  private Region backgroundView() {
    Region bg = new Region();
    bg.getStyleClass().add("main-menu-root-container");
    bg.setEffect(new GaussianBlur(20));
    return bg;
  }

  /**
   * Returns the username entered by the user.
   *
   * @return username String; could be empty
   */
  public String getUsername() {
    return this.usernameField.getText().trim();
  }

  /**
   * Returns the staring money entered by the user.
   *
   * @return starting money String; could be empty
   */
  public String getStartingMoney() {
    return this.startingMoneyField.getText().trim();
  }

  public Parent getRoot() {
    return this.root;
  }

  private VBox centerMenu() {
    VBox centerMenu = new VBox(30);
    centerMenu.setAlignment(Pos.CENTER);

    this.errorLabel = new Label();
    this.errorLabel.getStyleClass().add("error-message");

    this.usernameField = new TextField("Username");
    this.usernameField.getStyleClass().add("menu-text-field");

    this.startingMoneyField = new TextField();
    this.startingMoneyField.setPromptText("Insert Starting money");
    this.startingMoneyField.getStyleClass().add("menu-text-field");

    this.fileChooserBtn = buttonFactory.createMenuButton(
        "Pick CSV stock data file",
        this::pickFile
    );
    Button startNewGame = buttonFactory.createMenuButton(
        "Start New Game",
        this::startGame
    );
    Button mainMenu = buttonFactory.createMenuButton(
        "Main Menu", controller::handleMainMenu);

    centerMenu.getChildren().addAll(
        errorLabel,
        this.usernameField,
        this.startingMoneyField,
        fileChooserBtn,
        startNewGame,
        mainMenu
    );

    return centerMenu;
  }

  private void pickFile() {
    this.stockDataFile = this.appController.openStockFileDialog();

    if (this.stockDataFile != null) {
      this.fileChooserBtn.setText(this.stockDataFile.getName());
    }
  }

  private void startGame() {
    String username = getUsername();
    String amount = getStartingMoney();

    NewGameValidationState result =
        controller.validateNewGameInput(username, amount, stockDataFile);

    switch (result) {
      case VALID -> {
        try {
          controller.handleStartGame(
                  username,
                  new BigDecimal(amount),
                  this.stockDataFile
          );
        } catch (IOException e) {
          AppLogger.error("The selected file is invalid and/or cannot be read.", e);
          showError(
                  "The selected stock data file is invalid and/or cannot be read.",
                  fileChooserBtn
          );
        }
      }
      case NEGATIVE_AMOUNT -> showError(
          "Starting Money must be positive",
          startingMoneyField
      );
      case NOT_A_NUMBER -> showError(
          "Starting Money must be a number",
          startingMoneyField
      );
      case EMPTY_USERNAME -> showError(
          "Username cannot be empty",
          usernameField
      );
      case NO_FILE_CHOSEN -> showError(
          "No stock data file has been selected",
          fileChooserBtn
      );
      default -> throw new IllegalStateException("Unexpected startingMoney: " + result);
    }
  }

  private void showError(String errorMessage, Control... controls) {
    audioManager.playErrorSound();
    this.errorLabel.setText(errorMessage);

    this.usernameField.getStyleClass().remove("menu-text-field-error");
    this.startingMoneyField.getStyleClass().remove("menu-text-field-error");
    this.fileChooserBtn.getStyleClass().remove("menu-button-error");

    for (var control : controls) {
      if (control instanceof TextField) {
        control.getStyleClass().add("menu-text-field-error");
      } else if (control instanceof Button) {
        control.getStyleClass().add("menu-button-error");
      }
    }
  }
}
