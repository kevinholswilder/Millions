package edu.ntnu.idatt2003.group14.ui;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.app.AppController;
import edu.ntnu.idatt2003.group14.ui.app.AppControllerImpl;
import edu.ntnu.idatt2003.group14.ui.app.AppNavigator;
import edu.ntnu.idatt2003.group14.ui.app.AppNavigatorImpl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The JavaFx entry point. Responsible for initializing
 * the necessary classes and starting the application.
 */
public final class App extends Application {

  @Override
  public void start(Stage stage) {
    stage.setTitle("Millions");

    AudioManager audioManager = new AudioManager();
    audioManager.playOfficeNoice();

    AppController appController = new AppControllerImpl(stage);
    AppNavigator appNavigator = new AppNavigatorImpl(stage, appController, audioManager);
    appNavigator.showMainMenuView();

    stage.show();
  }
}
