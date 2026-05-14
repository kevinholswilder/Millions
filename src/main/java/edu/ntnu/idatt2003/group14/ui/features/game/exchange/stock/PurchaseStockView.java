package edu.ntnu.idatt2003.group14.ui.features.game.exchange.stock;

import edu.ntnu.idatt2003.group14.exception.InsufficientBalanceException;
import edu.ntnu.idatt2003.group14.model.Money;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import edu.ntnu.idatt2003.group14.service.GameService;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import edu.ntnu.idatt2003.group14.ui.app.Route;
import edu.ntnu.idatt2003.group14.ui.app.View;
import edu.ntnu.idatt2003.group14.ui.components.PlottableGraph;
import edu.ntnu.idatt2003.group14.ui.features.game.transaction.receipt.TransactionReceiptController;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Modal view for purchasing stocks in the exchange.
 *
 * <p>This view allows the player to:
 * <ul>
 *   <li>View stock information and price history</li>
 *   <li>Enter a quantity of shares to purchase</li>
 *   <li>Preview commission and total transaction cost</li>
 *   <li>Complete a stock purchase transaction</li>
 * </ul>
 * </p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class PurchaseStockView implements View {
  private final BorderPane root;
  private final AppRouter router;
  private final GameService gameService;

  /**
   * Creates a new purchase stock view.
   *
   * @param router the application router used for navigation
   */
  public PurchaseStockView(AppRouter router, GameService gameService) {
    this.gameService = gameService;
    this.root = new BorderPane();
    this.router = router;
    VBox center = centerMenu(PurchaseStockController.getInstance().getStock());
    center.getStyleClass().add("purchase-container");
    this.root.setCenter(center);

    this.root.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/exchange/stock/stock-purchase.css")
        ).toExternalForm()
    );
  }

  /**
   * Creates the main purchase menu for the selected stock.
   *
   * @param stock the stock being purchased
   * @return the constructed purchase menu
   */
  private VBox centerMenu(Stock stock) {
    VBox menu = new VBox(10);
    menu.setAlignment(Pos.CENTER);
    menu.setPadding(new Insets(20));

    Label title = new Label(
        "Purchase Stock: " + stock.getCompany() + " | " + stock.getSymbol()
    );
    title.getStyleClass().add("purchase-title");

    Label numericalLabel = new Label("Enter quantity:");
    numericalLabel.getStyleClass().add("purchase-numerical-label");

    TextField numericalField = new TextField();
    numericalField.getStyleClass().add("purchase-numerical-field");

    Label errorLabel = new Label("");
    errorLabel.getStyleClass().add("purchase-error-label");

    Label comissionLabel = new Label("Commission:");
    comissionLabel.getStyleClass().add("purchase-comission-label");

    Label totalLabel = new Label("Total:");
    totalLabel.getStyleClass().add("purchase-total-label");

    AtomicReference<Transaction> transaction = new AtomicReference<>();

    // Source - https://stackoverflow.com/a/30796829
    // Posted by Evan Knowles, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-05-13, License - CC BY-SA 3.0
    numericalField.textProperty().addListener((_, _, newValue) -> {
      if (!newValue.matches("\\d*")) {
        numericalField.setText(newValue.replaceAll("[^\\d]", ""));
        return;
      }

      BigDecimal quantity = newValue.isEmpty()
          ? BigDecimal.ZERO
          : new BigDecimal(newValue);

      if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
        errorLabel.setText("");
        comissionLabel.setText("Commission:");
        totalLabel.setText("Total:");
        return;
      }

      try {
        errorLabel.setText("");

        transaction.set(gameService.getExchange().purchase(
            stock.getSymbol(),
            quantity,
            gameService.getPlayer(),
            false
        ));

        BigDecimal commission = transaction.get()
            .getCalculator()
            .calculateCommission();

        BigDecimal total = transaction.get()
            .getCalculator()
            .calculateTotal();

        comissionLabel.setText(
            "Commission: " + Money.normalize(commission).toPlainString()
        );

        totalLabel.setText(
            "Total: " + Money.normalize(total).toPlainString()
        );

      } catch (InsufficientBalanceException e) {
        errorLabel.setText(e.getMessage());
      }
    });

    PlottableGraph graph = new PlottableGraph(stock);

    Button purchaseButton = new Button("Purchase");
    purchaseButton.getStyleClass().add("purchase-button");

    purchaseButton.setOnAction(_ -> {
      try {
        BigDecimal quantity = numericalField.getText().isEmpty()
            ? BigDecimal.ZERO
            : new BigDecimal(numericalField.getText());

        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
          errorLabel.setText("You must enter an integer higher than 0.");
          return;
        }

        transaction.get().commit(gameService.getPlayer());

        TransactionReceiptController.getInstance().setTransaction(transaction.get());

        router.hidePopup();
        router.navigate(Route.TRANSACTION_RECEIPT);
      } catch (InsufficientBalanceException e) {
        errorLabel.setText(e.getMessage());
      }
    });

    menu.getChildren().addAll(
        title,
        graph,
        numericalLabel,
        numericalField,
        errorLabel,
        comissionLabel,
        totalLabel,
        purchaseButton
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
