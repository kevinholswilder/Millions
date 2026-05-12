package edu.ntnu.idatt2003.group14.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group14.testutils.ExchangeFactory;
import edu.ntnu.idatt2003.group14.testutils.PlayerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class responsible for testing the {@link GameSession} class.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class GameSessionTest {
  private Player player;
  private Exchange exchange;

  @BeforeEach
  void instantiate_game_GameSession() {
    this.player = PlayerFactory.createPlayer();
    this.exchange = ExchangeFactory.createExchange();
  }

  @Test
  void verify_holds_player() {
    assertThrows(IllegalStateException.class, GameSession::getPlayer);

    GameSession.setPlayer(player);
    assertDoesNotThrow(GameSession::getPlayer);
    assertEquals(player, GameSession.getPlayer());

    GameSession.clearPlayer();
    assertThrows(IllegalStateException.class, GameSession::getPlayer);
  }

  @Test
  void verify_holds_exchange() {
    assertThrows(IllegalStateException.class, GameSession::getExchange);

    GameSession.setExchange(exchange);
    assertDoesNotThrow(GameSession::getExchange);
    assertEquals(exchange, GameSession.getExchange());

    GameSession.clearExchange();
    assertThrows(IllegalStateException.class, GameSession::getExchange);
  }
}
