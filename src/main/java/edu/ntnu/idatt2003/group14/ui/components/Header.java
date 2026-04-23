package edu.ntnu.idatt2003.group14.ui.components;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 */
public class Header extends VBox {

    /**
     *
     */
    public Header() {
        Button portfolioButton = this.createHeaderButton(
                "Portfolio",
                Double.MAX_VALUE,
                100.0
        );

        Button transactioArchiveButton = this.createHeaderButton(
                "Portfolio",
                Double.MAX_VALUE,
                100.0
        );

        this.getChildren().addAll(portfolioButton, transactioArchiveButton);
    }

    /**
     *
     * @param text
     * @param width
     * @param height
     * @return
     */
    private Button createHeaderButton(
            String text,
            double width,
            double height
    ) {
        Button button = new Button(text);
        button.setMaxWidth(width);
        button.setPrefHeight(height);
        return button;
    }

}
