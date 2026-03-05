package edu.ntnu.idatt2003.group14.model;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class responsible for testing the {@link Exchange} class.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class ExchangeTest {
  private Exchange exchange;
  private Stock appleStock;
  private Stock microsoftStock;

  @BeforeEach
  public void instantiate_exchange() {
    appleStock = new Stock("AAPL", "Apple Inc.", List.of(
        BigDecimal.valueOf(100.00),
        BigDecimal.valueOf(150.0),
        BigDecimal.valueOf(60.0)
    ));

    microsoftStock = new Stock("MSFT", "Microsoft Corporation", List.of(
        BigDecimal.valueOf(120.5),
        BigDecimal.valueOf(53.40),
        BigDecimal.valueOf(341.40)
    ));

    exchange = new Exchange(
        "New York Stock Exchange (NYSE)",
        List.of(
            appleStock,
            microsoftStock
        )
    );
  }

  @Test
  public void verify_exchange_name() {
    Assertions.assertEquals("New York Stock Exchange (NYSE)", exchange.getName());
  }

  @Test
  public void verify_exchange_stocks() {
    Assertions.assertTrue(exchange.hasStock("AAPL"));
    Assertions.assertTrue(exchange.hasStock("MSFT"));
    Assertions.assertFalse(exchange.hasStock("GOOG"));
    Assertions.assertFalse(exchange.hasStock(""));
  }

  @Test
  public void verify_week_of_exchange() {
    Assertions.assertEquals(0, exchange.getWeek());
  }

  @Test
  public void verify_search_stock() {
    Assertions.assertEquals(microsoftStock, exchange.getStock("MSFT"));
    Assertions.assertNull(exchange.getStock(""));
  }

  @Test
  public void verify_find_stock() {
    Assertions.assertEquals(List.of(appleStock), exchange.findStock("AAPL"));
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

    Assertions.assertEquals(gainers.getFirst(), microsoftStock);
    Assertions.assertEquals(gainers.getLast(), appleStock);
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

    Assertions.assertEquals(losers.getFirst(), appleStock);
    Assertions.assertEquals(losers.getLast(), microsoftStock);}
}
