package edu.ntnu.idatt2003.group14.model;

/**
 * Represents a session of a game, containing the player and the exchange.
 *
 * @param player   the player in the session
 * @param exchange the exchange in the session
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public record GameSession(Player player, Exchange exchange) {
}
