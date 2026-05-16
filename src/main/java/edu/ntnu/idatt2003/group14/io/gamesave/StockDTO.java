package edu.ntnu.idatt2003.group14.io.gamesave;

import edu.ntnu.idatt2003.group14.model.Stock;
import java.math.BigDecimal;
import java.util.List;

/**
 * Data transfer object for serializing and deserializing a {@link Stock}.
 *
 * <p>Price history is stored as plain strings to preserve {@link BigDecimal}
 * precision.</p>
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 1.0.0
 */
class StockDTO {
  String symbol;
  String company;
  List<String> priceHistory;

  /**
   * Constructs a {@code StockDTO} from the given stock.
   *
   * @param stock the stock to serialize
   */
  StockDTO(Stock stock) {
    this.symbol = stock.getSymbol();
    this.company = stock.getCompany();
    this.priceHistory = stock.getHistoricalPrices().stream()
        .map(BigDecimal::toPlainString)
        .toList();
  }

  /**
   * Reconstructs a {@link Stock} domain object from this DTO.
   *
   * @return the restored stock
   */
  Stock toDomain() {
    List<BigDecimal> prices = priceHistory.stream()
        .map(BigDecimal::new)
        .toList();
    return new Stock(this.symbol, this.company, prices);
  }
}
