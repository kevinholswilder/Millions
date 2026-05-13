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
  private final Series<Number, Number> series;
  private int weekCounter = 0;

  /**
   * Instantiates a new PlottableGraph for the given plottable.
   *
   * @param plottable the plottable whose net worth is displayed
   */
  public PlottableGraph(Plottable plottable) {
    super(createXAxis(), new NumberAxis());
    this.setLegendVisible(false);
    this.setAnimated(false);
    this.setCreateSymbols(false);

    this.series = new Series<>();
    this.getData().add(series);

    plottable.addListener(this);
    populateChart(plottable);
  }

  private static NumberAxis createXAxis() {
    NumberAxis axis = new NumberAxis();
    axis.setAutoRanging(false);
    axis.setLowerBound(1);
    axis.setUpperBound(5);
    axis.setTickUnit(1);
    return axis;
  }

  private void populateChart(Plottable plottable) {
    int numberOfWeeks = plottable.getWeek();

    for (int i = 0; i < numberOfWeeks; i++) {
      addDataPoint(plottable.getValueForWeek(i));
    }
  }

  private void addDataPoint(BigDecimal value) {
    series.getData().add(new Data<>(weekCounter, value.doubleValue()));
    ((NumberAxis) getXAxis()).setUpperBound(weekCounter);
    weekCounter++;
  }

  @Override
  public void plottableChanged(BigDecimal value) {
    addDataPoint(value);
  }
}
