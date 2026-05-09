package edu.ntnu.idatt2003.group14.model.plottable;

import java.math.BigDecimal;

/**
 * Interface for objects that listen to changes in a {@link Plottable}.
 * Implementing classes will be notified whenever the plottable's net worth changes.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public interface PlottableChangeListener {

  /**
   * Called when the plottable's net worth changes.
   * Implementing classes should update their state or UI accordingly.
   *
   * @param value the new value of the plottable
   */
  void plottableChanged(BigDecimal value);
}
