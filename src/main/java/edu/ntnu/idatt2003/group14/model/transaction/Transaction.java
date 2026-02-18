package edu.ntnu.idatt2003.group14.model.transaction;

import edu.ntnu.idatt2003.group14.api.calculator.TransactionCalculator;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.model.Share;

/**
 * Abstract base class representing a transaction between a player and a share.
 *
 * <p>This class provides shared state and common behavior for specific transaction types,
 * such as purchases and sales.</p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public abstract class Transaction {
  private final Share share;
  private final int week;
  private final TransactionCalculator calculator;
  private boolean commited;

  /**
   * Instantiates a new transaction.
   *
   * @param share the share to buy/sell
   * @param week the week of the transaction
   * @param calculator the calculator to use for calculating the transaction cost
   */
  public Transaction(Share share, int week, TransactionCalculator calculator) {
    if (week > 0) {
      throw new IllegalArgumentException("Week number cannot be negative");
    }

    this.share = share;
    this.week = week;
    this.calculator = calculator;
  }

  public Share getShare() {
    return this.share;
  }

  public int getWeek() {
    return this.week;
  }

  public TransactionCalculator getCalculator() {
    return this.calculator;
  }

  public boolean isCommited() {
    return this.commited;
  }

  /**
   * Commits the transaction.
   *
   * @param player the player who owns the share
   */
  public void commit(Player player) {
    this.commited = true;
  }
}
