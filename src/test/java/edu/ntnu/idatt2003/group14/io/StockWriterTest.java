package edu.ntnu.idatt2003.group14.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2003.group14.io.writer.stock.StockWriter;
import edu.ntnu.idatt2003.group14.testutils.StockFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Class responsible for testing the {@link StockWriter} class.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class StockWriterTest {
  private StockWriter writer;

  @TempDir
  Path tempDir;

  @BeforeEach
  void setUp() {
    writer = new StockWriter();

  }

  @Test
  void writeStockToFile_produces_correct_csv_line() throws IOException {
    var file = tempDir.resolve("stocks.csv").toFile();

    var stock = StockFactory.createStock();
    writer.writeStockToFile(stock, file);

    var lines = Files.readAllLines(file.toPath());
    assertEquals(1, lines.size());
    assertEquals("AMCH,Amazing Chairs,1,2,3,4,5", lines.getFirst());
  }

  @Test
  void writeStocksToFile_produces_correct_csv_line() throws IOException {
    var file = tempDir.resolve("stocks.csv").toFile();

    var stocks = StockFactory.createStocks(5);

    writer.writeStocksToFile(stocks, file);

    var lines = Files.readAllLines(file.toPath());
    assertEquals(5, lines.size());
    assertEquals("AMCH,Amazing Chairs,1,2,3,4,5", lines.getFirst());
    assertEquals("NOCA,Nordic Cars,1,2,3,4,5", lines.getLast());
  }
}
