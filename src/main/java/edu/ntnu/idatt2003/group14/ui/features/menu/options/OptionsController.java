package edu.ntnu.idatt2003.group14.ui.features.menu.options;

import edu.ntnu.idatt2003.group14.service.AudioManager;
import edu.ntnu.idatt2003.group14.ui.app.AppController;
import edu.ntnu.idatt2003.group14.ui.app.AppRouter;

/**
 * Controller class for handling user input from the Options scene.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class OptionsController {
  private final AppController appController;
  private final AppRouter router;
  private final AudioManager audioManager;

  /**
   * Initializes a new OptionsController.
   *
   * @param appController the application controller
   * @param router        the application router
   * @param audioManager  the application audio manager
   */

  public OptionsController(AppController appController, AppRouter router,
                           AudioManager audioManager) {
    this.appController = appController;
    this.router = router;
    this.audioManager = audioManager;
  }

  /**
   * User requests to toggle fullscreen.
   */
  public void handleFullScreen() {
    appController.toggleFullScreen();
  }

  /**
   * User requests to return to main menu.
   */
  public void handleReturnToMainMenu() {
    router.back();
  }

  /**
   * User requests to set the music volume.
   *
   * @param value the volume of the music (0 to 100)
   */
  public void handleMusicVolume(double value) {
    audioManager.setMusicVolume(value / 100);
  }

  /**
   * User requests to set the sound effects volume.
   *
   * @param value the volume of the sound effects (0 to 100)
   */
  public void handleSoundEffectVolume(double value) {
    audioManager.setSoundEffectVolume(value / 100);
  }
}
