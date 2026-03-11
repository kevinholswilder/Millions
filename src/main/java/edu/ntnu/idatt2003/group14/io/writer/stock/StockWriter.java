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
 * Represents a class for writing stock data to a CSV file.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
public class StockWriter {

  /**
   * Serializes a stock to a CSV file.
   *
   * <p>Example usage:
   *
   * <pre>
   * StockWriter writer = new StockWriter();
   * writer.writeStockToFile(stock, "stock.csv");
   * </pre>
   *
   * @param stock the stock to serialize
   * @param file the name of the CSV file to write to
   * @throws IOException if an I/O error occurs
   */
  public void writeStockToFile(Stock stock, File file) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {

      String symbol = stock.getSymbol();
      String company = stock.getCompany();
      List<BigDecimal> prices = stock.getHistoricalPrices();

      String priceString = prices.stream()
          .map(BigDecimal::toString)
          .collect(Collectors.joining(CSVLabels.SEPARATOR));

      String result = String.join(CSVLabels.SEPARATOR, symbol, company, priceString);

      writer.println(result);
    }
  }

  /**
   * Serializes a list of stocks to a CSV file.
   *
   * <p>Example usage:
   *
   * <pre>
   * StockWriter writer = new StockWriter();
   * writer.writeStocksToFile(stocks, "stocks.csv");
   * </pre>
   *
   * @param stocks the list of stocks to serialize
   * @param file the name of the CSV file to write to
   * @throws IOException if an I/O error occurs
   */
  public void writeStocksToFile(List<Stock> stocks, File file) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {

      for (Stock stock : stocks) {
        String symbol = stock.getSymbol();
        String company = stock.getCompany();
        List<BigDecimal> prices = stock.getHistoricalPrices();

        String priceString = prices.stream()
            .map(BigDecimal::toString)
            .collect(Collectors.joining(CSVLabels.SEPARATOR));

        String result = String.join(CSVLabels.SEPARATOR, symbol, company, priceString);

        writer.println(result);
      }
    }
  }

}