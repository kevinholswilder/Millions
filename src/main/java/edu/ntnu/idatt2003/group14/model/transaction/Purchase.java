package edu.ntnu.idatt2003.group14.model.transaction;

import edu.ntnu.idatt2003.group14.api.calculator.TransactionCalculator;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.model.Share;
import java.math.BigDecimal;

/**
 * Represents a purchase of stocks.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public final class Purchase extends Transaction {
  /**
   * Constructor for Transaction.
   *
   * @param share the share to buy/sell
   * @param week the week of the transaction
   */
  public Purchase(Share share, int week) {
    super(share, week, null);
  }

  /**
   * Commits the transaction of a purchase.
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

    // Withdraw money from player
    TransactionCalculator calculator = this.getCalculator();
    BigDecimal totalCosts = calculator.calculateTotal();
    player.withdrawMoney(totalCosts);

    // Add a share to the player's portfolio
    Share share = this.getShare();
    player.getPortfolio().addShare(share);

    // Add transaction to player's transaction archive'
    player.getTransactionArchive().add(this);
  }
}
