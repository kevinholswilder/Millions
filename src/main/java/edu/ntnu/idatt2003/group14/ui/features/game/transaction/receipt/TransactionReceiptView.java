package edu.ntnu.idatt2003.group14.ui.features.game.transaction.receipt;

import edu.ntnu.idatt2003.group14.calculator.TransactionCalculator;
import edu.ntnu.idatt2003.group14.config.lang.LangConfig;
import edu.ntnu.idatt2003.group14.model.Money;
import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.model.transaction.Purchase;
import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;
import edu.ntnu.idatt2003.group14.ui.app.View;
import java.math.BigDecimal;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Modal view responsible for displaying a transaction receipt.
 *
 * <p>The receipt contains stock information, transaction details,
 * calculated costs, and transaction status.</p>
 *
 * @author Kevin Holswilder, Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
 */
public class TransactionReceiptView implements View {
  private final BorderPane root;
  private final AppRouter router;

  /**
   * Creates a new transaction receipt view.
   *
   * @param router the application router used for popup handling
   */
  public TransactionReceiptView(AppRouter router) {
    this.root = new BorderPane();
    this.router = router;

    this.root.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/transaction_archive/transaction_receipt/receipt.css")
        ).toExternalForm()
    );

    VBox receipt = this.displayReceipt(TransactionReceiptController.getInstance().getTransaction());
    receipt.setAlignment(Pos.CENTER);
    receipt.getStyleClass().add("receipt-container");
    this.root.setCenter(receipt);
  }

  /**
   * Creates the receipt layout for a transaction.
   *
   * <p>The receipt includes stock information, pricing,
   * transaction calculations, and status.</p>
   *
   * @param transaction the transaction to display
   * @return a VBox containing the receipt information
   */
  private VBox displayReceipt(Transaction transaction) {
    VBox receipt = new VBox(10);

    Share share = transaction.getShare();
    Stock stock = share.getStock();

    String type = transaction instanceof Purchase
            ? LangConfig.getInstance().lang("receipt-menu.type.purchase")
            : LangConfig.getInstance().lang("receipt-menu.type.sale");
    Label title = new Label(LangConfig.getInstance().lang("receipt-menu.title").replace("{type}", type));
    title.getStyleClass().add("receipt-title-label");

    Label stockLabel = new Label(LangConfig.getInstance().lang("receipt-menu.label.symbol") + " " + stock.getSymbol());
    Label companyLabel = new Label(LangConfig.getInstance().lang("receipt-menu.label.company") + " " + stock.getCompany());
    Label weekLabel = new Label(LangConfig.getInstance().lang("receipt-menu.label.week") + " " + (transaction.getWeek() + 1));

    Label quantityLabel =
        new Label(LangConfig.getInstance().lang("receipt-menu.label.quantity") + " " + Money.normalize(share.getQuantity()).toPlainString());
    Label priceLabel =
        new Label(LangConfig.getInstance().lang("receipt-menu.label.price") + " " + Money.normalize(share.getPurchasePrice()).toPlainString());
    Label subtotalLabel =
        new Label(LangConfig.getInstance().lang("receipt-menu.label.subtotal") + " " + Money.normalize(share.getTotal()).toPlainString());

    TransactionCalculator calculator = transaction.getCalculator();

    BigDecimal total = calculator.calculateTotal();
    BigDecimal commission = calculator.calculateCommission();
    BigDecimal gross = calculator.calculateGross();
    BigDecimal tax = calculator.calculateTax();

    Label commissionLabel =
        new Label(LangConfig.getInstance().lang("receipt-menu.label.commission") + " " + Money.normalize(commission).toPlainString());
    Label grossLabel = new Label(LangConfig.getInstance().lang("receipt-menu.label.gross") + " " + Money.normalize(gross).toPlainString());
    Label taxLabel = new Label(LangConfig.getInstance().lang("receipt-menu.label.tax") + " " + Money.normalize(tax).toPlainString());
    Label totalLabel = new Label(LangConfig.getInstance().lang("receipt-menu.label.total") + " " + Money.normalize(total).toPlainString());

    Label statusLabel = new Label(LangConfig.getInstance().lang("receipt-menu.label.status"));

    Button closeButton = new Button(LangConfig.getInstance().lang("receipt-menu.button.close"));
    closeButton.getStyleClass().add("purchase-button");

    closeButton.setOnAction(_ -> router.hidePopup());

    receipt.getChildren().addAll(
        title,
        stockLabel,
        companyLabel,
        weekLabel,
        quantityLabel,
        priceLabel,
        subtotalLabel,
        commissionLabel,
        grossLabel,
        taxLabel,
        totalLabel,
        statusLabel,
        closeButton
    );

    return receipt;
  }

  /**
   * Returns the root node of the view.
   *
   * @return the root parent node
   */
  @Override
  public Parent getRoot() {
    return this.root;
  }
}
