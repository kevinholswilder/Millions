package edu.ntnu.idatt2003.group14.io.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

/**
 * Represents a class for reading CSV files.
 *
 * @author Kevin Holswilder
 * @since 0.0.1
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class CSVReader {

  private static final String SEPARATOR = ",";
  private static final String COMMENT_CHAR = "#";

  public static String getSeparator() {
    return SEPARATOR;
  }

  public static String getCommentChar() {
    return COMMENT_CHAR;
  }

  /**
   * Reads a file from the resources folder.
   *
   * @param filename the name of the file to read
   * @return a list of lines from the file
   * @throws IOException if the file cannot be read
   */
  public List<String> readFromFile(String filename) throws IOException {
    try (
        BufferedReader br = new BufferedReader(
            new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream("/" + filename)))
        )
    ) {
      if (br.ready()) {
        return br.readAllLines();
      }
    } catch (NullPointerException e) {
      System.err.println("Could not find file: " + filename + " in resources folder.");
    }
    return null;
  }
}
