package edu.ntnu.idatt2003.group14.ui.features.game.exchange;

import edu.ntnu.idatt2003.group14.config.lang.LangConfig;
import edu.ntnu.idatt2003.group14.model.Exchange;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.service.GameService;
import edu.ntnu.idatt2003.group14.ui.app.View;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * View responsible for displaying the exchange.
 *
 * <p>The view contains a top bar with search and sorting controls, as well as
 * a list view showing available stocks.</p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class ExchangeView implements View {
  private final BorderPane root;
  private final ExchangeController controller;
  private final GameService gameService;
  private final ListView<Stock> stockListView;

  /**
   * Creates a new exchange view using the provided controller.
   *
   * @param controller the controller used to manage stock filtering, sorting, and row creation
   */
  public ExchangeView(GameService gameService, ExchangeController controller) {
    this.root = new BorderPane();
    this.gameService = gameService;
    this.controller = controller;

    this.stockListView = new ListView<>();

    this.root.getStyleClass().add("exchange-root");

    this.root.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/stylesheets/exchange/stock-row.css")
        ).toExternalForm()
    );

    Parent topBar = this.createTopBar();
    BorderPane.setMargin(topBar, new Insets(20, 20, 0, 20));
    this.root.setTop(topBar);

    this.setupStockListView();
    this.root.setCenter(this.stockListView);

    Exchange exchange = gameService.getExchange();
    exchange.addWeekAdvanceListener(_ -> this.refreshStocks());
    this.setStocks(exchange.getStocks());
  }

  /**
   * Configures the stock list view and its custom cell factory.
   *
   * <p>Each non-empty list cell is rendered using a stock row created by the
   * controller.</p>
   */
  private void setupStockListView() {
    this.stockListView.setCellFactory(_ -> new ListCell<>() {
      @Override
      protected void updateItem(Stock stock, boolean empty) {
        super.updateItem(stock, empty);

        if (empty || stock == null) {
          setText(null);
          setGraphic(null);
          return;
        }

        setGraphic(controller.createStockRow(stock, getIndex()));
      }
    });

    BorderPane.setMargin(this.stockListView, new Insets(20));
  }

  /**
   * Sets the stocks to display in the exchange view.
   *
   * @param stocks the stocks to display
   */
  public void setStocks(List<Stock> stocks) {
    this.controller.setStocks(stocks);
    this.refreshStocks();
  }

  /**
   * Refreshes the stock list view using the controller's processed stock list.
   */
  private void refreshStocks() {
    this.stockListView.getItems().setAll(this.controller.getProcessedStocks());
  }

  /**
   * Creates the top bar containing search and sorting controls.
   *
   * @return the top bar as a {@link Parent}
   */
  private Parent createTopBar() {
    TextField searchField = new TextField();
    searchField.setPromptText(
        LangConfig.getInstance().lang("exchange-menu.header.text_field.search")
    );
    searchField.getStyleClass().add("exchange-search-field");

    searchField.textProperty().addListener((_, _, query) -> {
      this.controller.setCurrentSearch(query);
      this.refreshStocks();
    });

    Label sortLabel = new Label(
        LangConfig.getInstance().lang("exchange-menu.header.label.sort_by")
    );
    sortLabel.getStyleClass().add("exchange-sort-label");

    Button directionButton = new Button("↑");
    directionButton.getStyleClass().add("exchange-sort-button");

    directionButton.setOnAction(_ -> {
      this.controller.toggleAscending();
      directionButton.setText(this.controller.isAscending() ? "↑" : "↓");
      this.refreshStocks();
    });

    HBox topBar = new HBox(12);
    topBar.setAlignment(Pos.CENTER_LEFT);
    topBar.getStyleClass().add("exchange-top-bar");

    Label weekLabel = new Label();
    this.controller.updateWeekLabel(weekLabel);

    this.gameService.getExchange().addWeekAdvanceListener(_ -> {
      this.controller.updateWeekLabel(weekLabel);
    });

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    topBar.getChildren().addAll(
            searchField,
            sortLabel,
            this.createSortButton(
                LangConfig.getInstance().lang("exchange-menu.header.label.price")
            ),
            this.createSortButton(LangConfig.getInstance().lang("exchange-menu.header.label.a_z")),
            directionButton,
            spacer,
            weekLabel
    );

    return topBar;
  }

  /**
   * Creates a sort button for the given sorting option.
   *
   * @param text the text and sorting option represented by the button
   * @return a styled {@link Button} that updates the current sort option
   */
  private Button createSortButton(String text) {
    Button button = new Button(text);

    button.getStyleClass().add("exchange-sort-button");

    button.setOnAction(_ -> {
      this.controller.setCurrentSort(text);
      this.refreshStocks();
    });

    return button;
  }

  @Override
  public Parent getRoot() {
    return root;
  }

}
