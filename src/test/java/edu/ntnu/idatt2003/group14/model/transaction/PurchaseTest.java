package edu.ntnu.idatt2003.group14.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2003.group14.exception.InsufficientBalanceException;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.testutils.PlayerFactory;
import edu.ntnu.idatt2003.group14.testutils.ShareFactory;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseTest {
  private Share share;
  private Player player;

  @BeforeEach
  void instantiate_purchase() {
    this.share = ShareFactory.createShare();
    this.player = PlayerFactory.createPlayer();
  }

  @Test
  void verify_purchases_can_be_committed() throws InsufficientBalanceException {
    Purchase purchase = new Purchase(share, 3);
    assertFalse(purchase.isCommited());
    purchase.commit(player);
    assertTrue(purchase.isCommited());
  }

  @Test
  void verify_cannot_commit_purchase_with_insufficient_funds() throws InsufficientBalanceException {
    player.withdrawMoney(new BigDecimal("1000"));
    Purchase purchase = new Purchase(share, 3);
    assertThrows(InsufficientBalanceException.class, () -> purchase.commit(player));
  }

  @Test
  void verify_negative_week_purchase_is_not_allowed() {
    assertThrows(IllegalArgumentException.class, () -> new Purchase(share, -1));
  }


}
