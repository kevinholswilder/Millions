package edu.ntnu.idatt2003.group14.ui.components;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.portfolio.Portfolio;
import edu.ntnu.idatt2003.group14.model.portfolio.PortfolioChangeListener;
import java.math.BigDecimal;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

/**
 * A line chart that displays the net worth of a portfolio over time.
 * Listens to the {@link Portfolio} and updates automatically when the net worth changes.
 * Historical values are displayed on creation if available.
 */
public class PortfolioGraph extends LineChart<Number, Number> implements PortfolioChangeListener {
  private final Series<Number, Number> series;
  private int weekCounter = 1;

  /**
   * Instantiates a new PortfolioGraph for the given portfolio.
   *
   * @param portfolio the portfolio whose net worth is displayed
   */
  public PortfolioGraph(Portfolio portfolio) {
    super(new NumberAxis(), new NumberAxis());

    this.series = new Series<>();
    this.getData().add(series);

    portfolio.addListener(this);

    if (!portfolio.getShares().isEmpty()) {
      populateChart(portfolio);
    }
  }

  private void populateChart(Portfolio portfolio) {
    int numberOfWeeks = portfolio.getShares().getFirst().getStock().getHistoricalPrices().size();

    var stocks = portfolio.getShares().stream()
        .map(Share::getStock)
        .toList();

    for (int i = 0; i < numberOfWeeks; i++) {
      int week = i;
      BigDecimal thisValue = stocks.stream()
          .map(stock -> stock.getHistoricalPrices().get(week))
          .reduce(BigDecimal.ZERO, BigDecimal::add);
      addDataPoint(thisValue);
    }
  }

  private void addDataPoint(BigDecimal value) {
    series.getData().add(new Data<>(weekCounter++, value.doubleValue()));
  }

  @Override
  public void portfolioChanged(BigDecimal netWorth) {
    addDataPoint(netWorth);
  }
}
