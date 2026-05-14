package edu.ntnu.idatt2003.group14.io.reader.stock;

import edu.ntnu.idatt2003.group14.exception.csvreading.CSVError;
import edu.ntnu.idatt2003.group14.exception.csvreading.CSVReadException;
import edu.ntnu.idatt2003.group14.exception.csvreading.ParsingError;
import edu.ntnu.idatt2003.group14.io.CSVLabels;
import edu.ntnu.idatt2003.group14.io.reader.CSVReader;
import edu.ntnu.idatt2003.group14.logging.AppLogger;
import edu.ntnu.idatt2003.group14.model.Stock;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for reading from file and serializing CSV into {@link Stock} objects.
 *
 * <p>The class distinguishes between formatting logic and IO operations
 * to allow for isolated unit testing.</p>
 *
 * @author Kevin Holswilder, Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
 */
public final class StockReader extends CSVReader {

  /**
   * Reads stock data from a CSV file.
   *
   * <p>Example usage:
   *
   * <pre>
   *   StockReader reader = new StockReader();
   *   List&lt;Stock&gt; stocks = reader.read("stocks.csv");
   * </pre>
   *
   * @param filename the name of the CSV file to read from
   * @return a list of {@link Stock}s, or an empty list if the file is empty
   * @throws CSVReadException if the file cannot be read
   */
  public List<Stock> read(String filename) throws CSVReadException {
    List<String> data = readFromFile(filename);

    return parse(data);
  }

  /**
   * Parses a list of {@link String} containing {@link Stock} CSV data.
   *
   * @param data the data to parse
   * @return the {@link List} of {@link Stock}s
   * @throws CSVReadException if the parsing fails
   */
  public List<Stock> parse(List<String> data) throws CSVReadException {
    ArrayList<Stock> stocks = new ArrayList<>();
    for (int i = 0; i < data.size(); i++) {
      String line = data.get(i);

      if (line.startsWith(CSVLabels.COMMENT_CHAR) || line.isEmpty()) {
        continue;
      }

      String[] split = line.split(CSVLabels.SEPARATOR);
      if (split.length != 3) {
        AppLogger.error("Line " + (i + 1) + " is not formatted correctly");
        throw new CSVReadException("Line " + (i + 1) + " is not formatted correctly",
            CSVError.PARSING, new ParsingError(i + 1, line));
      }

      try {
        BigDecimal price = new BigDecimal(split[2].trim());
        Stock stock = new Stock(
            split[0].trim(),
            split[1].trim(),
            new ArrayList<>(List.of(price))
        );
        stocks.add(stock);
      } catch (NumberFormatException e) {
        AppLogger.error("Could not parse stock price at line " + (i + 1));
        throw new CSVReadException("Could not parse stock price at line " + (i + 1),
            CSVError.PARSING,
            new ParsingError(i + 1, line));
      }
    }

    if (stocks.isEmpty()) {
      AppLogger.error("CSV file did not contain any valid stocks");
      throw new CSVReadException("CSV file did not contain any valid stocks", CSVError.EMPTY_FILE);
    }
    return stocks;
  }
}
