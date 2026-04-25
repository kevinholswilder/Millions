package edu.ntnu.idatt2003.group14.ui.app;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewManager {
    private static final int DEFAULT_WIDTH = 1280;
    private static final int DEFAULT_HEIGHT = 720;

    private static Stage stage;

    public ViewManager(Stage stage) {
        ViewManager.stage = stage;
    }

    public static void navigateTo(Parent root) {
        // First time setup
        if (stage.getScene() == null) {
            Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            String css = Objects.requireNonNull(ViewManager.class.getResource("/css/style.css")).toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
        }
        stage.getScene().setRoot(root);
    }
}
