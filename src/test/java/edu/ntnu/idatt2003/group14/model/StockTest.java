package edu.ntnu.idatt2003.group14.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class responsible for testing the {@link Stock} class.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class StockTest {
  private Stock stock;

  @BeforeEach
  public void instantiate_stock() {
    stock = new Stock(
        "AAPL",
        "Apple Inc.",
        new ArrayList<>(Arrays.asList(
            BigDecimal.valueOf(100.00), BigDecimal.valueOf(101.00), BigDecimal.valueOf(102.00)
        ))
    );
  }

  @Test
  public void empty_prices_list_throws_exception() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        new Stock("AAPL", "Apple Inc.", List.of());
    });

    String expectedMessage = "List of prices can not be empty";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void verify_stock_symbol() {
    Assertions.assertEquals("AAPL", stock.getSymbol());
  }

  @Test
  public void verify_stock_company() {
    Assertions.assertEquals("Apple Inc.", stock.getCompany());
  }

  @Test
  public void verify_stock_sales_price() {
    Assertions.assertEquals(BigDecimal.valueOf(102.00), stock.getSalesPrice());

    // Add new price.
    stock.addNewSalesPrice(BigDecimal.valueOf(103.00));
    Assertions.assertEquals(BigDecimal.valueOf(103.00), stock.getSalesPrice());
  }

  @Test
  public void verify_historical_prices() {
    Assertions.assertEquals(stock.getHistoricalPrices(),
        new ArrayList<>(Arrays.asList(
            BigDecimal.valueOf(100.00), BigDecimal.valueOf(101.00), BigDecimal.valueOf(102.00)
        ))
    );
  }

  @Test
  public void verify_highest_price() {
    Assertions.assertEquals(BigDecimal.valueOf(102.00), stock.getHighestPrice());
  }

  @Test
  public void verify_lowest_price() {
    Assertions.assertEquals(BigDecimal.valueOf(100.00), stock.getLowestPrice());
  }

  @Test
  public void verify_latest_price_change() {
    Assertions.assertEquals(BigDecimal.valueOf(1.00), stock.getLatestPriceChange());
  }

}
