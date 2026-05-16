package edu.ntnu.idatt2003.group14.io.gamesave;

/**
 * Metadata for a saved game, used to populate the load game menu
 * without deserializing the full save file.
 *
 * @param uuid       the unique identifier of the save file
 * @param playerName the name of the player at the time of saving
 * @param savedAt    the timestamp of when the save was created
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 1.0.0
 */
public record SaveMetadata(String uuid, String playerName, String savedAt) {
}
