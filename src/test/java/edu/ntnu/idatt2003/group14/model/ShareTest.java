package edu.ntnu.idatt2003.group14.model;

import edu.ntnu.idatt2003.group14.testutils.ShareFactory;
import java.math.BigDecimal;
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
    share = ShareFactory.createShare();
  }

  @Test
  public void verify_share_price() {
    Assertions.assertEquals(BigDecimal.valueOf(5), share.getPurchasePrice());
  }

  @Test
  public void verify_share_quantity() {
    Assertions.assertEquals(BigDecimal.valueOf(3), share.getQuantity());
  }

  @Test
  public void verify_share_stock() {
    Assertions.assertEquals("AMCH", share.getStock().getSymbol());
  }
}
