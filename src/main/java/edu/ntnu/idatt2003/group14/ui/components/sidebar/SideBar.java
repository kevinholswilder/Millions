package edu.ntnu.idatt2003.group14.ui.components.sidebar;

import edu.ntnu.idatt2003.group14.ui.components.ExchangeOverview;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class SideBar extends BorderPane {
    private SideBarController controller;

    public SideBar() {
        this.controller = new SideBarController();
        Header header = new Header(controller);
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