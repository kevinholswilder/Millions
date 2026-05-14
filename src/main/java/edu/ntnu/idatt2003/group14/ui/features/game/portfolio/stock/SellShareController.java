package edu.ntnu.idatt2003.group14.ui.features.game.portfolio.stock;

import edu.ntnu.idatt2003.group14.model.Share;

/**
 * Singleton controller for managing the currently selected share
 * in the sell share view.
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 1.0.0
 */
public class SellShareController {

  private static final SellShareController instance =
      new SellShareController();

  private Share share;

  /**
   * Private constructor to prevent instantiation.
   */
  private SellShareController() {
  }

  /**
   * Returns the singleton instance of the controller.
   *
   * @return the sell share controller instance
   */
  public static SellShareController getInstance() {
    return instance;
  }

  /**
   * Returns the currently selected stock.
   *
   * @return the selected stock
   */
  public Share getShare() {
    return this.share;
  }

  /**
   * Sets the currently selected stock.
   *
   * @param share the stock to set
   */
  public void setShare(Share share) {
    this.share = share;
  }
}
