package edu.ntnu.idatt2003.group14.ui.features.menu.newgame;

import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;
import java.io.File;
import java.math.BigDecimal;

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
   * */
  public NewGameController(AppNavigator appNavigator) {
    this.appNavigator = appNavigator;
  }

  /**
   * Starts the game.
   *
   * @param username the users username
   * @param startingMoney the amount of starting money
   * @param stockData the CSV file containing stock data
   */
  public void handleStartGame(String username, BigDecimal startingMoney, File stockData) {
    IO.println("Username: " + username);
    IO.println("Starting Money: " + startingMoney);
    IO.println("Stock data file: " + stockData.getName());
  }

  /**
   * Shows the main menu.
   */
  public void handleMainMenu() {
    appNavigator.showMainMenuView();
  }
}
