package edu.ntnu.idatt2003.group14.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    assertFalse(GameSession.getPlayer().isPresent());

    GameSession.setPlayer(player);
    assertTrue(GameSession.getPlayer().isPresent());
    assertEquals(player, GameSession.getPlayer().orElseThrow());

    GameSession.clearPlayer();
    assertFalse(GameSession.getPlayer().isPresent());
  }

  @Test
  void verify_holds_exchange() {
    assertFalse(GameSession.getExchange().isPresent());

    GameSession.setExchange(exchange);
    assertTrue(GameSession.getExchange().isPresent());
    assertEquals(exchange, GameSession.getExchange().orElseThrow());

    GameSession.clearExchange();
    assertFalse(GameSession.getExchange().isPresent());
  }
}
