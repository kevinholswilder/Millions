package edu.ntnu.idatt2003.group14.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2003.group14.exception.InsufficientBalanceException;
import edu.ntnu.idatt2003.group14.testutils.ExchangeFactory;
import edu.ntnu.idatt2003.group14.testutils.PlayerFactory;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class responsible for testing the {@link Exchange} class.
 *
 * @author Kevin Holswilder, Elias Haugsbakk
 * @since 0.0.1
 */
public class ExchangeTest {
  private Exchange exchange;
  private Stock nordicChairsStock;
  private Stock luxuriousHomes;

  @BeforeEach
  public void instantiate_exchange() {
    exchange = ExchangeFactory.createExchange();
    nordicChairsStock = exchange.getStock("NOCH");
    luxuriousHomes = exchange.getStock("LUHO");
  }

  @Test
  public void verify_exchange_name() {
    assertEquals("New York Stock Exchange (NYSE)", exchange.getName());
  }

  @Test
  public void verify_exchange_stocks() {
    assertTrue(exchange.hasStock("NOCH"));
    assertTrue(exchange.hasStock("LUHO"));
    assertFalse(exchange.hasStock("GOOG"));
    assertFalse(exchange.hasStock(""));
  }

  @Test
  public void verify_week_of_exchange() {
    assertEquals(0, exchange.getWeek());
  }

  @Test
  public void verify_search_stock() {
    assertEquals(luxuriousHomes, exchange.getStock("LUHO"));
    assertNull(exchange.getStock(""));
  }

  @Test
  public void verify_find_stock() {
    assertEquals(List.of(nordicChairsStock), exchange.findStock("NOCH"));
  }

  @Test
  public void verify_get_gainers_size() {
    List<Stock> gainers = exchange.getGainers(2);
    assertEquals(2, gainers.size());

    gainers = exchange.getGainers(1);
    assertEquals(1, gainers.size());
  }

  @Test
  public void verify_get_gainers_order() {
    List<Stock> gainers = exchange.getGainers(2);

    assertEquals(gainers.getFirst(), nordicChairsStock);
    assertEquals(gainers.getLast(), luxuriousHomes);
  }

  @Test
  public void verify_get_losers_size() {
    List<Stock> losers = exchange.getLosers(2);
    assertEquals(2, losers.size());

    losers = exchange.getLosers(1);
    assertEquals(1, losers.size());
  }

  @Test
  public void verify_get_losers_order() {
    List<Stock> losers = exchange.getLosers(2);

    assertEquals(losers.getFirst(), nordicChairsStock);
    assertEquals(losers.getLast(), luxuriousHomes);
  }

  @Test
  void verify_purchasing_of_shares() throws InsufficientBalanceException {
    Player player = PlayerFactory.createPlayer();
    assertEquals(new BigDecimal("1000.0"), player.getMoney());
    assertTrue(player.getPortfolio().getShares().isEmpty());

    exchange.buy("NOCH", new BigDecimal("10"), player);

    assertFalse(player.getPortfolio().getShares().isEmpty());
    // cost of 50 + fees of 2.5 (0.05%)
    assertEquals(new BigDecimal("949.750"), player.getMoney());
  }

  @Test
  void verify_selling_of_shares() throws InsufficientBalanceException {
    Player player = PlayerFactory.createPlayer();
    assertEquals(new BigDecimal("1000.0"), player.getMoney());
    assertTrue(player.getPortfolio().getShares().isEmpty());

    exchange.buy("NOCH", new BigDecimal("10"), player);

    assertFalse(player.getPortfolio().getShares().isEmpty());
    // cost of 50 + fees of 2.5 (0.05%)
    assertEquals(new BigDecimal("949.750"), player.getMoney());

    exchange.sell(player.getPortfolio().getShares().getFirst(), player);

    assertTrue(player.getPortfolio().getShares().isEmpty());
    // 947.5 + 50 + 1% commission rate
    assertEquals(new BigDecimal("999.250"), player.getMoney());
  }

  @Test
  void varify_proceed_to_next_week() {
    assertEquals(0, exchange.getWeek());
    assertEquals(5, exchange.getStock("NOCH").getHistoricalPrices().size());

    exchange.advance();

    assertEquals(1, exchange.getWeek());
    assertEquals(6, exchange.getStock("NOCH").getHistoricalPrices().size());
  }

  @Test
  void verify_getValueForWeek() {
    exchange = ExchangeFactory.createExchangeNonUniformPrices();
    assertEquals(new BigDecimal("1000.0000"), exchange.getValueForWeek(0));
    assertEquals(new BigDecimal("1500.0000"), exchange.getValueForWeek(1));
    assertEquals(new BigDecimal("2000.0000"), exchange.getValueForWeek(2));
  }
}
