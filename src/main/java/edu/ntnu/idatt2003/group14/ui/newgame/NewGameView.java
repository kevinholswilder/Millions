package edu.ntnu.idatt2003.group14.ui.newgame;

import edu.ntnu.idatt2003.group14.ui.App;
import java.io.File;
import java.math.BigDecimal;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

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
  private final BorderPane root;
  private final NewGameController controller;
  private final TextField usernameField;
  private final TextField startingMoneyField;
  private final Button fileChooserBtn;
  private File stockDataFile;
  private final Label errorLabel;

  /**
   * Initializes a new NewGameView.
   *
   * @param app the main application controller used for navigation
   */
  public NewGameView(App app) {
    this.controller = new NewGameController(app);
    this.root = new BorderPane();
    this.root.getStyleClass().add("main-menu-root-container");

    // Center menu
    VBox centerMenu = new VBox(30);
    centerMenu.setAlignment(Pos.CENTER);

    this.errorLabel = new Label();
    this.errorLabel.getStyleClass().add("error-message");

    // Username text field
    this.usernameField = new TextField("Username");
    this.usernameField.getStyleClass().add("menu-text-field");

    // Staring money text field
    this.startingMoneyField = new TextField();
    this.startingMoneyField.setPromptText("Insert Starting money");
    this.startingMoneyField.getStyleClass().add("menu-text-field");

    // Pick file of stocks button
    this.fileChooserBtn = new Button("Pick CSV stock data file");
    this.fileChooserBtn.getStyleClass().add("menu-button");
    this.fileChooserBtn.setOnAction(e -> pickFile(app));

    // Start new game
    Button startNewGame = new Button("Start New Game");
    startNewGame.getStyleClass().add("menu-button");
    startNewGame.setOnAction(e -> startGame(getUsername(), getStartingMoney()));

    // Exit to main menu
    Button mainMenu = new Button("Main Menu");
    mainMenu.getStyleClass().add("menu-button");
    mainMenu.setOnAction(e -> controller.handleMainMenu());

    centerMenu.getChildren().addAll(errorLabel,
        this.usernameField, this.startingMoneyField, fileChooserBtn, startNewGame, mainMenu);

    root.setCenter(centerMenu);
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
    return root;
  }

  private void pickFile(App app) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV-file with stock data");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("CSV files", "*.csv"));

    stockDataFile = fileChooser.showOpenDialog(app.getStage());
    this.fileChooserBtn.setText(stockDataFile.getName());
  }

  enum IsValid {
    valid,
    negativeAmount,
    notANumber,
    emptyUsername,
    noFileChosen
  }

  private record ValidationResult(IsValid state, String username, BigDecimal value) {}

  private ValidationResult areValuesValid(String username, String stringAmount) {
    IsValid usernameValid = validateUsername(username);
    if (usernameValid != IsValid.valid) {
      return new ValidationResult(usernameValid, null, null);
    }

    IsValid amountValid = validateAmount(stringAmount);
    if (amountValid != IsValid.valid) {
      return new ValidationResult(amountValid, null, null);
    }

    IsValid fileValid = validateFileChosen();
    if (fileValid != IsValid.valid) {
      return new ValidationResult(fileValid, null, null);
    }

    return new ValidationResult(IsValid.valid, username, new BigDecimal(stringAmount));
  }

  private IsValid validateUsername(String username) {
    return username.isBlank() ? IsValid.emptyUsername : IsValid.valid;
  }

  private IsValid validateAmount(String amount) {
    BigDecimal bigDecimalAmount;
    try {
      bigDecimalAmount = new BigDecimal(amount);
      if (bigDecimalAmount.longValue() <= 0) {
        return IsValid.negativeAmount;
      } else {
        return IsValid.valid;
      }
    } catch (NumberFormatException e) {
      return IsValid.notANumber;
    }
  }

  private IsValid validateFileChosen() {
    return this.stockDataFile == null ? IsValid.noFileChosen : IsValid.valid;
  }

  private void startGame(String username, String amount) {
    ValidationResult result = areValuesValid(username, amount);

    switch (result.state) {
      case valid -> controller.handleStartGame(result.username, result.value, this.stockDataFile);
      case negativeAmount -> showError(
          "Starting Money must be positive", this.startingMoneyField);
      case notANumber -> showError(
          "Starting Money must be a number", this.startingMoneyField);
      case emptyUsername -> showError(
          "Username cannot be empty", this.usernameField);
      case noFileChosen -> showError(
          "No stock data file has been selected", this.fileChooserBtn);
      default -> throw new IllegalStateException("Unexpected value: " + result.state);
    }
  }

  private void showError(String errorMessage, Control... controls) {
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
