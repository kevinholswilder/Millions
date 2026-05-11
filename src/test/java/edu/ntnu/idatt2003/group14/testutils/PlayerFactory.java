package edu.ntnu.idatt2003.group14.testutils;

import edu.ntnu.idatt2003.group14.model.Player;
import java.math.BigDecimal;

/**
 * Player factory class.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public final class PlayerFactory {

  private PlayerFactory() {}

  /**
   * <pre>
   * Creates a new player with the values:
   * name: "Bernt"
   * startingMoney: 1000.0
   * </pre>
   *
   * @return a new player
   */
  public static Player createPlayer() {
    return new Player(
        "Bernt",
        BigDecimal.valueOf(1000.0)
    );
  }
}
