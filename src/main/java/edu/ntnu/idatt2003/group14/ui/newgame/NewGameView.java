package edu.ntnu.idatt2003.group14.ui.newgame;

import edu.ntnu.idatt2003.group14.ui.App;
import java.math.BigDecimal;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX scene for the New Game scene of the application.
 *
 * <p>Prompts the user for a username and lets them start the game.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class NewGameView {
  private final BorderPane root;
  private final NewGameController controller;
  private final TextField usernameField;
  private final TextField startingMoneyField;
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
    this.startingMoneyField = new TextField("Starting Money");
    this.startingMoneyField.getStyleClass().add("menu-text-field");

    // Start new game
    Button startNewGame = new Button("Start New Game");
    startNewGame.getStyleClass().add("menu-button");
    startNewGame.setOnAction(e -> startGame(getUsername(), getStartingMoney()));

    // Exit to main menu
    Button mainMenu = new Button("Main Menu");
    mainMenu.getStyleClass().add("menu-button");
    mainMenu.setOnAction(e -> controller.handleMainMenu());

    centerMenu.getChildren().addAll(errorLabel,
        this.usernameField, this.startingMoneyField, startNewGame, mainMenu);

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

  enum IsValid {
    valid,
    negativeAmount,
    notANumber,
    emptyUsername
  }

  private record ValidationResult(IsValid state, BigDecimal value, String username) {}

  private ValidationResult areValuesValid(String username, String stringAmount) {
    if (username.isBlank()) {
      return new ValidationResult(IsValid.emptyUsername, null, null);
    }
    BigDecimal bigDecimalAmount;
    try {
      bigDecimalAmount = new BigDecimal(stringAmount);
      if (bigDecimalAmount.longValue() <= 0) {
        return new ValidationResult(IsValid.negativeAmount, null, null);
      } else {
        return new ValidationResult(IsValid.valid, bigDecimalAmount, username);
      }
    } catch (NumberFormatException e) {
      return new ValidationResult(IsValid.notANumber, null, null);
    }
  }

  private void startGame(String username, String amount) {
    ValidationResult result = areValuesValid(username, amount);

    switch (result.state) {
      case valid -> controller.handleStartGame(username, result.value);
      case negativeAmount -> showError("Starting Money must be positive", this.startingMoneyField);
      case notANumber -> showError("Starting Money must be a number", this.startingMoneyField);
      case emptyUsername -> showError("Username cannot be empty", this.usernameField);
      default -> throw new IllegalStateException("Unexpected value: " + result.state);
    }
  }

  private void showError(String errorMessage, TextField field) {
    this.errorLabel.setText(errorMessage);

    this.usernameField.getStyleClass().remove("menu-text-field-error");
    this.startingMoneyField.getStyleClass().remove("menu-text-field-error");

    field.getStyleClass().add("menu-text-field-error");
  }
}
