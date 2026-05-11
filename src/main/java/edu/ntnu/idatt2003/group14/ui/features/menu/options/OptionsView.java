package edu.ntnu.idatt2003.group14.ui.features.menu.options;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.app.View;
import edu.ntnu.idatt2003.group14.ui.components.MenuButtonFactory;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX scene for the Options screen of the application.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class OptionsView implements View {
  private final StackPane root;
  private final OptionsController controller;
  private final MenuButtonFactory buttonFactory;
  private final AudioManager audioManager;

  /**
   * Initializes a new OptionsView.
   *
   * @param controller   the controller for this view
   * @param audioManager the audio manager
   */
  public OptionsView(OptionsController controller, AudioManager audioManager) {
    this.controller = controller;
    this.buttonFactory = new MenuButtonFactory(audioManager);
    this.audioManager = audioManager;
    this.root = new StackPane();
    this.root.getStylesheets().add(
        Objects.requireNonNull(getClass().getResource("/css/menu.css")).toExternalForm()
    );

    this.root.getChildren().addAll(backgroundView(), centerMenu());
  }

  @Override
  public Parent getRoot() {
    return this.root;
  }

  private Region backgroundView() {
    Region bg = new Region();
    bg.getStyleClass().add("main-menu-root-container");
    bg.setEffect(new GaussianBlur(10));
    return bg;
  }

  private VBox centerMenu() {

    VBox centerMenu = new VBox(50);
    centerMenu.setAlignment(Pos.CENTER);
    centerMenu.setPadding(new Insets(100, 0, 0, 0));

    VBox btnBox = new VBox(15);
    btnBox.setAlignment(Pos.CENTER);

    Slider ambianceSlider = new Slider(0, 100, audioManager.getMusicVolume() * 100);
    ambianceSlider.getStyleClass().add("menu-slider");
    ambianceSlider.valueProperty().addListener(
        (_, _, newVal) -> controller.handleMusicVolume(newVal.doubleValue())
    );
    StackPane ambiance = createLabeledSlider(ambianceSlider, "Music");

    Slider effectsSlider = new Slider(0, 100, audioManager.getSoundEffectVolumeVolume() * 100);
    effectsSlider.getStyleClass().add("menu-slider");
    effectsSlider.valueProperty().addListener(
        (_, _, newVal) -> controller.handleSoundEffectVolume(newVal.doubleValue())
    );
    StackPane effects = createLabeledSlider(effectsSlider, "Sound Effects");


    Button fullScreenBtn = buttonFactory
        .createMenuButton("Toggle Full Screen", controller::handleFullScreen);
    Button doneBtn = buttonFactory.createMenuButton("Done", controller::handleReturnToMainMenu);

    btnBox.getChildren().addAll(ambiance, effects, fullScreenBtn, doneBtn);
    centerMenu.getChildren().add(btnBox);

    return centerMenu;
  }

  private static StackPane createLabeledSlider(Slider slider, String label) {
    Label valueLabel = new Label();
    valueLabel.getStyleClass().add("menu-slider-label");
    valueLabel.setMouseTransparent(true);

    valueLabel.textProperty().bind(
        slider.valueProperty().asObject()
            .map(v -> {
              int val = (int) Math.round(v);
              return label + ": " + (val == 0 ? "OFF" : String.format("%3d", val) + "%");
            })
    );

    return new StackPane(slider, valueLabel);
  }
}
