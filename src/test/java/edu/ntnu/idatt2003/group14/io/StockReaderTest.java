package edu.ntnu.idatt2003.group14.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group14.io.reader.CSVReader;
import edu.ntnu.idatt2003.group14.io.reader.stock.StockReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Class responsible for testing the {@link StockReader} class.
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public class StockReaderTest {
  private StockReader reader;

  @TempDir
  Path tempDir;

  @BeforeEach
  void setUp() {
    reader = new StockReader();
  }

  @Test
  void read_parses_symbol_and_company() throws IOException {
    var stocks = reader.read("test-stocks.csv");
    assertEquals(2, stocks.size());
    assertEquals("SWOI", stocks.getFirst().getSymbol());
    assertEquals("SwedenOil", stocks.getFirst().getCompany());
  }

  @Test
  void read_throws_when_file_not_found() {
    assertThrows(IOException.class, () -> reader.read("does not exist"));
  }

  @Test
  void readFromFile_reads_from_filesystem_path() throws IOException {
    Path file = tempDir.resolve("stocks.csv");
    Files.writeString(file, "GEAU,German Automobile,120.00");

    var reader = new CSVReader();
    var lines = reader.readFromFile(file.toString());

    assertEquals(1, lines.size());
    assertEquals("GEAU,German Automobile,120.00", lines.getFirst());
  }
}
