package edu.ntnu.idatt2003.group14.io.reader.stock;

import edu.ntnu.idatt2003.group14.io.CSVLabels;
import edu.ntnu.idatt2003.group14.io.reader.CSVReader;
import edu.ntnu.idatt2003.group14.model.Stock;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class for reading stock data from a CSV file.
 *
 * @author Kevin Holswilder
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
   * @throws IOException if the file cannot be read
   */
  public List<Stock> read(String filename) throws IOException {
    List<String> data = readFromFile(filename);

    ArrayList<Stock> stocks = new ArrayList<>();
    for (String line : data) {
      if (line.startsWith(CSVLabels.COMMENT_CHAR) || line.isEmpty()) {
        continue;
      }
      String[] split = line.split(CSVLabels.SEPARATOR);
      List<BigDecimal> prices = new ArrayList<>();

      for (int i = 2; i < split.length; i++) {
        prices.add(new BigDecimal(split[i]));
      }

      Stock stock = new Stock(
          split[0],
          split[1],
          prices
      );
      stocks.add(stock);
    }
    return stocks;
  }

}