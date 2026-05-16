package edu.ntnu.idatt2003.group14.service;

import edu.ntnu.idatt2003.group14.exception.csvreading.CSVReadException;
import edu.ntnu.idatt2003.group14.io.gamesave.GameSaveManager;
import edu.ntnu.idatt2003.group14.io.gamesave.SaveMetadata;
import edu.ntnu.idatt2003.group14.io.reader.stock.StockReader;
import edu.ntnu.idatt2003.group14.model.Exchange;
import edu.ntnu.idatt2003.group14.model.GameSession;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Service responsible for managing the game lifecycle and state.
 *
 * <p>Handles starting and quitting games, advancing time, saving and loading,
 * and provides access to the current game session.</p>
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
 */
public class GameService {
  private final StockReader stockReader;
  private GameSession currentSession;
  private final GameSaveManager gameSaveManager;

  /**
   * Constructs a new {@code GameService}.
   *
   * @param stockReader     used to read stock data from disk
   * @param gameSaveManager used to persist and restore game saves
   */
  public GameService(StockReader stockReader, GameSaveManager gameSaveManager) {
    this.stockReader = stockReader;
    this.gameSaveManager = gameSaveManager;
  }


  /**
   * Starts a new game session with the given parameters.
   *
   * @param username      the name of the player
   * @param startingMoney the initial balance for the player
   * @param stockDataFile the file containing stock data for the exchange
   * @throws CSVReadException if the stock data file cannot be read
   */
  public void startGame(String username, BigDecimal startingMoney, File stockDataFile)
      throws CSVReadException {
    Player player = new Player(username, startingMoney);
    String exchangeName =
        stockDataFile.getName().substring(0, stockDataFile.getName().lastIndexOf("."));
    Exchange exchange =
        new Exchange(exchangeName, stockReader.read(stockDataFile.toPath().toString()));

    // Add portfolio as listener to the exchange
    exchange.addWeekAdvanceListener(player.getPortfolio());
    this.currentSession = new GameSession(player, exchange);
  }

  /**
   * Saves the current game session and ends it.
   *
   * @param router the app router, used to clear cached views
   * @throws IOException if the game state cannot be saved
   */
  public void quitGame(AppRouter router) throws IOException {
    try {
      saveGame();
    } finally {
      this.currentSession = null;
      router.clearCache();
    }
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

  /**
   * Saves the current game session to disk.
   *
   * @throws IOException           if the game state cannot be written
   * @throws IllegalStateException if no game session is active
   */
  public void saveGame() throws IOException {
    this.gameSaveManager.save(this.getPlayer(), this.getExchange());
  }

  /**
   * Loads a saved game by UUID and replaces the current session.
   *
   * @param uuid the UUID of the save to load
   * @throws IOException if the save file cannot be read
   */
  public void loadGame(String uuid) throws IOException {
    GameSaveManager.SaveGame savedGame = gameSaveManager.load(uuid);
    currentSession = new GameSession(savedGame.player(), savedGame.exchange());
    savedGame.exchange().addWeekAdvanceListener(savedGame.player().getPortfolio());
  }

  /**
   * Returns metadata for all existing saves.
   *
   * @return a list of {@link SaveMetadata}, empty if no saves exist
   * @throws IOException if the save index cannot be read
   */
  public List<SaveMetadata> getSavedGames() throws IOException {
    return gameSaveManager.listSaves();
  }

  private void ensureActiveSession() {
    if (currentSession == null) {
      throw new IllegalStateException("No active game session");
    }
  }
}
