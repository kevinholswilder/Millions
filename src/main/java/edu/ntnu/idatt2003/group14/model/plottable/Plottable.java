package edu.ntnu.idatt2003.group14.model.plottable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that provides observer pattern functionality for plottable changes.
 * Subclasses can notify listeners when the plottable's total value changes.
 *
 * <p>A Plottable will make its price history available though a valueForWeek method. As well as
 * implementing an Observer pattern by notifying listeners of price updates.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public abstract class Plottable {
  final List<PlottableChangeListener> plottableChangeListeners;

  /**
   * Initializes the plottable with an empty listener list.
   */
  public Plottable() {
    this.plottableChangeListeners = new ArrayList<>();
  }

  /**
   * Registers a listener to be notified of total value changes.
   *
   * @param plottableChangeListener the listener to add
   */
  public void addListener(PlottableChangeListener plottableChangeListener) {
    plottableChangeListeners.add(plottableChangeListener);
  }

  /**
   * Unregisters a listener from plottable change notifications.
   *
   * @param plottableChangeListener the listener to remove
   */
  public void removeListener(PlottableChangeListener plottableChangeListener) {
    plottableChangeListeners.remove(plottableChangeListener);
  }

  /**
   * Notifies all registered listeners of a change in total value.
   *
   * @param value the new net worth of the portfolio
   */
  public void notifyNetWorthListeners(
      BigDecimal value) {
    plottableChangeListeners.forEach(
        listener ->
            listener.plottableChanged(value));
  }

  /**
   * Provides the total value of the plottable for a given week.
   *
   * <p>The value provided should be representative a representative value of the unit
   * at the given week. Fees should not be included in the calculation.</p>
   *
   * @param week the week to fetch value for
   * @return the value of the plottable
   */
  public abstract BigDecimal getValueForWeek(int week);

  /**
   * Provides the total number of weeks of data the plottable contains.
   *
   * @return the week count
   */
  public abstract int getWeek();
}
