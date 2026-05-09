package edu.ntnu.idatt2003.group14.ui.features.menu.newgame;

import edu.ntnu.idatt2003.group14.Launcher;
import edu.ntnu.idatt2003.group14.io.reader.stock.StockReader;
import edu.ntnu.idatt2003.group14.model.Exchange;
import edu.ntnu.idatt2003.group14.model.GameSession;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * Controller class for handling user input from the New Game scene.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class NewGameController {
  private final AppNavigator appNavigator;

  /**
   * Initializes a new NewGameController.
   *
   * @param appNavigator the main application controller used for navigation
   *
   */
  public NewGameController(AppNavigator appNavigator) {
    this.appNavigator = appNavigator;
  }

  /**
   * Shows the main menu.
   */
  public void handleMainMenu() {
    appNavigator.showMainMenuView();
  }

  /**
   * Validates the user inputted values.
   *
   * @param username      the username
   * @param amount        the amount of starting money
   * @param stockDataFile the stock data {@link File}
   * @return the {@link NewGameValidationState} containing information
   *        on if the inputted information is valid
   */
  public NewGameValidationState validateNewGameInput(String username, String amount,
                                                     File stockDataFile) {
    NewGameValidationState usernameValid = validateUsername(username);
    if (usernameValid != NewGameValidationState.VALID) {
      return usernameValid;
    }
    NewGameValidationState amountValid = validateAmount(amount);
    if (amountValid != NewGameValidationState.VALID) {
      return amountValid;
    }

    return validateFileChosen(stockDataFile);
  }

  /**
   * Starts the game and passes on the inputted information.
   *
   * @param username      the users username
   * @param startingMoney the users starting money
   * @param stockDataFile the file containing stock data
   */
  public void handleStartGame(String username, BigDecimal startingMoney, File stockDataFile) {
    IO.println("Username: " + username);
    IO.println("Starting Money: " + startingMoney);
    IO.println("Stock data file: " + stockDataFile.getName());

    try {
      // Initialize player
      GameSession.setPlayer(
              new Player(username, startingMoney)
      );

      // Initialize exchange
      StockReader reader = new StockReader();
      GameSession.setExchange(
              new Exchange("Temporary placeholder", reader.read(stockDataFile.getName()))
      );
      appNavigator.showPortfolioView();
    } catch (IOException e) {
      // TODO: Give the user proper feedback by using a pop up dialog.
      Launcher.LOGGER.log(Level.SEVERE, "The selected file is invalid and/or cannot be read.", e.getMessage());
    }
  }

  private NewGameValidationState validateUsername(String username) {
    return username.isBlank() ? NewGameValidationState.EMPTY_USERNAME :
            NewGameValidationState.VALID;
  }

  private NewGameValidationState validateAmount(String amount) {
    BigDecimal bigDecimalAmount;
    try {
      bigDecimalAmount = new BigDecimal(amount);
      if (bigDecimalAmount.longValue() <= 0) {
        return NewGameValidationState.NEGATIVE_AMOUNT;
      } else {
        return NewGameValidationState.VALID;
      }
    } catch (NumberFormatException e) {
      return NewGameValidationState.NOT_A_NUMBER;
    }
  }

  private NewGameValidationState validateFileChosen(File stockDataFile) {
    return stockDataFile == null ? NewGameValidationState.NO_FILE_CHOSEN :
            NewGameValidationState.VALID;
  }
}
