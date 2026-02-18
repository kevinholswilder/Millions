package edu.ntnu.idatt2003.group14.model;

import edu.ntnu.idatt2003.group14.exception.InsufficientBalanceException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class responsible for testing the {@link Player} class.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class PlayerTest {
  private Player player;

  @BeforeEach
  public void instantiate_player() {
    player = new Player(
        "Bernt",
        BigDecimal.valueOf(1000.0)
    );
  }

  @Test
  public void verify_player_name() {
    Assertions.assertEquals("Bernt", player.getName());
  }

  @Test
  public void verify_player_money() {
    Assertions.assertEquals(BigDecimal.valueOf(1000.0), player.getMoney());
  }

  @Test
  public void verify_deposit() {
    player.depositMoney(BigDecimal.valueOf(100.0));
    Assertions.assertEquals(BigDecimal.valueOf(1100.0), player.getMoney());
  }

  @Test
  public void verify_withdraw() {
    try {
      player.withdrawMoney(BigDecimal.valueOf(200.0));
    } catch (InsufficientBalanceException e) {
      System.err.println("Insufficient funds to withdraw." + e.getMessage());
    }
    Assertions.assertThrows(InsufficientBalanceException.class, () -> player.withdrawMoney(BigDecimal.valueOf(20000.0)));
    Assertions.assertEquals(BigDecimal.valueOf(800.0), player.getMoney());
  }

  // TODO: Add more tests.
}
