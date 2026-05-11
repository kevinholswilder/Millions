package edu.ntnu.idatt2003.group14.ui.components.sidebar;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Header component containing sidebar navigation buttons.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class Header extends VBox {

  private SideBarController controller;

  /**
   * Creates a new header with navigation buttons.
   *
   * @param controller controller used to create navigation buttons
   */
  public Header(SideBarController controller) {
    this.controller = controller;

    Button transactionArchiveButton = controller.createTransactionButton(
        "Transactions"
    );

    Button portfolioButton = controller.createPortfolioButton(
        "Portfolio"
    );

    Button exchangeButton = controller.createExchangeButton(
        "Exchange"
    );

    this.getChildren().addAll(
        portfolioButton,
        transactionArchiveButton,
        exchangeButton
    );
  }
}