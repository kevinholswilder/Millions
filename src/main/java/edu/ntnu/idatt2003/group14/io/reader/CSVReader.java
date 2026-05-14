package edu.ntnu.idatt2003.group14.io.reader;

import edu.ntnu.idatt2003.group14.exception.csvReading.CSVError;
import edu.ntnu.idatt2003.group14.exception.csvReading.CSVReadException;
import edu.ntnu.idatt2003.group14.logging.AppLogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Represents a class for reading CSV files.
 *
 * @author Kevin Holswilder, Elias Haugsbakk
 * @version 1.0.0
 * @since 0.0.1
 */
public class CSVReader {

  /**
   * Reads a file either from the local system or from the resources' folder.
   *
   * @param filename the name of the file to read
   * @return a list of lines from the file
   * @throws CSVReadException if the file cannot be read
   */
  public List<String> readFromFile(String filename) throws CSVReadException {
    Path path = Path.of(filename);

    if (Files.exists(path) && !Files.isReadable(path)) {
      AppLogger.error("CSV file exists but is not readable: " + filename);
      throw new CSVReadException("No read permission for: " + path, CSVError.READ_FAILED);
    }

    if (Files.exists(path)) {
      try (BufferedReader br = Files.newBufferedReader(path)) {
        return br.readAllLines();
      } catch (IOException e) {
        AppLogger.error("CSV file could not be read: " + filename, e);
        throw new CSVReadException("Could not read from: " + path, e, CSVError.READ_FAILED);
      }
    }

    try (InputStream is = getClass().getResourceAsStream("/" + filename)) {
      if (is == null) {
        AppLogger.error("CSV file not found: " + filename);
        throw new CSVReadException("File not found: " + filename, CSVError.FILE_NOT_FOUND);
      }

      try (BufferedReader br = new BufferedReader(
          new InputStreamReader(is, StandardCharsets.UTF_8))) {
        return br.readAllLines();
      }
    } catch (CSVReadException e) {
      throw e;
    } catch (IOException e) {
      AppLogger.error("CSV file could not be read: " + filename, e);
      throw new CSVReadException("Could not read from: " + filename, e, CSVError.READ_FAILED);
    }
  }
}
