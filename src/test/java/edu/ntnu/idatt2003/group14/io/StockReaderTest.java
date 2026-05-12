package edu.ntnu.idatt2003.group14.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group14.io.reader.stock.StockReader;
import edu.ntnu.idatt2003.group14.model.Stock;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Class responsible for testing the {@link StockReader} class.
 *
 * <p>Does not contain any integration tests</p>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class StockReaderTest {
  private StockReader reader;

  @BeforeEach
  void setUp() {
    reader = new StockReader();
  }

  @Test
  void parse_converts_valid_strings_to_stocks() throws IOException {
    List<String> input = List.of(
        "# comment",
        "SWOI,SwedenOil,00.001",
        "NOLI,NordicLifts,120.00,19"
    );

    List<Stock> stocks = reader.parse(input);

    assertEquals(2, stocks.size());
    assertEquals("SWOI", stocks.getFirst().getSymbol());
    assertEquals("NOLI", stocks.getLast().getSymbol());
  }

  @Test
  void parse_throws_exception_on_invalid_format() {
    List<String> input = List.of("Bad CSV line");
    assertThrows(IOException.class, () -> reader.parse(input));
  }

  @Test
  @Disabled("Manual test only; IO access")
  void read_actually_reads_from_disk(@TempDir Path tempDir) throws IOException {
    // Tests the path resolution and interaction with the OS
    // This test is disabled since it is directly testing file reading

    Path file = tempDir.resolve("test.csv");
    Files.writeString(file, "SWOI,SwedenOil,00.001");

    var stocks = reader.read(file.toString());
    assertFalse(stocks.isEmpty());
    assertEquals("SWOI", stocks.getFirst().getSymbol());
  }
}
