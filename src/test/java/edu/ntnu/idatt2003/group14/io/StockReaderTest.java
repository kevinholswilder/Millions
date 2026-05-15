package edu.ntnu.idatt2003.group14.io;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group14.exception.csvreading.CSVError;
import edu.ntnu.idatt2003.group14.exception.csvreading.CSVReadException;
import edu.ntnu.idatt2003.group14.io.reader.stock.StockReader;
import edu.ntnu.idatt2003.group14.logging.AppLogger;
import edu.ntnu.idatt2003.group14.model.Stock;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Class responsible for testing the {@link StockReader} class.
 *
 * <p>Does not contain any integration tests</p>
 *
 * @author Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
 */
public class StockReaderTest {
  private StockReader reader;

  @BeforeEach
  void setUp() {
    AppLogger.silence();
    reader = new StockReader();
  }

  @Test
  void parse_converts_valid_strings_to_stocks() throws CSVReadException {
    List<String> input = List.of(
        "# comment",
        "AAPL,Apple Inc,150.00",
        "MSFT,Microsoft Corporation,300.50"
    );

    List<Stock> stocks = reader.parse(input);

    assertEquals(2, stocks.size());
    assertEquals("AAPL", stocks.get(0).getSymbol());
    assertEquals("MSFT", stocks.get(1).getSymbol());
    assertEquals(new BigDecimal("150.00"), stocks.get(0).getSalesPrice());
  }

  @Test
  void parse_trims_whitespace_from_fields() throws CSVReadException {
    List<String> input = List.of("  AAPL  ,  Apple Inc  ,  150.00  ");

    List<Stock> stocks = reader.parse(input);

    assertEquals("AAPL", stocks.getFirst().getSymbol());
    assertEquals("Apple Inc", stocks.getFirst().getCompany());
    assertEquals(new BigDecimal("150.00"), stocks.getFirst().getSalesPrice());
  }

  @Test
  void parse_supports_utf8_characters() throws CSVReadException {
    String name = "你妈妈是个男人！\uD83D\uDE43 ASA øæå 123";
    List<String> input = List.of("test, " + name +" , 120.00");

    List<Stock> stocks = reader.parse(input);

    assertEquals(name, stocks.getFirst().getCompany());
  }

  @Test
  void parse_creates_mutable_price_history() throws CSVReadException {
    List<String> input = List.of("AAPL,Apple Inc,150.00");

    List<Stock> stocks = reader.parse(input);
    Stock stock = stocks.getFirst();

    assertDoesNotThrow(() -> stock.addNewSalesPrice(new BigDecimal("155.00")));
    assertEquals(2, stock.getHistoricalPrices().size());
  }

  @Test
  void parse_throws_exception_on_invalid_column_count() {
    List<String> tooFew = List.of("AAPL,Apple Inc");
    List<String> tooMany = List.of("AAPL,Apple Inc,150.00,Extra");

    assertThrows(CSVReadException.class, () -> reader.parse(tooFew));
    assertThrows(CSVReadException.class, () -> reader.parse(tooMany));
  }

  @Test
  void parse_throws_exception_with_correct_details() {
    String invalidLine = "AAPL,Apple Inc,NotANumber";
    List<String> input = List.of(
        "# comment",
        invalidLine
    );

    CSVReadException exception = assertThrows(CSVReadException.class, () -> reader.parse(input));

    assertEquals(CSVError.PARSING, exception.getError());
    assertEquals(2, exception.getParsingError().errorLineNumber());
    assertEquals(invalidLine, exception.getParsingError().errorLineString());
  }

  @Test
  void parse_throws_exception_on_empty_input() {
    List<String> empty = List.of();
    List<String> onlyComments = List.of("# comment", "");

    CSVReadException ex1 = assertThrows(CSVReadException.class, () -> reader.parse(empty));
    CSVReadException ex2 = assertThrows(CSVReadException.class, () -> reader.parse(onlyComments));

    assertEquals(CSVError.EMPTY_FILE, ex1.getError());
    assertEquals(CSVError.EMPTY_FILE, ex2.getError());
  }

  @Test
  @Tag("integration")
  void read_actually_reads_from_disk(@TempDir Path tempDir) throws IOException, CSVReadException {
    Path file = tempDir.resolve("test.csv");
    Files.writeString(file, "SWOI,SwedenOil,00.001");

    var stocks = reader.read(file.toString());
    assertFalse(stocks.isEmpty());
    assertEquals("SWOI", stocks.getFirst().getSymbol());
  }
}
