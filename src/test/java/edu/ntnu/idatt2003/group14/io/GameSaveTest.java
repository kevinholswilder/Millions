package edu.ntnu.idatt2003.group14.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group14.io.gamesave.GameSaveManager;
import edu.ntnu.idatt2003.group14.model.Exchange;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.testutils.ExchangeFactory;
import edu.ntnu.idatt2003.group14.testutils.PlayerFactory;
import edu.ntnu.idatt2003.group14.utils.AppDataPathUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for GameSaveManager.
 *
 * <p>These tests write to the actual saves file and do not clean up afterwords.</p>
 *
 * @author EliasHaugsbakk
 * @version 1.0.0
 * @since 1.0.0
 */
public class GameSaveTest {
  private GameSaveManager gameSaveManager;
  private Exchange exchange;
  private Player player;

  @BeforeEach
  void setup() throws IOException {
    this.gameSaveManager = new GameSaveManager();
    this.exchange = ExchangeFactory.createExchange();
    this.player = PlayerFactory.createPlayer();

    // delete the directory
    Path savesDir = AppDataPathUtil.getDataDirectory().resolve("saves");
    Files.list(savesDir).forEach(p -> p.toFile().delete());
    Files.delete(savesDir);
  }

  @Test
  @Tag("integration")
  void save_persistsCorrectPlayerData() throws IOException {
    this.exchange.advance();
    this.exchange.advance();
    gameSaveManager.save(this.player, this.exchange);
    String uuid = gameSaveManager.listSaves().getLast().uuid();
    GameSaveManager.SaveGame save = gameSaveManager.load(uuid);
    assertEquals(this.player.getName(), save.player().getName());
    assertEquals(this.player.getNetWorth(), save.player().getNetWorth());
  }

  @Test
  @Tag("integration")
  void save_persistsCorrectExchangeData() throws IOException {
    this.exchange.advance();
    this.exchange.advance();
    gameSaveManager.save(this.player, this.exchange);
    String uuid = gameSaveManager.listSaves().getLast().uuid();
    GameSaveManager.SaveGame save = gameSaveManager.load(uuid);
    assertEquals(this.exchange.getName(), save.exchange().getName());
    assertEquals(this.exchange.getWeek(), save.exchange().getWeek());
  }

  @Test
  @Tag("integration")
  void delete_removesGameFromIndex() throws IOException {
    gameSaveManager.save(this.player, this.exchange);
    String uuid = gameSaveManager.listSaves().getLast().uuid();
    gameSaveManager.delete(uuid);
    assertThrows(IOException.class, () -> gameSaveManager.load(uuid));
  }
}
