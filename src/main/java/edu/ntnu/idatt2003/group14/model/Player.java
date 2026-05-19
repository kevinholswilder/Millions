package edu.ntnu.idatt2003.group14.model;

import edu.ntnu.idatt2003.group14.config.lang.LangConfig;
import edu.ntnu.idatt2003.group14.exception.InsufficientBalanceException;
import edu.ntnu.idatt2003.group14.model.transaction.TransactionArchive;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a player.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public final class Player {
  private final String name;
  private final BigDecimal startingMoney;
  private BigDecimal money;
  private final Portfolio portfolio;
  private final TransactionArchive transactionArchive;

  /**
   * Instantiates a new player.
   *
   * @param name          the name of the player
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
   * @throws IllegalArgumentException     if the amount to withdraw is negative
   */
  public void withdrawMoney(BigDecimal amount) throws InsufficientBalanceException {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Cannot withdraw negative money.");
    }

    if (this.money.compareTo(amount) < 0) {
      throw new InsufficientBalanceException(
          LangConfig.getInstance().lang("purchase-stock-menu.error.insufficient_balance")
      );
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
    // potential division by zero
    if (startingMoney.compareTo(BigDecimal.ZERO) <= 0) {
      return PlayerStatus.NOVICE;
    }

    BigDecimal netWorth = getNetWorth();

    // Profit = (NetWorth - Start) / Start
    BigDecimal profitRatio = netWorth.subtract(startingMoney)
        .divide(startingMoney, 4, RoundingMode.HALF_UP);

    int weeksTraded = transactionArchive.countDistinctWeeks();

    boolean isSpeculator =
        weeksTraded >= 20 && profitRatio.compareTo(new BigDecimal("1.00")) >= 0; // 100%
    boolean isInvestor =
        weeksTraded >= 10 && profitRatio.compareTo(new BigDecimal("0.20")) >= 0;   // 20%

    if (isSpeculator) {
      return PlayerStatus.SPECULATOR;
    }
    if (isInvestor) {
      return PlayerStatus.INVESTOR;
    }
    return PlayerStatus.NOVICE;
  }
}
