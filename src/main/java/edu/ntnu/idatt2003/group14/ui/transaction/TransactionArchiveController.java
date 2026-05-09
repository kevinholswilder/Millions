package edu.ntnu.idatt2003.group14.ui.transaction;

import edu.ntnu.idatt2003.group14.model.Share;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.model.transaction.Purchase;
import edu.ntnu.idatt2003.group14.model.transaction.Transaction;
import java.math.BigDecimal;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller responsible for creating and styling transaction archive UI elements.
 *
 * <p>This controller generates visual rows representing transactions,
 * including its transaction type, quantity, price, total value, and week.</p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class TransactionArchiveController {

  /**
   * Creates a row displaying transaction information.
   *
   * @param transaction the transaction to display
   * @param index the row index
   * @return a styled transaction row
   */
  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
  public HBox createTransactionRow(Transaction transaction, int index) {
    Share share = transaction.getShare();
    Stock stock = share.getStock();

    String transactionType = transaction instanceof Purchase ? "PURCHASE" : "SALE";

    BigDecimal quantity = share.getQuantity();
    BigDecimal price = share.getPurchasePrice();
    BigDecimal total = quantity.multiply(price);

    VBox typeBox = this.createInfoBox(
        transactionType,
        stock.getSymbol()
    );

    VBox quantityBox = this.createInfoBox(
        "Quantity",
        quantity.toPlainString() + " shares"
    );

    VBox priceBox = this.createInfoBox(
        "Price",
        "$" + price.toPlainString()
    );

    VBox totalBox = this.createInfoBox(
        "Total",
        "$" + total.toPlainString()
    );

    VBox weekBox = this.createInfoBox(
        "Week",
        String.valueOf(transaction.getWeek())
    );

    HBox row = new HBox(40);
    row.setAlignment(Pos.CENTER_LEFT);

    row.getStyleClass().add("transaction-row");
    row.getStyleClass().add(index % 2 == 0 ? "transaction-row-even" : "transaction-row-odd");

    row.getChildren().addAll(
        typeBox,
        quantityBox,
        priceBox,
        totalBox,
        weekBox
    );

    Label title = (Label) typeBox.getChildren().getFirst();

    if (transaction instanceof Purchase) {
      title.getStyleClass().add("purchase-label");
    } else {
      title.getStyleClass().add("sale-label");
    }

    return row;
  }

  /**
   * Creates a labeled info box.
   *
   * @param title the title text
   * @param value the value text
   * @return a styled info box
   */
  private VBox createInfoBox(String title, String value) {
    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("transaction-title");

    Label valueLabel = new Label(value);
    valueLabel.getStyleClass().add("transaction-value");

    VBox box = new VBox(4);
    box.getStyleClass().add("transaction-info-box");
    box.getChildren().addAll(titleLabel, valueLabel);

    return box;
  }

}