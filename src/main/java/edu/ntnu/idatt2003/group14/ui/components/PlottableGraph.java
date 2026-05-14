package edu.ntnu.idatt2003.group14.ui.components;

import edu.ntnu.idatt2003.group14.model.plottable.Plottable;
import edu.ntnu.idatt2003.group14.model.plottable.PlottableChangeListener;
import java.math.BigDecimal;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;

/**
 * A line chart that displays the net worth of a plottable over time.
 * Listens to the {@link Plottable} and updates automatically when the net worth changes.
 * Historical values are displayed on creation if available.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class PlottableGraph extends AreaChart<Number, Number> implements PlottableChangeListener {
  private static final int MAX_WEEKS = 20;
  private final Series<Number, Number> series;
  private final Plottable plottable;

  /**
   * Instantiates a new PlottableGraph for the given plottable.
   *
   * @param plottable the plottable whose net worth is displayed
   */
  public PlottableGraph(Plottable plottable) {
    super(createXAxis(), new NumberAxis());
    this.plottable = plottable;
    this.setLegendVisible(false);
    this.setAnimated(false);
    this.setCreateSymbols(false);

    this.series = new Series<>();
    this.getData().add(series);

    populateChart(plottable);
    plottable.addListener(this);
  }

  private static NumberAxis createXAxis() {
    NumberAxis axis = new NumberAxis();
    axis.setAutoRanging(false);
    axis.setLowerBound(1);
    axis.setUpperBound(MAX_WEEKS);
    axis.setTickUnit(1);
    axis.setTickMarkVisible(false);
    axis.setMinorTickVisible(false);

    return axis;
  }

  private void populateChart(Plottable plottable) {
    int currentWeek = plottable.getWeek();
    int startWeek = Math.max(0, currentWeek - MAX_WEEKS + 1);

    for (int i = startWeek; i <= currentWeek; i++) {
      addDataPoint(i + 1, plottable.getValueForWeek(i));
    }
  }

  private void addDataPoint(int week, BigDecimal value) {
    series.getData().add(new Data<>(week, value.doubleValue()));

    if (series.getData().size() > MAX_WEEKS) {
      series.getData().remove(0);
    }

    NumberAxis axis = (NumberAxis) getXAxis();
    axis.setUpperBound(Math.max(MAX_WEEKS, week));
    axis.setLowerBound(Math.max(1, week - MAX_WEEKS + 1));
  }

  @Override
  public void plottableChanged(BigDecimal value) {
    int week = this.plottable.getWeek() + 1;
    addDataPoint(week, value);
  }
}
