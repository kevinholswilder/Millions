package edu.ntnu.idatt2003.group14.model;

/**
 * Holds the current objects and options required at runtime.
 *
 * <p>These options change during normal running.
 * Any persistent options should live in {@link edu.ntnu.idatt2003.group14.GameConfig}.</p>
 *
 * <p>The player and exchange are expected to be assigned before the game starts.
 * Accessing them before assignment will throw an {@link IllegalStateException}.</p>
 *
 * @author Elias Haugsbakk, Kevin Holswilder
 * @since 0.0.1
 */
public class GameSession {
  private static Player player = null;
  private static Exchange exchange = null;

  // Prevent instantiation
  private GameSession() {}

  /**
   * Sets the current player. If null is passed, player is cleared.
   *
   * @param player the player to set, or null to clear
   */
  public static void setPlayer(Player player) {
    GameSession.player = player;
  }

  /**
   * Sets the current exchange. If null is passed, exchange is cleared.
   *
   * @param exchange the exchange to set, or null to clear
   */
  public static void setExchange(Exchange exchange) {
    GameSession.exchange = exchange;
  }

  /**
   * Clears the current player.
   */
  public static void clearPlayer() {
    GameSession.player = null;
  }

  /**
   * Clears the current exchange.
   */
  public static void clearExchange() {
    GameSession.exchange = null;
  }

  /**
   * Gets the current player.
   *
   * @return the current player
   * @throws IllegalStateException if no player has been assigned to the session
   */
  public static Player getPlayer() {
    if (player == null) {
      throw new IllegalStateException("No player assigned to the current session.");
    }
    return player;
  }

  /**
   * Gets the current exchange.
   *
   * @return the current exchange
   * @throws IllegalStateException if no exchange has been assigned to the session
   */
  public static Exchange getExchange() {
    if (exchange == null) {
      throw new IllegalStateException("No exchange assigned to the current session.");
    }
    return exchange;
  }
}
