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
 */
public class GameSession {
  private Player player = null;
  private Exchange exchange = null;

  /**
   * Sets the current player. If null is passed, player is cleared.
   *
   * @param player the player to set, or null to clear
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Sets the current exchange. If null is passed, exchange is cleared.
   *
   * @param exchange the exchange to set, or null to clear
   */
  public void setExchange(Exchange exchange) {
    this.exchange = exchange;
  }

  /**
   * Clears the current player.
   */
  public void clearPlayer() {
    this.player = null;
  }

  /**
   * Clears the current exchange.
   */
  public void clearExchange() {
    this.exchange = null;
  }

  /**
   * Gets the current player.
   *
   * @return an {@link Optional} containing the player, or empty if none is set
   */
  public Optional<Player> getPlayer() {
    return Optional.ofNullable(this.player);
  }

  /**
   * Gets the current exchange.
   *
   * @return an {@link Optional} containing the exchange, or empty if none is set
   */
  public Optional<Exchange> getExchange() {
    return Optional.ofNullable(this.exchange);
  }
}
