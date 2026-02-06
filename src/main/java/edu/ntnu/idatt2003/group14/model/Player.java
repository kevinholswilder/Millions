package edu.ntnu.idatt2003.group14.model;

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
  // TODO: private final TransactionArchive transactionArchive;

  /**
   * Instantiates a new player.
   *
   * @param name the name of the player
   * @param startingMoney the amount of money the player is starting with
   */
  public Player(String name, BigDecimal startingMoney) {
    this.name = name;
    this.startingMoney = startingMoney;
    this.money = startingMoney;
    this.portfolio = new Portfolio();
    // TODO: this.transactionArchive = new TransactionArchive();
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
   * @param amount the amount of money to add
   */
  public void addMoney(BigDecimal amount) {
    // TODO: Verify positive amount
    this.money = this.money.add(amount);
  }

  /**
   * Withdraw money from the user.
   *
   * @param amount the amount of money to withdraw
   */
  public void withdrawManet(BigDecimal amount) {
    // TODO: Verify positive amount
    this.money = this.money.subtract(amount);
  }

  public Portfolio getPortfolio() {
    return this.portfolio;
  }

  /* TODO:
  public TransactionArchive getTransactionArchive() {
    return this.transactionArchive;
  }
  */

}
