package edu.ntnu.idatt2003.group14.service;

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

  private MediaPlayer musicPlayer;
  private double musicVolume = 0.5;
  private double soundEffectVolume = 0.8;

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
    playSoundEffect(BUTTON_CLICK);
  }

  /**
   * Play the button hover sound effect.
   */
  public void playButtonHoverSound() {
    playSoundEffect(BUTTON_HOVER);
  }

  /**
   * Play the error sound effect.
   */
  public void playErrorSound() {
    playSoundEffect(ERROR);
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
      // TODO: implement proper logging
      System.err.println("Could not play music: " + e.getMessage());
    }
  }

  private void playSoundEffect(String resourcePath) {
    try {
      var resource = getClass().getResource(resourcePath);
      if (resource == null) {
        // TODO: implement proper logging
        System.err.println("Sound effect not found: " + resourcePath);
        return;
      }

      AudioClip clip = new AudioClip(resource.toExternalForm());
      clip.play(soundEffectVolume);
    } catch (Exception e) {
      // TODO: implement proper logging
      System.err.println("Could not play sound effect: " + e.getMessage());
    }
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
   * Sets the volume of the sound effects.
   *
   * @param volume double value between 0 and 1
   */
  public void setSoundEffectVolume(double volume) {
    this.soundEffectVolume = volume;
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
