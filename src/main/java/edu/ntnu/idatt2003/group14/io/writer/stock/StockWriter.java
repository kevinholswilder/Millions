package edu.ntnu.idatt2003.group14.io.writer.stock;

import edu.ntnu.idatt2003.group14.io.CSVLabels;
import edu.ntnu.idatt2003.group14.model.Stock;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Responsible for serializing {@link Stock} objects into CSV format and writing them to file.
 *
 * <p>The class distinguishes between formatting logic and IO operations to allow for isolated
 * unit testing.</p>
 *
 * @author Kevin Holswilder, Elias Haugsbakk
 * @since 0.0.1
 */
public class StockWriter {

  /**
   * Converts a single {@link Stock} object into a CSV-formatted string line.
   *
   * @param stock the stock object to be formatted
   * @return a CSV-formatted string representation of the stock
   */
  public String toCsvLine(Stock stock) {
    String symbol = stock.getSymbol();
    String company = stock.getCompany();
    List<BigDecimal> prices = stock.getHistoricalPrices();

    String priceString =
        prices.stream().map(BigDecimal::toString).collect(Collectors.joining(CSVLabels.SEPARATOR));

    return String.join(CSVLabels.SEPARATOR, symbol, company, priceString);
  }

  /**
   * Writes a single {@link Stock} to a file.
   *
   * @param stock the stock to write to disk
   * @param file  the {@link File} to write the stock data to
   * @throws IOException if an error occurs during the writing process
   */
  public void writeStockToFile(Stock stock, File file) throws IOException {
    writeStocksToFile(List.of(stock), file);
  }

  /**
   * Writes a list of {@link Stock} objects to a file, one stock per line.
   *
   * @param stocks the list of stocks to write to disk
   * @param file   the {@link File} to write the stock data to
   * @throws IOException if an error occurs during the writing process
   */
  public void writeStocksToFile(List<Stock> stocks, File file) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
      for (Stock stock : stocks) {
        writer.println(toCsvLine(stock));
      }
    }
  }
}
