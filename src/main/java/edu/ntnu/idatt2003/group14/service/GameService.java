package edu.ntnu.idatt2003.group14.service;

import edu.ntnu.idatt2003.group14.io.reader.stock.StockReader;
import edu.ntnu.idatt2003.group14.model.Exchange;
import edu.ntnu.idatt2003.group14.model.GameSession;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Service responsible for managing the game lifecycle and state.
 *
 * <p>Handles starting and quitting games, advancing time, and provides access
 * to the current game session.</p>
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
 */
public class GameService {
  private final StockReader stockReader;
  private GameSession currentSession;

  /**
   * Initiates a new GameService class.
   *
   * @param stockReader to read stock file from disk
   */
  public GameService(StockReader stockReader) {
    this.stockReader = stockReader;
  }

  /**
   * Starts a new game session with the given parameters.
   *
   * @param username      the name of the player
   * @param startingMoney the initial balance for the player
   * @param stockDataFile the file containing stock data for the exchange
   */
  public void startGame(String username, BigDecimal startingMoney, File stockDataFile)
      throws IOException {
    Player player = new Player(username, startingMoney);
    String exchangeName =
        stockDataFile.getName().substring(0, stockDataFile.getName().lastIndexOf("."));
    Exchange exchange =
        new Exchange(exchangeName, stockReader.read(stockDataFile.toPath().toString()));
    // TODO: should notify the user if  the read fails, and why.
    //  (improper formatted file? disk read failer?)

    // Add portfolio as listener to the exchange
    exchange.addWeekAdvanceListener(player.getPortfolio());
    this.currentSession = new GameSession(player, exchange);
  }

  /**
   * Ends the current game session and clears the state.
   */
  public void quitGame(AppRouter router) {
    // TODO: implement game saving
    this.currentSession = null;
    router.clearCache();
  }

  /**
   * Advances the game by one week.
   *
   * @throws IllegalStateException if no game session is active
   */
  public void advanceWeek() {
    ensureActiveSession();
    this.currentSession.exchange().advance();
  }

  /**
   * Gets the current player.
   *
   * @return the current player
   * @throws IllegalStateException if no game session is active
   */
  public Player getPlayer() {
    ensureActiveSession();
    return currentSession.player();
  }

  /**
   * Gets the current exchange.
   *
   * @return the current exchange
   * @throws IllegalStateException if no game session is active
   */
  public Exchange getExchange() {
    ensureActiveSession();
    return currentSession.exchange();
  }

  private void ensureActiveSession() {
    if (currentSession == null) {
      throw new IllegalStateException("No active game session");
    }
  }
}
