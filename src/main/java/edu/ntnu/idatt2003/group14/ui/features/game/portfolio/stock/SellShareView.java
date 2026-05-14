package edu.ntnu.idatt2003.group14.ui.features.game.portfolio.stock;

import edu.ntnu.idatt2003.group14.config.lang.LangConfig;
import edu.ntnu.idatt2003.group14.exception.InsufficientBalanceException;
import edu.ntnu.idatt2003.group14.model.Money;
import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.transaction.Sale;
import edu.ntnu.idatt2003.group14.service.GameService;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import edu.ntnu.idatt2003.group14.ui.app.Route;
import edu.ntnu.idatt2003.group14.ui.app.View;
import edu.ntnu.idatt2003.group14.ui.components.PlottableGraph;
import edu.ntnu.idatt2003.group14.ui.features.game.transaction.receipt.TransactionReceiptController;
import java.math.BigDecimal;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Modal view for selling shares in the portfolio.
 *
 * <p>This view allows the player to:
 * <ul>
 *   <li>View stock information and price history</li>
 *   <li>Preview commission, tax and total transaction proceeds</li>
 *   <li>Complete a stock sale transaction</li>
 * </ul>
 * </p>
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 1.0.0
 */
public class SellShareView implements View {
  private final BorderPane root;
  private final AppRouter router;
  private final GameService gameService;

  /**
   * Creates a new sell share view.
   *
   * @param router      the application router used for navigation
   * @param gameService the game service providing player and portfolio data
   */
  public SellShareView(AppRouter router, GameService gameService) {
    this.gameService = gameService;
    this.root = new BorderPane();
    this.router = router;
    VBox center = centerMenu(SellShareController.getInstance().getShare());
    center.getStyleClass().add("purchase-container");
    this.root.setCenter(center);

    this.root.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/exchange/stock/stock-purchase.css")
        ).toExternalForm()
    );
  }

  /**
   * Creates the main sell menu for the selected share.
   *
   * @param share the share being sold
   * @return the constructed sell menu
   */
  private VBox centerMenu(Share share) {
    VBox menu = new VBox(10);
    menu.setAlignment(Pos.CENTER);
    menu.setPadding(new Insets(20));

    Label title = new Label(
        LangConfig.getInstance().lang("sell-stock-menu.title") + " " + share.getStock().getCompany() + " | " + share.getStock().getSymbol()
    );
    title.getStyleClass().add("purchase-title");

    Label errorLabel = new Label("");
    errorLabel.getStyleClass().add("purchase-error-label");

    Sale sale = new Sale(share, share.getStock().getWeek());
    BigDecimal commission = sale.getCalculator().calculateCommission();
    BigDecimal tax = sale.getCalculator().calculateTax();
    BigDecimal total = sale.getCalculator().calculateTotal();

    Label stockAmount = new Label(LangConfig.getInstance().lang("sell-stock-menu.label.amount") + " " + share.getQuantity());
    stockAmount.getStyleClass().add("purchase-comission-label");

    Label comissionLabel = new Label(
            LangConfig.getInstance().lang("sell-stock-menu.label.commission") + " " + Money.normalize(commission).toPlainString()
    );
    comissionLabel.getStyleClass().add("purchase-comission-label");

    Label taxLabel = new Label(
            LangConfig.getInstance().lang("sell-stock-menu.label.tax") + " " + Money.normalize(tax).toPlainString()
    );
    taxLabel.getStyleClass().add("purchase-comission-label");

    Label totalLabel = new Label(
            LangConfig.getInstance().lang("sell-stock-menu.label.total") + " " + Money.normalize(total).toPlainString()
    );
    totalLabel.getStyleClass().add("purchase-total-label");

    PlottableGraph graph = new PlottableGraph(share.getStock());

    Button sellButton = new Button(LangConfig.getInstance().lang("sell-stock-menu.button.sell"));
    sellButton.getStyleClass().add("purchase-button");

    sellButton.setOnAction(_ -> {
      try {
        sale.commit(gameService.getPlayer());

        TransactionReceiptController.getInstance().setTransaction(sale);

        router.hidePopup();
        router.navigate(Route.TRANSACTION_RECEIPT);

      } catch (InsufficientBalanceException e) {
        errorLabel.setText(e.getMessage());
      }
    });

    menu.getChildren().addAll(
        title,
        graph,
        errorLabel,
        stockAmount,
        comissionLabel,
        taxLabel,
        totalLabel,
        sellButton
    );

    return menu;
  }

  /**
   * Returns the root node of the view.
   *
   * @return the root node
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}
