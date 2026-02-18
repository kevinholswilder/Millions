package edu.ntnu.idatt2003.group14.model.transaction;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents the class which is responsible for storing all transactions a player performs.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class TransactionArchive {
  private List<Transaction> transactions;

  /**
   * Instantiates a new transaction archive.
   */
  public TransactionArchive() {}

  /**
   * Adds a {@link Transaction} to the archive.
   *
   * @param transaction the transaction to add
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean add(Transaction transaction) {
    Objects.requireNonNull(transaction, "Transaction must not be null.");
    return transactions.add(transaction);
  }

  public boolean isEmpty() {
    return transactions.isEmpty();
  }

  /**
   * Returns all transactions for a given week.
   *
   * @param week the week to get the transactions for
   * @return the transactions for the given week
   */
  public List<Transaction> getTransactions(int week) {
    return this.transactions.stream()
        .filter(transaction -> transaction.getWeek() == week)
        .toList();
  }

  /**
   * Returns all purchases in the archive.
   *
   * @return all purchases
   */
  public List<Purchase> getPurchases() {
    return this.transactions.stream()
        .filter(Purchase.class::isInstance)
        .map(Purchase.class::cast)
        .toList();
  }

  /**
   * Returns all sales in the archive.
   *
   * @return all sales
   */
  public List<Sale> getSales() {
    return this.transactions.stream()
        .filter(Sale.class::isInstance)
        .map(Sale.class::cast)
        .toList();
  }

  /**
   * Returns the number of distinct week numbers in the archive.
   *
   * <p>A week is counted if it occurs at least once in the archive.</p>
   *
   * @return the number of weeks
   */
  public int countDistinctWeeks() {
    return this.transactions.stream()
        .map(Transaction::getWeek)
        .distinct()
        .toList()
        .size();
  }

}
