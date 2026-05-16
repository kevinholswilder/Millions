package edu.ntnu.idatt2003.group14.io.gamesave;

import edu.ntnu.idatt2003.group14.model.Exchange;
import edu.ntnu.idatt2003.group14.model.Player;
import edu.ntnu.idatt2003.group14.model.Stock;
import java.math.BigDecimal;
import java.util.List;

/**
 * Data transfer object for serializing and deserializing a game state.
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 1.0.0
 */
public class SaveFileDTO {
  String playerName;
  String exchangeName;
  int exchangeWeek;
  String netWorth;
  List<StockDTO> stocks;

  /**
   * Constructs a {@code SaveFileDTO} from the current game state.
   *
   * @param player   the player to serialize
   * @param exchange the exchange to serialize
   */
  SaveFileDTO(Player player, Exchange exchange) {
    this.playerName = player.getName();
    this.exchangeName = exchange.getName();
    this.exchangeWeek = exchange.getWeek();
    this.netWorth = player.getNetWorth().toPlainString();
    this.stocks = exchange.getStocks().stream()
        .map(StockDTO::new)
        .toList();
  }

  /**
   * Reconstructs the domain objects from this DTO.
   *
   * @return a {@link LoadResult} containing the restored player and exchange
   */
  LoadResult toDomain() {
    List<Stock> domainStocks = stocks.stream()
        .map(StockDTO::toDomain)
        .toList();
    Exchange exchange = new Exchange(exchangeName, domainStocks, exchangeWeek);
    Player player = new Player(playerName, new BigDecimal(netWorth));
    return new LoadResult(player, exchange);
  }

  record LoadResult(Player player, Exchange exchange) {
  }
}
