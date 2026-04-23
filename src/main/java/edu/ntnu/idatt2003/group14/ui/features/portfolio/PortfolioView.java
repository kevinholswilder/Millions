package edu.ntnu.idatt2003.group14.ui.features.portfolio;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.components.PortfolioGraph;
import edu.ntnu.idatt2003.group14.ui.components.SideBar;
import edu.ntnu.idatt2003.group14.ui.components.StockMoversComponent;
import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * JavaFX scene for the Portfolio scene of the application.
 *
 * <p>Lets the Player view and interact with their current portfolio.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class PortfolioView {
  private final PortfolioController controller;
  private final BorderPane root;

  // contains the portfolio graph and stocks
  private final VBox portfolioPane;

  // contains gainers and losers
  private final VBox stockMoversPane;

  // contains the navigation bar
  private final SideBar navigation;

  /**
   * Initializes a new PortfolioView.
   *
   * @param controller   the controller for this view
   * @param audioManager the audio manager
   */
  public PortfolioView(PortfolioController controller, AudioManager audioManager) {
    this.controller = controller;
    this.root = new BorderPane();

    String mainStyle =
        Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm();
    String gameStyle =
        Objects.requireNonNull(getClass().getResource("/css/game.css")).toExternalForm();
    this.root.getStylesheets().addAll(mainStyle, gameStyle);

    // contains all the page content except for navigation
    HBox mainPane = new HBox();
    this.portfolioPane = new VBox();
    this.stockMoversPane = new VBox();

    mainPane.getChildren().addAll(portfolioPane, stockMoversPane);
    this.root.setCenter(mainPane);

    navigation = new SideBar();
    this.root.setLeft(navigation);

    populate();
  }

  public Parent getRoot() {
    return this.root;
  }

  private void populate() {
    PortfolioGraph graph = new PortfolioGraph(controller.getPortfolio());
    this.portfolioPane.getChildren().add(graph);

    StockMoversComponent gainers =
        new StockMoversComponent(controller.getPortfolioStockList(),
            StockMoversComponent.Type.GAINERS);
    StockMoversComponent losers =
        new StockMoversComponent(controller.getPortfolioStockList(),
            StockMoversComponent.Type.LOSERS);
    this.stockMoversPane.getChildren().addAll(gainers, losers);

    Label navbarLabel = new Label("The other game views goes here");
    this.navigation.getChildren().add(navbarLabel);
  }
}
