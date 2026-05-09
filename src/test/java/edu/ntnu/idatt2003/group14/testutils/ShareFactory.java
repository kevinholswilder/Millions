package edu.ntnu.idatt2003.group14.testutils;

import edu.ntnu.idatt2003.group14.model.Share;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Share factory class.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public final class ShareFactory {

  private ShareFactory() {}

  /**
   * <pre>
   * Creates a new share with the values:
   * stock: As defined by the {@link StockFactory} method getStock
   * quantity: 3
   * price: the sales price multiplied by the quantity
   * </pre>
   *
   * @return a new share
   */
  public static Share createShare() {
    var stock = StockFactory.createStock();
    var quantity = new BigDecimal("3");
    return new Share(
        stock,
        quantity,
        stock.getSalesPrice()
    );
  }

  /**
   * <pre>
   * Creates a list of new shares with the values:
   * stock: As defined by the {@link StockFactory} method getStocks
   * quantity: from 1 to 4
   * price: the sales price multiplied by the quantity
   * </pre>
   *
   * @param amount the amount of shares to be created
   * @return a list of new shares
   */
  public static List<Share> createShares(int amount) {
    var shares = new ArrayList<Share>();
    var stocks = StockFactory.createStocks(amount);

    for (int i = 0; i < amount; i++) {
      var quantity = new BigDecimal(i % 4 + 1);
          shares.add(new Share(
              stocks.get(i),
              quantity,
              stocks.get(i).getSalesPrice()
      ));
    }
    return shares;
  }
}
