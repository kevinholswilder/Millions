package edu.ntnu.idatt2003.group14.ui.features.game.portfolio;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.ui.app.View;
import edu.ntnu.idatt2003.group14.ui.components.PlottableGraph;
import edu.ntnu.idatt2003.group14.ui.components.StockMoversComponent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * View displaying portfolio information and stock movers.
 *
 * @author Kevin Holswilder, Elias Haugsbakk
 * @since 0.0.1
 */
public class PortfolioView implements View {
  private final BorderPane root;
  private final PortfolioController controller;

  /**
   * Creates a new portfolio view.
   *
   * @param controller controller providing portfolio data
   */
  public PortfolioView(PortfolioController controller) {
    this.controller = controller;
    this.root = new BorderPane();

    populate();

    // Prevent autofocus
    this.root.sceneProperty().addListener((_, _, newScene) -> {
      if (newScene != null) {
        this.root.requestFocus();
      }
    });
  }

  /**
   * Populates the view with portfolio components.
   */
  private void populate() {
    PlottableGraph graph = createGraph();
    VBox stockMoversPane = createStockMovers();

    // Holds portfolio graph and stock movers
    HBox graphMoversPane = new HBox(graph, stockMoversPane);

    ListView<Share> shareListView = createListView();

    // Holds graph, stock movers and shares list
    VBox portfolioPane = new VBox(graphMoversPane, shareListView);

    this.root.setCenter(portfolioPane);

    this.root.setTop(createTopBar());
  }

  private HBox createTopBar() {
    Label netWorthLabel = new Label();
    Label cashLabel = new Label();
    Label portfolioLabel = new Label();


    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    ImageView imageView = new ImageView();
    imageView.setFitHeight(100);
    imageView.setPreserveRatio(true);

    controller.setTopBarLabels(netWorthLabel, cashLabel, portfolioLabel);
    controller.setStatusImageView(imageView);

    HBox topBar = new HBox(40, netWorthLabel, cashLabel, portfolioLabel, spacer, imageView);
    topBar.getStyleClass().add("portfolio-top-bar");
    topBar.setPadding(new Insets(15));
    return topBar;
  }

  private ListView<Share> createListView() {
    ListView<Share> listView = new ListView<>(controller.getShares());

    listView.setCellFactory(_ -> new ListCell<>() {
      @Override
      protected void updateItem(Share share, boolean empty) {
        super.updateItem(share, empty);

        if (empty || share == null) {
          setText(null);
          setGraphic(null);
        } else {
          setGraphic(controller.createShareRow(share, getIndex()));
        }
      }
    });

    listView.setPlaceholder(new Label("No shares to show yet."));
    VBox.setVgrow(listView, Priority.ALWAYS);

    return listView;
  }

  private PlottableGraph createGraph() {
    PlottableGraph graph = new PlottableGraph(controller.getPortfolio());
    controller.getPortfolio().addListener(graph);
    return graph;
  }

  private VBox createStockMovers() {
    StockMoversComponent gainers = new StockMoversComponent(controller.getPortfolioStockList(),
        StockMoversComponent.Type.GAINERS);
    StockMoversComponent losers = new StockMoversComponent(controller.getPortfolioStockList(),
        StockMoversComponent.Type.LOSERS);

    controller.getPortfolio().addListener(gainers);
    controller.getPortfolio().addListener(losers);

    return new VBox(gainers, losers);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
