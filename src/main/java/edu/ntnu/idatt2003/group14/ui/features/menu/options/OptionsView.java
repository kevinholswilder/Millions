package edu.ntnu.idatt2003.group14.ui.features.menu.options;

import edu.ntnu.idatt2003.group14.config.lang.LangConfig;
import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.app.View;
import edu.ntnu.idatt2003.group14.ui.components.MenuButtonFactory;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX scene for the Options screen of the application.
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
 */
public final class OptionsView implements View {
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
        Objects.requireNonNull(getClass().getResource("/stylesheets/menu.css")).toExternalForm()
    );
    this.root.getChildren().addAll(backgroundView(), centerMenu());

    // Prevent autofocus
    this.root.sceneProperty().addListener((_, _, newScene) -> {
      if (newScene != null) {
        this.root.requestFocus();
      }
    });

    this.root.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        controller.handleReturnToMainMenu();
      }
    });
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
    StackPane ambiance = createLabeledSlider(
        ambianceSlider,
        LangConfig.getInstance().lang("options-menu.music")
    );

    Slider effectsSlider = new Slider(0, 100, audioManager.getSoundEffectVolume() * 100);
    effectsSlider.getStyleClass().add("menu-slider");
    effectsSlider.valueProperty().addListener(
        (_, _, newVal) -> controller.handleSoundEffectVolume(newVal.doubleValue())
    );
    StackPane effects = createLabeledSlider(
        effectsSlider,
        LangConfig.getInstance().lang("options-menu.sound_effects")
    );

    // Language option
    ComboBox<String> languageBox = new ComboBox<>();
    languageBox.getStyleClass().add("menu-button");

    languageBox.getItems().addAll(
        "English",
        "Norwegian",
        "Dutch"
    );

    languageBox.setValue(LangConfig.getCurrentLanguage());
    languageBox.setOnAction(_ -> {
      String selected = languageBox.getValue();

      switch (selected) {
        case "English" -> LangConfig.getInstance().load("/lang/en_us.json");
        case "Dutch" -> LangConfig.getInstance().load("/lang/nl_nl.json");
        case "Norwegian" -> LangConfig.getInstance().load("/lang/no_no.json");
        default -> LangConfig.getInstance().load("/lang/en_us.json");
      }

      LangConfig.setLanguage(selected);

      controller.refresh();
    });

    Button fullScreenBtn = buttonFactory
        .createMenuButton(
            LangConfig.getInstance().lang("options-menu.toggle_full_screen"),
            controller::handleFullScreen
        );
    Button doneBtn = buttonFactory
        .createMenuButton(
            LangConfig.getInstance().lang("options-menu.return"),
            controller::handleReturnToMainMenu
        );

    btnBox.getChildren().addAll(ambiance, effects, languageBox, fullScreenBtn, doneBtn);
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
              return label + ": " + (val == 0 ? LangConfig.getInstance().lang("options-menu.off") :
                  String.format("%3d", val) + "%");
            })
    );

    return new StackPane(slider, valueLabel);
  }
}
