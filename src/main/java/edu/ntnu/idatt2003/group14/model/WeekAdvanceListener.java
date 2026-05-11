package edu.ntnu.idatt2003.group14.model;

/**
 * Interface for objects listening to week advances in a {@link Exchange}.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public interface WeekAdvanceListener {

  /**
   * Called when the week advances.
   *
   * @param week the new week number
   */
  void onWeekAdvanced(int week);
}
