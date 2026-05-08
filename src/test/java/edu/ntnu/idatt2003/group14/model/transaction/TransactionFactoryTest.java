package edu.ntnu.idatt2003.group14.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group14.exception.transaction.UnknownTransactionException;
import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.testutils.ShareFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionFactoryTest {
  private Share share;

  @BeforeEach
  void initialize_transaction_factory() {
    share = ShareFactory.createShare();
  }

  @Test
  void verify_enum_creation() {
    Transaction sale = TransactionFactory.get(
        TransactionType.SALE,
        share,
        3
    );

    Transaction purchase = TransactionFactory.get(
        TransactionType.PURCHASE,
        share,
        3
    );
    assertEquals(share, sale.getShare());
    assertEquals(share, purchase.getShare());
  }

  @Test
  void verify_string_creation() throws UnknownTransactionException {
    Transaction sale = TransactionFactory.get(
        "sale",
        share,
        3
    );

    Transaction purchase = TransactionFactory.get(
        "Purchase",
        share,
        3
    );
    assertEquals(share, sale.getShare());
    assertEquals(share, purchase.getShare());
  }

  @Test
  void verify_throws_on_unknown_string() {
    assertThrows(IllegalArgumentException.class, () -> TransactionFactory.get(
        "UNKNOWN TYPE",
        share,
        3
    ));
  }
}
