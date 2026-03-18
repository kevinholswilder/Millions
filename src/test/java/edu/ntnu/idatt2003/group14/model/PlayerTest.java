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

  @Test
  void getPlayerStatus_returns_the_expected_player_status() {
    List<BigDecimal> stockValues = new ArrayList<>();
    stockValues.add(new BigDecimal("1"));
    Stock testStock = new Stock("tst", "Test Inc.", stockValues);
    Exchange exchange = new Exchange("test exchange", List.of(testStock));

    assertEquals(PlayerStatus.NOVICE, player.getStatus());

    // Advance to week 10 with purchases every week.
    for (int i = 0; i < 10; i++) {
      exchange.buy("tst", new BigDecimal("1"), player);
      exchange.advance();
    }

    assertEquals(PlayerStatus.NOVICE, player.getStatus());

    // Deposit 400 money to increase player profit to above 20%.
    player.depositMoney(BigDecimal.valueOf(313.0));

    assertEquals(PlayerStatus.INVESTOR, player.getStatus());

    // Advance to week 20.
    for (int i = 0; i < 10; i++) {
      exchange.buy("tst", new BigDecimal("1"), player);
      exchange.advance();
    }

    assertEquals(PlayerStatus.INVESTOR, player.getStatus());

    // Deposit 1000 money to increase player profit to above 100%.
    player.depositMoney(BigDecimal.valueOf(1000.0));

    assertEquals(PlayerStatus.SPECULATOR, player.getStatus());
  }
}
