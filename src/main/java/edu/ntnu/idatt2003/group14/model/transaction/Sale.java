package edu.ntnu.idatt2003.group14.model.transaction;

import edu.ntnu.idatt2003.group14.api.calculator.TransactionCalculator;
import edu.ntnu.idatt2003.group14.calculator.SaleCalculator;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.model.Share;
import java.math.BigDecimal;

/**
 * Represents a sale of stocks.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public final class Sale extends Transaction {
  /**
   * Constructor for Transaction.
   *
   * @param share the share to buy/sell
   * @param week the week of the transaction
   */
  public Sale(Share share, int week) {
    super(share, week, new SaleCalculator(share));
  }

  /**
   * Commits the transaction of a sale.
   *
   * <p>This will perform the following actions:</p>
   * <ol>
   *   <li>Deposit money to the player's account</li>
   *   <li>Remove a share from the player's portfolio</li>
   *   <li>Save the transaction to the player's transaction archive</li>
   * </ol>
   *
   * @param player the player who owns the share
   */
  @Override
  public void commit(Player player) {
    super.commit(player);

    // Add money to the player's account
    TransactionCalculator calculator = this.getCalculator();
    BigDecimal surplus = calculator.calculateTotal();
    player.depositMoney(surplus);

    // Remove a share from the player's portfolio
    Share share = this.getShare();
    player.getPortfolio().removeShare(share);

    // Add transaction to the player's transaction archive
    player.getTransactionArchive().add(this);
  }
}
