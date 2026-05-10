package edu.ntnu.idatt2003.group14.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Test of AppLogger.
 *
 * <p>These tests are required to be run manually.
 * <b>Note</b>: This writes to the actual log file.</p>
 */
public class AppLoggerTest {
  @Disabled("Manual test only; writes to log file")
  @Test
  void error_writes_to_log_file() throws IOException {
    // should also write to terminal, but harder to assert
    AppLogger.error("test error");
    AppLogger.warn("test warn");
    AppLogger.fine("test fine");
    String log = Files.readString(Path.of("millions.log"));
    assertTrue(log.contains("test error"));
  }

  @Disabled("Manual test only; writes to log file")
  @Test
  void warn_does_not_write_to_log_file() throws IOException {
    String before = Files.readString(Path.of("millions.log"));
    AppLogger.warn("test warn");
    String after = Files.readString(Path.of("millions.log"));
    assertEquals(before, after);
  }
}
