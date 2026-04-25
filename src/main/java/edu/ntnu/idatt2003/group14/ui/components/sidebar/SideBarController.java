package edu.ntnu.idatt2003.group14.ui.components.sidebar;

import edu.ntnu.idatt2003.group14.ui.components.Navigable;
import edu.ntnu.idatt2003.group14.ui.app.ViewManager;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class SideBarController implements Navigable {

    @Override
    public Button createNavigationButton(String text, Parent root) {
        Button button = new Button(text);

        button.setOnAction(_ -> {
            ViewManager.navigateTo(root);
        });

        return button;
    }
}
