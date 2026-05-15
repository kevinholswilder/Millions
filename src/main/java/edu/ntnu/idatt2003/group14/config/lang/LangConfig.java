package edu.ntnu.idatt2003.group14.config.lang;

import com.google.gson.reflect.TypeToken;
import edu.ntnu.idatt2003.group14.config.GameConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Language configuration singleton.
 *
 * <p>Loads key-value pairs from a JSON file such as:</p>
 * <pre>{@code
 * {
 *   "main-menu.new_game": "New Game",
 *   "main-menu.options": "Options",
 * }
 * }</pre>
 *
 * @author Kevin Holswilder
 * @since 1.0.0
 */
public final class LangConfig {
  private static String currentLanguage = "English";

  private static LangConfig instance;

  private final Map<String, String> langMap = new HashMap<>();

  // Prevent instantiation
  private LangConfig() {}

  /**
   * Returns the singleton instance.
   *
   * @return singleton instance
   */
  public static LangConfig getInstance() {
    if (instance == null) {
      instance = new LangConfig();
    }

    return instance;
  }

  /**
   * Loads language entries from a JSON resource file.
   *
   * @param resourcePath path to the language resource (e.g. {@code "/lang/en_us.json"})
   * @throws IllegalArgumentException if the resource is missing
   * @throws IllegalStateException if loading fails
   */
  public void load(String resourcePath) {
    Type type = new TypeToken<Map<String, String>>() {}.getType();

    try (InputStream is = LangConfig.class.getResourceAsStream(resourcePath)) {
      if (is == null) {
        throw new IllegalArgumentException("Language file not found: " + resourcePath);
      }

      try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
        Map<String, String> loadedMap = GameConfig.GSON.fromJson(reader, type);

        langMap.clear();

        if (loadedMap != null) {
          langMap.putAll(loadedMap);
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load language file: " + resourcePath, e);
    }
  }

  /**
   * Returns the translated value for the given key.
   *
   * <p>If the key does not exist, the key itself is returned.</p>
   *
   * @param key translation key
   * @return translated string or the key itself if missing
   */
  public String lang(String key) {
    return langMap.getOrDefault(key, key);
  }

  /**
   * Sets the currently selected language.
   *
   * @param language the language name to set as the current language (e.g. {@code "Norwegian"})
   */
  public static void setLanguage(String language) {
    currentLanguage = language;
  }

  /**
   * Returns the currently selected language.
   *
   * @return the current language code or name
   */
  public static String getCurrentLanguage() {
    return currentLanguage;
  }
}