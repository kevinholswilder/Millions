package edu.ntnu.idatt2003.group14.model.portfolio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that provides observer pattern functionality for portfolio changes.
 * Subclasses can notify listeners when the portfolio's net worth changes.
 */
public abstract class ObservablePortfolio {
  final List<PortfolioChangeListener> portfolioChangeListeners;

  /**
   * Initializes the observable portfolio with an empty listener list.
   */
  public ObservablePortfolio() {
    this.portfolioChangeListeners = new ArrayList<>();
  }

  /**
   * Registers a listener to be notified of portfolio changes.
   *
   * @param portfolioChangeListener the listener to add
   */
  public void addListener(PortfolioChangeListener portfolioChangeListener) {
    portfolioChangeListeners.add(portfolioChangeListener);
  }

  /**
   * Unregisters a listener from portfolio change notifications.
   *
   * @param portfolioChangeListener the listener to remove
   */
  public void removeListener(PortfolioChangeListener portfolioChangeListener) {
    portfolioChangeListeners.remove(portfolioChangeListener);
  }

  /**
   * Notifies all registered listeners of a change in net worth.
   *
   * @param netWorth the new net worth of the portfolio
   */
  public void notifyNetWorthListeners(
      BigDecimal netWorth) {
    portfolioChangeListeners.forEach(
        portfolioChangeListener ->
            portfolioChangeListener.portfolioChanged(netWorth));
  }
}
