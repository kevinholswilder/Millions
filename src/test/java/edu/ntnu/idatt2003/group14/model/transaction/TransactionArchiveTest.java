package edu.ntnu.idatt2003.group14.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.testutils.ShareFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionArchiveTest {
  TransactionArchive transactionArchive;

  @BeforeEach
  void initiate_transaction_archive() {
    transactionArchive = new TransactionArchive();
  }

  @Test
  void verify_is_empty() {
    assertTrue(transactionArchive.isEmpty());
    transactionArchive.add(new Sale(
        ShareFactory.createShare(),
        3
    ));
    assertFalse(transactionArchive.isEmpty());
  }

  @Test
  void verify_get_transactions() {
    assertTrue(transactionArchive.isEmpty());
    var shares = ShareFactory.createShares(5);
    for (Share s : shares) {
      transactionArchive.add(new Sale(s, 3));
      transactionArchive.add(new Purchase(s, 3));
    }
    assertEquals(10, transactionArchive.getTransactions(3).size());
    assertEquals(5, transactionArchive.getSales().size());
    assertEquals(5, transactionArchive.getPurchases().size());
  }
}
