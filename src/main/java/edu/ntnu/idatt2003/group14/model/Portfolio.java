package edu.ntnu.idatt2003.group14.model;

import edu.ntnu.idatt2003.group14.calculator.SaleCalculator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a portfolio of shares.
 *
 * @author Kevin Holswilder, Elias Haugsbakk
 * @since 0.0.1
 */
public class Portfolio {
  private final List<Share> shares;

  /**
   * Instantiates a new portfolio.
   */
  public Portfolio() {
    this.shares = new ArrayList<>();
  }

  /**
   * Adds a {@link Share} to the portfolio.
   *
   * @param share the share to add
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean addShare(Share share) {
    return this.shares.add(share);
  }

  /**
   * Removes a {@link Share} from the portfolio.
   *
   * @param share the share to remove
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean removeShare(Share share) {
    return this.shares.remove(share);
  }

  public List<Share> getShares() {
    return this.shares;
  }

  /**
   * Returns all shares owned by a company with the given symbol.
   *
   * @param symbol the symbol of the company
   * @return the shares owned by the company
   */
  public List<Share> getShares(String symbol) {
    return this.shares.stream()
        .filter(share -> share.getStock().getSymbol().equalsIgnoreCase(symbol))
        .toList();
  }

  /**
   * Checks if the portfolio contains the specified {@link Share}.
   *
   * @param share the share to check
   * @return true if the portfolio contains the share, false otherwise
   */
  public boolean contains(Share share) {
    return this.shares.contains(share);
  }

  /**
   * Calculates and returns the total sales value of all shares in the portfolio.
   *
   * @return the total sales value of all shares
   */
  public BigDecimal getNetWorth() {
    return this.shares.stream()
        .map(SaleCalculator::new)
        .map(SaleCalculator::calculateTotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
