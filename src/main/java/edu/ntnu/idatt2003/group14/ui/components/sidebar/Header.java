package edu.ntnu.idatt2003.group14.ui.components.sidebar;

import edu.ntnu.idatt2003.group14.ui.transaction.TransactionArchiveView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 */
public class Header extends VBox {
    private final double MAX_BUTTON_WIDTH = Double.MAX_VALUE;
    private final double PREFERRED_BUTTON_HEIGHT = 100.0;

    private SideBarController controller;
    /**
     *
     */
    public Header(SideBarController controller) {
        this.controller = controller;

//        Button portfolioButton = controller.createNavigationButton("Portfolio", new PortfolioView().getRoot());
//        portfolioButton.setMaxWidth(this.MAX_BUTTON_WIDTH);
//        portfolioButton.setPrefHeight(this.PREFERRED_BUTTON_HEIGHT);

        Button transactionArchiveButton = controller.createNavigationButton("Transactions", new TransactionArchiveView().getRoot());
        transactionArchiveButton.setMaxWidth(this.MAX_BUTTON_WIDTH);
        transactionArchiveButton.setPrefHeight(this.PREFERRED_BUTTON_HEIGHT);

        this.getChildren().addAll(transactionArchiveButton);
    }

}
