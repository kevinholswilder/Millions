package edu.ntnu.idatt2003.group14.model;

import java.util.Optional;

/**
 * Holds the current objects and options required at runtime.
 *
 * <p>These options change during normal running.
 * Any persistent options should live in {@link edu.ntnu.idatt2003.group14.GameConfig}.</p>
 *
 * <p>The player and exchange are returned as {@link Optional} to enforce explicit null handling
 * in the UI layer.
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
   * @return an {@link Optional} containing the player, or empty if none is set
   */
  public static Optional<Player> getPlayer() {
    return Optional.ofNullable(GameSession.player);
  }

  /**
   * Gets the current exchange.
   *
   * @return an {@link Optional} containing the exchange, or empty if none is set
   */
  public static Optional<Exchange> getExchange() {
    return Optional.ofNullable(GameSession.exchange);
  }
}
