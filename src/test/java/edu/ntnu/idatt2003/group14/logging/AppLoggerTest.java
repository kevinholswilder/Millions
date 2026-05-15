package edu.ntnu.idatt2003.group14.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2003.group14.utils.AppDataPathUtil;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test of AppLogger.
 *
 * <p>These tests are required to be run manually.
 * <b>Note</b>: This writes to the actual log file.</p>
 */
public class AppLoggerTest {

  @Test
  @Tag("integration")
  void error_writes_to_log_file() throws IOException {
    // NOTE: this wipes the current log file
    // should also write to terminal, but harder to assert
    Files.write(AppDataPathUtil.getLogsFile(), "".getBytes());
    AppLogger.error("test error");
    AppLogger.warn("test warn");
    AppLogger.fine("test fine");
    String log = Files.readString(AppDataPathUtil.getLogsFile());
    assertTrue(log.contains("test error"));
  }

  @Test
  @Tag("integration")
  void warn_does_not_write_to_log_file() throws IOException {
    String before = Files.readString(AppDataPathUtil.getDataDirectory());
    AppLogger.warn("test warn");
    String after = Files.readString(AppDataPathUtil.getDataDirectory());
    assertEquals(before, after);
  }
}
