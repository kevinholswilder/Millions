package edu.ntnu.idatt2003.group14.ui.features.game.portfolio;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.ui.app.View;
import edu.ntnu.idatt2003.group14.ui.components.PlottableGraph;
import edu.ntnu.idatt2003.group14.ui.components.StockMoversComponent;
import java.math.RoundingMode;
import java.util.Objects;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
 * @version 1.0.0
 * @since 0.0.1
 */
public class PortfolioView implements View {
  private final BorderPane root;
  private final PortfolioController controller;
  private StockMoversComponent gainers;
  private StockMoversComponent losers;

  /**
   * Creates a new portfolio view.
   *
   * @param controller controller providing portfolio data
   */
  public PortfolioView(PortfolioController controller) {
    this.controller = controller;
    this.root = new BorderPane();

    populate();

    this.root.getStylesheets().addAll(
        Objects.requireNonNull(
            getClass().getResource("/css/portfolio/portfolio.css")
        ).toExternalForm(),
        Objects.requireNonNull(
            getClass().getResource("/css/exchange/stock-row.css")
        ).toExternalForm()
    );

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
    VBox moversWrapper = new VBox();

    // Initial movers
    updateMovers(moversWrapper);

    controller.getPortfolio()
        .addListener(_ -> Platform.runLater(() -> updateMovers(moversWrapper)));

    HBox.setHgrow(graph, Priority.ALWAYS);

    // Holds portfolio graph and stock movers
    HBox graphMoversPane = new HBox(5, graph, moversWrapper);
    HBox.setMargin(moversWrapper, new Insets(0, 5, 0, 0));

    ListView<Share> shareListView = createListView();

    // Holds graph, stock movers and shares list
    VBox portfolioPane = new VBox(graphMoversPane, shareListView);

    this.root.setCenter(portfolioPane);

    this.root.setTop(createTopBar());
  }

  private void updateMovers(VBox wrapper) {
    if (gainers != null) {
      controller.getPortfolio().removeListener(gainers);
    }
    if (losers != null) {
      controller.getPortfolio().removeListener(losers);
    }

    wrapper.getChildren().clear();

    gainers = new StockMoversComponent(controller.getPortfolioStockList(),
        StockMoversComponent.Type.GAINERS);
    losers = new StockMoversComponent(controller.getPortfolioStockList(),
        StockMoversComponent.Type.LOSERS);

    controller.getPortfolio().addListener(gainers);
    controller.getPortfolio().addListener(losers);

    wrapper.getChildren().addAll(gainers, losers);
    wrapper.setSpacing(5);
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
          setGraphic(createShareRow(share, getIndex()));
        }
      }
    });

    listView.setPlaceholder(new Label("No shares to show yet."));
    VBox.setVgrow(listView, Priority.ALWAYS);

    return listView;
  }

  private HBox createShareRow(Share share, int index) {
    // Identity
    VBox assetBox = createInfoBox(share.getStock().getSymbol(), share.getStock().getCompany());

    // Position
    VBox posBox = createInfoBox("Quantity", String.valueOf(share.getQuantity()));

    // Total Value
    VBox valueBox = createInfoBox("Total Value",
        "$" + share.getCurrentValue().setScale(2, RoundingMode.HALF_UP));

    // Buttons
    HBox actions = new HBox(10);
    Button buyMore = new Button("Buy");
    buyMore.setOnAction(
        _ -> controller.buyStock(share.getStock())
    );
    Button sellAll = new Button("Sell");
    sellAll.setOnAction(
        _ -> controller.sellShare(share)
    );
    actions.getChildren().addAll(buyMore, sellAll);
    actions.setAlignment(Pos.CENTER);

    HBox row = new HBox(24);
    row.getStyleClass().add("stock-row");
    row.getStyleClass().add(index % 2 == 0 ? "stock-row-even" : "stock-row-odd");

    row.getChildren().addAll(assetBox, posBox, valueBox, actions);
    return row;
  }

  private VBox createInfoBox(String title, String value) {
    Label t = new Label(title);
    t.getStyleClass().add("stock-info-title");
    Label v = new Label(value);
    v.getStyleClass().add("stock-info-value");
    VBox box = new VBox(6);
    box.getChildren().addAll(t, v);
    return box;
  }

  private PlottableGraph createGraph() {
    PlottableGraph graph = new PlottableGraph(controller.getPortfolio());

    graph.getStyleClass().add("portfolio-graph");

    return graph;
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
