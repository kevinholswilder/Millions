package edu.ntnu.idatt2003.group14.service;

import edu.ntnu.idatt2003.group14.logging.AppLogger;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Controller for handling background music and sound effects.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class AudioManager {
  private static final String MENU_MUSIC = "/audio/music/background-music.wav";
  private static final String OFFICE_NOICE = "/audio/music/office-background.wav";
  private static final String BUTTON_CLICK = "/audio/sounds/button-click.wav";
  private static final String BUTTON_HOVER = "/audio/sounds/button-hover.wav";
  private static final String ERROR = "/audio/sounds/error.wav";

  private final AudioClip buttonClickClip;
  private final AudioClip buttonHoverClip;
  private final AudioClip errorClip;

  private MediaPlayer musicPlayer;
  private double musicVolume = 0.3;
  private double soundEffectVolume = 0.3;

  /**
   * Initializes a new audio manager.
   */
  public AudioManager() {
    buttonClickClip = loadClip(BUTTON_CLICK);
    buttonHoverClip = loadClip(BUTTON_HOVER);
    errorClip = loadClip(ERROR);
  }

  private AudioClip loadClip(String resourcePath) {
    var resource = getClass().getResource(resourcePath);
    if (resource == null) {
      AppLogger.error("Sound effect not found: " + resourcePath);
      throw new IllegalArgumentException("Sound effect not found: " + resourcePath);
    }
    return new AudioClip(resource.toExternalForm());
  }

  /**
   * Starts the menu music loop.
   */
  public void playMenuMusic() {
    playLoopingMusic(MENU_MUSIC);
  }

  /**
   * Starts the office background noice loop.
   */
  public void playOfficeNoice() {
    playLoopingMusic(OFFICE_NOICE);
  }

  /**
   * Play the button click sound effect.
   */
  public void playButtonClickSound() {
    playSoundEffect(buttonClickClip);
  }

  /**
   * Play the button hover sound effect.
   */
  public void playButtonHoverSound() {
    playSoundEffect(buttonHoverClip);
  }

  /**
   * Play the error sound effect.
   */
  public void playErrorSound() {
    playSoundEffect(errorClip);
  }

  private void playLoopingMusic(String resourcePath) {
    try {
      var resource = getClass().getResource(resourcePath);
      if (resource == null) {
        throw new IllegalArgumentException("Music resource not found: " + resourcePath);
      }

      Media media = new Media(resource.toExternalForm());

      if (musicPlayer != null) {
        musicPlayer.stop();
      }

      musicPlayer = new MediaPlayer(media);
      musicPlayer.setVolume(musicVolume);

      musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

      musicPlayer.play();
    } catch (Exception e) {
      AppLogger.error("Could not play music", e);
    }
  }

  private void playSoundEffect(AudioClip clip) {
    clip.setVolume(soundEffectVolume);
    clip.play();
  }

  /**
   * Sets the volume of the music.
   *
   * @param volume double value between 0 and 1
   */
  public void setMusicVolume(double volume) {
    this.musicVolume = volume;
    if (musicPlayer != null) {
      musicPlayer.setVolume(volume);
    }
  }

  /**
   * Gets the volume of the music.
   *
   * @return the music volume from 0 to 1
   */
  public double getMusicVolume() {
    return this.musicVolume;
  }

  /**
   * Sets the volume of the sound effects.
   *
   * @param volume double value between 0 and 1
   */
  public void setSoundEffectVolume(double volume) {
    this.soundEffectVolume = volume;
  }

  /**
   * Gets the volume of the sound effects.
   *
   * @return the sound effect volume from 0 to 1
   */
  public double getSoundEffectVolumeVolume() {
    return this.soundEffectVolume;
  }

  /**
   * Stops the playing music.
   */
  public void stopMusic() {
    if (musicPlayer != null) {
      musicPlayer.stop();
    }
  }
}
