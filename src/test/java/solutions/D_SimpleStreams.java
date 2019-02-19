package solutions;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This set of exercises covers simple stream pipelines, including intermediate operations and basic collectors.
 *
 * Some of these exercises use a BufferedReader variable named "reader" that the test has set up for you.
 */
public class D_SimpleStreams {
  /**
   * Given a list of words, create an output list that contains only the odd-length words, converted to upper case.
   */
  @Test
  public void d1_upcaseOddLengthWords() {
    List<String> input = Arrays.asList("alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

    Predicate<String> filterFun = new Predicate<String>() {
      @Override
      public boolean test(String t) {
        return (t.length() % 2) != 0 ? true : false;
      }
    };
    List<String> result = input.stream().filter(filterFun).map(x -> x.toUpperCase()).collect(Collectors.toList());
    assertEquals(Arrays.asList("BRAVO", "CHARLIE", "DELTA", "FOXTROT"), result);
  }

  /**
   * Take the third through fifth words of the list, extract the second letter from each, and join them, separated by commas, into a single string. Watch for off-by-one errors.
   */
  @Test
  public void d2_joinStreamRange() {
    List<String> input = Arrays.asList("alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

    String result = input.stream().skip(2).limit(3).map(x -> String.valueOf(x.charAt(1))).collect(Collectors.joining(","));

    assertEquals("h,e,c", result);
  }

  /**
   * Count the number of lines in the text file. (Remember to use the BufferedReader named "reader" that has already been opened for you.)
   *
   * @throws IOException
   */
  @Test
  public void d3_countLinesInFile() throws IOException {

    long count = reader.lines().count(); // TODO

    assertEquals(14, count);
  }

  /**
   * Find the length of the longest line in the text file.
   *
   * @throws IOException
   */
  @Test
  public void d4_findLengthOfLongestLine() throws IOException {
    Comparator<String> longestStringFinder = new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.length() < o2.length() ? -1 : 1;
      }
    };
    int longestLength = reader.lines().collect(Collectors.maxBy(longestStringFinder)).get().length();
    assertEquals(53, longestLength);
  }

  /**
   * Find the longest line in the text file.
   *
   * @throws IOException
   */
  @Test
  public void d5_findLongestLine() throws IOException {
    Comparator<String> longestStringFinder = new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.length() < o2.length() ? -1 : 1;
      }
    };
    String longest = reader.lines().collect(Collectors.maxBy(longestStringFinder)).get();

    assertEquals("Feed'st thy light's flame with self-substantial fuel,", longest);
  }

  /**
   * Select the longest words from the input list. That is, select the words whose lengths are equal to the maximum word length.
   */
  @Test
  public void d6_selectLongestWords() {
    Comparator<String> longestStringFinder = new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.length() < o2.length() ? -1 : 1;
      }
    };
    List<String> input = Arrays.asList("alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

    int length = input.stream().collect(Collectors.maxBy(longestStringFinder)).get().length();
    List<String> result = input.stream().filter(x -> (x.length() == length)).collect(Collectors.toList());
    assertEquals(Arrays.asList("charlie", "foxtrot"), result);
  }

  /**
   * Select the list of words from the input list whose length is greater than the word's position in the list (starting from zero) .
   */
  @Test
  public void d7_selectByLengthAndPosition() {
    List<String> input = Arrays.asList("alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");
    List<String> result = input.stream().filter(x -> x.length() > input.indexOf(x)).collect(Collectors.toList());; 

    assertEquals(Arrays.asList("alfa", "bravo", "charlie", "delta", "foxtrot"), result);
  }

  // // Hint:
  // // <editor-fold defaultstate="collapsed">
  // // Instead of a stream of words (Strings), run an IntStream of indexes of
  // // the input list, using index values to get elements from the input list.
  // // </editor-fold>
  //
  // // ========================================================
  // // END OF EXERCISES
  // // TEST INFRASTRUCTURE IS BELOW
  // // ========================================================
  //
  private BufferedReader reader;

  @Before
  public void z_setUpBufferedReader() throws IOException {
    reader = Files.newBufferedReader(Paths.get("src/test/java/SonnetI.txt"), StandardCharsets.UTF_8);
  }

  @After
  public void z_closeBufferedReader() throws IOException {
    reader.close();
  }

  //// Hint 1:
  //// <editor-fold defaultstate="collapsed">
  //// Use Stream.skip() and Stream.limit().
  //// </editor-fold>
  //// Hint 2:
  //// <editor-fold defaultstate="collapsed">
  //// Use Collectors.joining().
  //// </editor-fold>

}
