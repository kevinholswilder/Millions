package edu.ntnu.idatt2003.group14.model;

import edu.ntnu.idatt2003.group14.exception.InsufficientBalanceException;
import edu.ntnu.idatt2003.group14.model.transaction.TransactionArchive;
import java.math.BigDecimal;

/**
 * Represents a player.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class Player {
  private final String name;
  private final BigDecimal startingMoney;
  private BigDecimal money;
  private final Portfolio portfolio;
  private final TransactionArchive transactionArchive;

  /**
   * Instantiates a new player.
   *
   * @param name the name of the player
   * @param startingMoney the amount of money the player is starting with
   */
  public Player(String name, BigDecimal startingMoney) {
    if (startingMoney.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Cannot start with negative money.");
    }

    this.name = name;
    this.startingMoney = startingMoney;
    this.money = startingMoney;
    this.portfolio = new Portfolio();
    this.transactionArchive = new TransactionArchive();
  }

  public String getName() {
    return this.name;
  }

  public BigDecimal getMoney() {
    return this.money;
  }

  /**
   * Add money to the user.
   *
   * @param amount the amount of money to deposit
   * @throws IllegalArgumentException if the amount to deposit is negative
   */
  public void depositMoney(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Cannot deposit negative money.");
    }
    this.money = this.money.add(amount);
  }

  /**
   * Withdraw money from the user.
   *
   * @param amount the amount of money to withdraw
   * @throws InsufficientBalanceException if the player does not have enough money to withdraw
   * @throws IllegalArgumentException if the amount to withdraw is negative
   */
  public void withdrawMoney(BigDecimal amount) throws InsufficientBalanceException {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Cannot withdraw negative money.");
    }

    if (this.money.compareTo(amount) < 0) {
      throw new InsufficientBalanceException("You do not have enough money to withdraw.");
    }

    this.money = this.money.subtract(amount);
  }

  public Portfolio getPortfolio() {
    return this.portfolio;
  }

  public TransactionArchive getTransactionArchive() {
    return this.transactionArchive;
  }

  public BigDecimal getNetWorth() {
    return this.portfolio.getNetWorth().add(this.money);
  }

  /**
   * Returns the player status based on their profit and their amount of
   * time in the market.
   * The status is returned as the enumerator: {@link PlayerStatus}.
   *
   * @return the player status
   */
  public PlayerStatus getStatus() {
    // converting to double for faster calculation
    // the small imprecision is insignificant
    double netWorth = getNetWorth().doubleValue();
    double start = startingMoney.doubleValue();
    double profitPercentage = ((netWorth - start) / start) * 100;
    int weeksTraded = transactionArchive.countDistinctWeeks();

    if (weeksTraded >= 20 && profitPercentage >= 100) {
      return PlayerStatus.SPECULATOR;
    } else if (weeksTraded >= 10 && profitPercentage >= 20) {
      return PlayerStatus.INVESTOR;
    } else {
      return PlayerStatus.NOVICE;
    }
  }
}
