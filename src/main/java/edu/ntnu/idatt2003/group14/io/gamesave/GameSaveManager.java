package edu.ntnu.idatt2003.group14.io.gamesave;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.ntnu.idatt2003.group14.model.Exchange;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.utils.AppDataPathUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Manages saving, loading, and deleting game saves.
 *
 * <p>Each save is stored as a separate JSON file identified by a UUID
 * with a shared index file containing metadata for all saves.</p>
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 1.0.0
 */
public class GameSaveManager {
  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private final Path savesDir = AppDataPathUtil.getDataDirectory().resolve("saves");
  private final Path indexPath = savesDir.resolve("index.json");

  /**
   * Represents a loaded game, containing the restored player and exchange.
   */
  public record SaveGame(Player player, Exchange exchange) {
  }

  /**
   * Saves the current game state to disk.
   *
   * <p>Creates a new save file identified by a UUID and
   * updates the save index with the associated metadata.</p>
   *
   * @param player   the player whose state should be saved
   * @param exchange the exchange whose state should be saved
   * @throws IOException if the save file or index cannot be written
   */
  public void save(Player player, Exchange exchange) throws IOException {
    Files.createDirectories(savesDir);
    String uuid = UUID.randomUUID().toString();
    String savedAt = LocalDateTime.now().toString();

    Path savePath = savesDir.resolve(uuid + ".json");
    Files.writeString(savePath, gson.toJson(new SaveFileDTO(player, exchange)));

    List<SaveMetadata> index = listSaves();
    index.add(new SaveMetadata(uuid, player.getName(), savedAt));
    Files.writeString(indexPath, gson.toJson(index));
  }

  /**
   * Loads a saved game by UUID.
   *
   * @param uuid the UUID of the save to load
   * @return a {@link SaveGame} containing the restored player and exchange
   * @throws IOException if the save file cannot be read
   */
  public SaveGame load(String uuid) throws IOException {
    Path savePath = savesDir.resolve(uuid + ".json");
    SaveFileDTO.LoadResult domainData =
        gson.fromJson(Files.readString(savePath), SaveFileDTO.class).toDomain();
    return new SaveGame(domainData.player(), domainData.exchange());
  }

  /**
   * Deletes a saved game by UUID.
   *
   * <p>Removes the save file from disk and removes the corresponding
   * entry from the save index.</p>
   *
   * @param uuid the UUID of the save to delete
   * @throws IOException if the save file or index cannot be updated
   */
  public void delete(String uuid) throws IOException {
    Files.deleteIfExists(savesDir.resolve(uuid + ".json"));
    List<SaveMetadata> index = listSaves();
    index.removeIf(m -> m.uuid().equals(uuid));
    Files.writeString(indexPath, gson.toJson(index));
  }

  /**
   * Returns a list of metadata for all existing saves.
   *
   * @return a mutable list of {@link SaveMetadata}
   * @throws IOException if the index file cannot be read
   */
  public List<SaveMetadata> listSaves() throws IOException {
    if (!Files.exists(indexPath)) {
      return new ArrayList<>();
    }
    return gson.fromJson(
        Files.readString(indexPath),
        new TypeToken<List<SaveMetadata>>() {
        }.getType()
    );
  }
}
