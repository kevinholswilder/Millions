package edu.ntnu.idatt2003.group14.ui.components;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class SideBar extends BorderPane {

    public SideBar() {
        Header header = new Header();
        Footer footer = new Footer();
        ExchangeOverview center = new ExchangeOverview();

        ScrollPane scrollPane = new ScrollPane(center);
        scrollPane.setFitToWidth(true);

        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);

        sceneProperty().addListener((_, _, newScene) -> {
            prefWidthProperty().bind(newScene.widthProperty().multiply(0.15));
        });
    }

}