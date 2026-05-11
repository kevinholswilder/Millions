package edu.ntnu.idatt2003.group14.ui.features.portfolio;

import edu.ntnu.idatt2003.group14.ui.app.View;
import edu.ntnu.idatt2003.group14.ui.components.PlottableGraph;
import edu.ntnu.idatt2003.group14.ui.components.StockMoversComponent;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View displaying portfolio information and stock movers.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class PortfolioView implements View {
  private final HBox root;
  private final PortfolioController controller;

  /**
   * Creates a new portfolio view.
   *
   * @param controller controller providing portfolio data
   */
  public PortfolioView(PortfolioController controller) {
    this.controller = controller;
    this.root = new HBox();

    populate();
  }

  /**
   * Populates the view with portfolio components.
   */
  private void populate() {
    VBox portfolioPane = new VBox();
    VBox stockMoversPane = new VBox();

    PlottableGraph graph = new PlottableGraph(controller.getPortfolio());
    controller.getPortfolio().addListener(graph);
    portfolioPane.getChildren().add(graph);

    StockMoversComponent gainers =
        new StockMoversComponent(
            controller.getPortfolioStockList(),
            StockMoversComponent.Type.GAINERS
        );

    StockMoversComponent losers =
        new StockMoversComponent(
            controller.getPortfolioStockList(),
            StockMoversComponent.Type.LOSERS
        );

    stockMoversPane.getChildren().addAll(gainers, losers);

    root.getChildren().addAll(portfolioPane, stockMoversPane);
  }

  /**
   * Returns the root node of the view.
   *
   * @return the root layout node
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}
