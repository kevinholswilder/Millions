package edu.ntnu.idatt2003.group14.ui.components.sidebar;

import edu.ntnu.idatt2003.group14.config.lang.LangConfig;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Header component containing sidebar navigation buttons.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public final class Header extends VBox {

  /**
   * Creates a new header with navigation buttons.
   *
   * @param controller controller used to create navigation buttons
   */
  public Header(SideBarController controller) {
    Button portfolioButton = controller.createPortfolioButton(
            LangConfig.getInstance().lang("sidebar-header.portfolio")
    );

    Button transactionArchiveButton = controller.createTransactionButton(
            LangConfig.getInstance().lang("sidebar-header.transactions")
    );

    Button exchangeButton = controller.createExchangeButton(
        LangConfig.getInstance().lang("sidebar-header.exchange")
    );

    this.getChildren().addAll(
        portfolioButton,
        transactionArchiveButton,
        exchangeButton
    );
  }
}