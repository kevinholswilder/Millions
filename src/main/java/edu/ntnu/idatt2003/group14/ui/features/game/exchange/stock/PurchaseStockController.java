package edu.ntnu.idatt2003.group14.ui.features.game.exchange.stock;

import edu.ntnu.idatt2003.group14.model.Stock;

/**
 * Singleton controller for managing the currently selected stock
 * in the purchase stock view.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public final class PurchaseStockController {

  private static final PurchaseStockController instance =
      new PurchaseStockController();

  private Stock stock;

  /**
   * Private constructor to prevent instantiation.
   */
  private PurchaseStockController() {}

  /**
   * Returns the singleton instance of the controller.
   *
   * @return the purchase stock controller instance
   */
  public static PurchaseStockController getInstance() {
    return instance;
  }

  /**
   * Returns the currently selected stock.
   *
   * @return the selected stock
   */
  public Stock getStock() {
    return stock;
  }

  /**
   * Sets the currently selected stock.
   *
   * @param stock the stock to set
   */
  public void setStock(Stock stock) {
    this.stock = stock;
  }
}