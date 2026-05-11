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
  private GameSession session;
  private Player player;
  private Exchange exchange;

  @BeforeEach
  void instantiate_game_session() {
    this.session = new GameSession();
    this.player = PlayerFactory.createPlayer();
    this.exchange = ExchangeFactory.createExchange();
  }

  @Test
  void verify_holds_player() {
    assertFalse(session.getPlayer().isPresent());

    session.setPlayer(player);
    assertTrue(session.getPlayer().isPresent());
    assertEquals(player, session.getPlayer().orElseThrow());

    session.clearPlayer();
    assertFalse(session.getPlayer().isPresent());
  }

  @Test
  void verify_holds_exchange() {
    assertFalse(session.getExchange().isPresent());

    session.setExchange(exchange);
    assertTrue(session.getExchange().isPresent());
    assertEquals(exchange, session.getExchange().orElseThrow());

    session.clearExchange();
    assertFalse(session.getExchange().isPresent());
  }
}
