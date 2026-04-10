package edu.ntnu.idatt2003.group14.ui.menu.newgame;

import edu.ntnu.idatt2003.group14.ui.App;
import edu.ntnu.idatt2003.group14.ui.AudioManager;
import edu.ntnu.idatt2003.group14.ui.menu.ButtonFactory;
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
  private final AudioManager audioManager;
  private final ButtonFactory buttonFactory;
  private TextField usernameField;
  private TextField startingMoneyField;
  private Button fileChooserBtn;
  private File stockDataFile;
  private Label errorLabel;

  /**
   * Initializes a new NewGameView.
   *
   * @param app the main application controller used for navigation
   */
  public NewGameView(App app, AudioManager audioManager) {
    this.controller = new NewGameController(app);
    this.audioManager = audioManager;
    this.buttonFactory = new ButtonFactory(audioManager);
    this.root = new BorderPane();
    this.root.getStyleClass().add("main-menu-root-container");
    this.root.setCenter(centerMenu(app));
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

  private VBox centerMenu(App app) {
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
        () -> pickFile(app)
    );
    Button startNewGame = buttonFactory.createMenuButton(
        "Start New Game",
        () -> startGame(getUsername(), getStartingMoney())
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

  private void pickFile(App app) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV-file with stock data");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("CSV files", "*.csv"));



    this.stockDataFile = fileChooser.showOpenDialog(app.getStage());

    if (this.stockDataFile != null) {
      this.fileChooserBtn.setText(this.stockDataFile.getName());
    }
  }

  enum IsValid {
    VALID,
    NEGATIVE_AMOUNT,
    NOT_A_NUMBER,
    EMPTY_USERNAME,
    NO_FILE_CHOSEN
  }

  private record ValidationResult(IsValid state, String username, BigDecimal value) {}

  private ValidationResult areValuesValid(String username, String stringAmount) {
    IsValid usernameValid = validateUsername(username);
    if (usernameValid != IsValid.VALID) {
      return new ValidationResult(usernameValid, null, null);
    }

    IsValid amountValid = validateAmount(stringAmount);
    if (amountValid != IsValid.VALID) {
      return new ValidationResult(amountValid, null, null);
    }

    IsValid fileValid = validateFileChosen();
    if (fileValid != IsValid.VALID) {
      return new ValidationResult(fileValid, null, null);
    }

    return new ValidationResult(IsValid.VALID, username, new BigDecimal(stringAmount));
  }

  private IsValid validateUsername(String username) {
    return username.isBlank() ? IsValid.EMPTY_USERNAME : IsValid.VALID;
  }

  private IsValid validateAmount(String amount) {
    BigDecimal bigDecimalAmount;
    try {
      bigDecimalAmount = new BigDecimal(amount);
      if (bigDecimalAmount.longValue() <= 0) {
        return IsValid.NEGATIVE_AMOUNT;
      } else {
        return IsValid.VALID;
      }
    } catch (NumberFormatException e) {
      return IsValid.NOT_A_NUMBER;
    }
  }

  private IsValid validateFileChosen() {
    return this.stockDataFile == null ? IsValid.NO_FILE_CHOSEN : IsValid.VALID;
  }

  private void startGame(String username, String amount) {
    ValidationResult result = areValuesValid(username, amount);

    switch (result.state) {
      case VALID -> controller.handleStartGame(result.username, result.value, this.stockDataFile);
      case NEGATIVE_AMOUNT -> showError(
          "Starting Money must be positive", this.startingMoneyField);
      case NOT_A_NUMBER -> showError(
          "Starting Money must be a number", this.startingMoneyField);
      case EMPTY_USERNAME -> showError(
          "Username cannot be empty", this.usernameField);
      case NO_FILE_CHOSEN -> showError(
          "No stock data file has been selected", this.fileChooserBtn);
      default -> throw new IllegalStateException("Unexpected value: " + result.state);
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
