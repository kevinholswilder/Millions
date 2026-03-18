package edu.ntnu.idatt2003.group14.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group14.exception.InsufficientBalanceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
  public void verify_negative_money_throws_exception() {
    assertThrows(IllegalArgumentException.class, () -> new Player("Bernt", BigDecimal.valueOf(-1000.0)));

    assertThrows(IllegalArgumentException.class, () -> player.depositMoney(BigDecimal.valueOf(-1000.0)));

    assertThrows(IllegalArgumentException.class, () -> player.withdrawMoney(BigDecimal.valueOf(-1000.0)));
  }

  @Test
  public void verify_player_name() {
    assertEquals("Bernt", player.getName());
  }

  @Test
  public void verify_player_money() {
    assertEquals(BigDecimal.valueOf(1000.0), player.getMoney());
  }

  @Test
  public void verify_deposit() {
    player.depositMoney(BigDecimal.valueOf(100.0));
    assertEquals(BigDecimal.valueOf(1100.0), player.getMoney());
  }

  @Test
  public void verify_withdraw() {
    try {
      player.withdrawMoney(BigDecimal.valueOf(200.0));
    } catch (InsufficientBalanceException e) {
      System.err.println("Insufficient funds to withdraw." + e.getMessage());
    }
    assertThrows(InsufficientBalanceException.class, () -> player.withdrawMoney(BigDecimal.valueOf(20000.0)));
    assertEquals(BigDecimal.valueOf(800.0), player.getMoney());
  }

  @Test
  void getNetWorth_returns_the_total_net_worth() {
    assertEquals(BigDecimal.valueOf(1000.0), player.getNetWorth());

    List<BigDecimal> stockValues = new ArrayList<>();
    stockValues.add(new BigDecimal("150"));

    Stock testStock = new Stock("tst", "Test Inc.", stockValues);
    player.getPortfolio().addShare(new Share(testStock, new BigDecimal("1"), new BigDecimal("100")));

    /*
     // portfolio:
     gross = 150 × 1 = 150
     commission = 1% of gross = 150 × 0.01 = 1.5
     profit = 150 − 1.5 − 100 = 48.5
     tax = 30% of profit = 48.5 × 0.30 = 14.55
     portfolio total = 150 − 1.5 − 14.55 = 133.95
     // player total:
     player total = 133.95 + 1000
     */
    BigDecimal expected = new BigDecimal("1133.95");

    BigDecimal actual = player.getNetWorth();

    assertEquals(0, actual.compareTo(expected));
  }
}
