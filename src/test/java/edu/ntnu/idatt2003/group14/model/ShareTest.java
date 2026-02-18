package edu.ntnu.idatt2003.group14.model;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class responsible for testing the {@link Share} class.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class ShareTest {
  private Share share;

  @BeforeEach
  public void instantiate_share() {
    share = new Share(
        new Stock("AAPL", "Apple Inc.", List.of(BigDecimal.valueOf(100.0))),
        BigDecimal.valueOf(10),
        BigDecimal.valueOf(100)
    );
  }

  @Test
  public void verify_share_price() {
    Assertions.assertEquals(BigDecimal.valueOf(100), share.getPurchasePrice());
  }

  @Test
  public void verify_share_quantity() {
    Assertions.assertEquals(BigDecimal.valueOf(10), share.getQuantity());
  }

  @Test
  public void verify_share_stock() {
    Assertions.assertEquals("AAPL", share.getStock().getSymbol());
  }
}
