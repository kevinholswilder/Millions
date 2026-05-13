package edu.ntnu.idatt2003.group14.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2003.group14.io.writer.stock.StockWriter;
import edu.ntnu.idatt2003.group14.model.Stock;
import edu.ntnu.idatt2003.group14.testutils.StockFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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

  @BeforeEach
  void setUp() {
    writer = new StockWriter();
  }

  @Test
  void toCsvLine_formats_stock_data_correctly() {
    Stock stock = StockFactory.createStock();

    String result = writer.toCsvLine(stock);

    assertEquals("AMCH,Amazing Chairs,1,2,3,4,5", result);
  }

  @Test
  @Tag("integration")
  void writeStocksToFile_successfully_creates_file_on_disk(@TempDir Path tempDir)
      throws IOException {
    Path filePath = tempDir.resolve("integration_test.csv");
    var stocks = StockFactory.createStocks(2);

    writer.writeStocksToFile(stocks, filePath.toFile());

    assertTrue(Files.exists(filePath), "The file should be physically created on disk.");
    assertEquals(2, Files.readAllLines(filePath).size());
  }

  @Test
  @Tag("integration")
  void writeStockToFile_overwrites_existing_file_content(@TempDir Path tempDir) throws IOException {
    Path filePath = tempDir.resolve("overwrite_test.csv");

    Files.writeString(filePath, "Old Content,Should be gone");

    Stock newStock = StockFactory.createStock();
    writer.writeStockToFile(newStock, filePath.toFile());

    List<String> lines = Files.readAllLines(filePath);
    assertEquals(1, lines.size());
    assertTrue(lines.getFirst().contains("AMCH"));
  }
}
