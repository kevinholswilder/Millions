package edu.ntnu.idatt2003.group14.io.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Represents a class for reading CSV files.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class CSVReader {

  /**
   * Reads a file either from the local system or from the resources folder.
   *
   * @param filename the name of the file to read
   * @return a list of lines from the file
   * @throws IOException if the file cannot be read
   */
  public List<String> readFromFile(String filename) throws IOException {
    Path path = Path.of(filename);

    if (Files.exists(path)) {
      try (BufferedReader br = Files.newBufferedReader(path)) {
        return br.readAllLines();
      }
    }

    try (InputStream is = getClass().getResourceAsStream("/" + filename)) {
      if (is == null) {
        throw new IOException("File not found: " + filename);
      }

      try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
        return br.readAllLines();
      }
    }
  }
}