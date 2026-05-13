package edu.ntnu.idatt2003.group14.ui.features.game.portfolio;

import edu.ntnu.idatt2003.group14.logging.AppLogger;
import edu.ntnu.idatt2003.group14.model.GameSession;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.model.PlayerStatus;
import edu.ntnu.idatt2003.group14.model.Portfolio;
import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.model.plottable.PlottableChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller class for {@link PortfolioView}. Provides live updated JavaFX components to display.
 *
 * <p>This class implements {@link PlottableChangeListener} and should be assigned to the
 * {@link Player}s portfolio when a player is assigned.</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class PortfolioController implements PlottableChangeListener {
  private final ObservableList<Share> shares = FXCollections.observableArrayList();
  private final Portfolio portfolio;

  private ImageView statusImageView;
  private Label netWorthLabel;
  private Label cashLabel;
  private Label portfolioValueLabel;

  private PlayerStatus previousStatus = GameSession.getPlayer().getStatus();

  /**
   * Initiates a new PortfolioController.
   */
  public PortfolioController() {
    this.portfolio = GameSession.getPlayer().getPortfolio();
    this.portfolio.addListener(this);
    this.plottableChanged(null);
  }

  @Override
  public void plottableChanged(BigDecimal newValue) {
    Platform.runLater(() -> {
      shares.setAll(List.copyOf(portfolio.getShares()));

      BigDecimal cash = GameSession.getPlayer().getMoney();
      BigDecimal portfolioValue = portfolio.getValueForWeek(portfolio.getWeek());
      BigDecimal netWorth = cash.add(portfolioValue);

      if (GameSession.getPlayer().getStatus() != this.previousStatus) {
        this.previousStatus = GameSession.getPlayer().getStatus();
        updateStatusImage();
      }

      if (netWorthLabel != null) {
        netWorthLabel.setText("Net Worth: $" + netWorth.setScale(2, RoundingMode.HALF_UP));
        cashLabel.setText("Cash: $" + cash.setScale(2, RoundingMode.HALF_UP));
        portfolioValueLabel.setText(
            "Portfolio: $" + portfolioValue.setScale(2, RoundingMode.HALF_UP));
      }
    });
  }

  public HBox createShareRow(Share share, int index) {
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
    Button sellAll = new Button("Sell");
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

  /**
   * Returns the player's portfolio.
   *
   * @return the player's {@link Portfolio}
   */
  public Portfolio getPortfolio() {
    return this.portfolio;
  }

  /**
   * Get all the stocks in the portfolio.
   *
   * @return the list of {@link Stock}'s
   */
  public List<Stock> getPortfolioStockList() {
    return this.portfolio.getShares().stream()
        .map(Share::getStock)
        .toList();
  }

  public ObservableList<Share> getShares() {
    return this.shares;
  }

  public void setTopBarLabels(Label netWorthLabel, Label cashLabel, Label portfolioLabel) {
    this.netWorthLabel = netWorthLabel;
    this.cashLabel = cashLabel;
    this.portfolioValueLabel = portfolioLabel;
  }

  public void setStatusImageView(ImageView imageView) {
    this.statusImageView = imageView;
  }

  private void updateStatusImage() {
    if (statusImageView == null) {
      return;
    }
    // Image naming must match NOVICE.png, INVESTOR.png, SPECULATOR.png
    String path = "/images/icons/" + GameSession.getPlayer().getStatus().name() + ".png";

    var resource = getClass().getResourceAsStream(path);
    if (resource != null) {
      statusImageView.setImage(new Image(resource));
    } else {
      AppLogger.error("Could not find image: " + path);
    }
  }
}
