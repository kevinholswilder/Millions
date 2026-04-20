package edu.ntnu.idatt2003.group14.ui.features.menu.newgame;

/**
 * Contains information on whether the application can start. If not, it gives the cause.
 */
public enum NewGameValidationState {
  VALID,
  NEGATIVE_AMOUNT,
  NOT_A_NUMBER,
  EMPTY_USERNAME,
  NO_FILE_CHOSEN
}
