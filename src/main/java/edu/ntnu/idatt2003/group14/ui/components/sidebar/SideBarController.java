package edu.ntnu.idatt2003.group14.ui.components.sidebar;

import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;
import edu.ntnu.idatt2003.group14.ui.exchange.ExchangeView;
import edu.ntnu.idatt2003.group14.ui.features.portfolio.PortfolioView;
import edu.ntnu.idatt2003.group14.ui.features.transaction.TransactionArchiveView;
import javafx.scene.control.Button;

/**
 * Controller responsible for creating and configuring sidebar navigation buttons.
 *
 * <p>Each button is connected to a specific navigation action handled by the
 * {@link AppNavigator}.
 * </p>
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class SideBarController {
  /**
   * Maximum width applied to all sidebar buttons.
   */
  private static final double MAX_BUTTON_WIDTH = Double.MAX_VALUE;

  /**
   * Preferred height applied to all sidebar buttons.
   */
  private static final double PREFERRED_BUTTON_HEIGHT = 100.0;

  /**
   * Navigator used to switch between views.
   */
  private final AppNavigator appNavigator;

  /**
   * Instantiates a new controller.
   *
   * @param appNavigator the navigator responsible for handling view transitions
   */
  public SideBarController(AppNavigator appNavigator) {
    this.appNavigator = appNavigator;
  }

  /**
   * Creates a navigation button that opens the {@link TransactionArchiveView}.
   *
   * @param text the text displayed on the button
   * @return a configured transaction navigation button
   */
  public Button createTransactionButton(String text) {
    return createNavigationButton(
        text,
        appNavigator::showTransactionArchiveView
    );
  }

  /**
   * Creates a navigation button that opens the {@link PortfolioView}.
   *
   * @param text the text displayed on the button
   * @return a configured portfolio navigation button
   */
  public Button createPortfolioButton(String text) {
    return createNavigationButton(
        text,
        appNavigator::showPortfolioView
    );
  }

  /**
   * Creates a navigation button that opens the {@link ExchangeView}.
   *
   * @param text the text displayed on the button
   * @return a configured exchange navigation button
   */
  public Button createExchangeButton(String text) {
    return createNavigationButton(
        text,
        appNavigator::showExchangeView
    );
  }

  /**
   * Creates and configures a navigation button with a specified action.
   *
   * <p>The button is configured with predefined sizing and an action handler
   * that executes the provided navigation action when clicked.
   * </p>
   *
   * @param text the text displayed on the button
   * @param action the action executed when the button is pressed
   * @return a configured navigation button
   */
  private Button createNavigationButton(String text, Runnable action) {
    Button button = new Button(text);

    button.setMaxWidth(MAX_BUTTON_WIDTH);
    button.setPrefHeight(PREFERRED_BUTTON_HEIGHT);

    button.setOnAction(_ -> action.run());

    return button;
  }
}
