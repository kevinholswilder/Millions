package edu.ntnu.idatt2003.group14.model;

import edu.ntnu.idatt2003.group14.testutils.ExchangeFactory;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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
    Assertions.assertEquals("New York Stock Exchange (NYSE)", exchange.getName());
  }

  @Test
  public void verify_exchange_stocks() {
    Assertions.assertTrue(exchange.hasStock("NOCH"));
    Assertions.assertTrue(exchange.hasStock("LUHO"));
    Assertions.assertFalse(exchange.hasStock("GOOG"));
    Assertions.assertFalse(exchange.hasStock(""));
  }

  @Test
  public void verify_week_of_exchange() {
    Assertions.assertEquals(1, exchange.getWeek());
  }

  @Test
  public void verify_search_stock() {
    Assertions.assertEquals(luxuriousHomes, exchange.getStock("LUHO"));
    Assertions.assertNull(exchange.getStock(""));
  }

  @Test
  public void verify_find_stock() {
    Assertions.assertEquals(List.of(nordicChairsStock), exchange.findStock("NOCH"));
  }

  @Test
  public void verify_get_gainers_size() {
    List<Stock> gainers = exchange.getGainers(2);
    Assertions.assertEquals(2, gainers.size());

    gainers = exchange.getGainers(1);
    Assertions.assertEquals(1, gainers.size());
  }

  @Test
  public void verify_get_gainers_order() {
    List<Stock> gainers = exchange.getGainers(2);

    Assertions.assertEquals(gainers.getFirst(), nordicChairsStock);
    Assertions.assertEquals(gainers.getLast(), luxuriousHomes);
  }

  @Test
  public void verify_get_losers_size() {
    List<Stock> losers = exchange.getLosers(2);
    Assertions.assertEquals(2, losers.size());

    losers = exchange.getLosers(1);
    Assertions.assertEquals(1, losers.size());
  }

  @Test
  public void verify_get_losers_order() {
    List<Stock> losers = exchange.getLosers(2);

    Assertions.assertEquals(losers.getFirst(), nordicChairsStock);
    Assertions.assertEquals(losers.getLast(), luxuriousHomes);}
}
