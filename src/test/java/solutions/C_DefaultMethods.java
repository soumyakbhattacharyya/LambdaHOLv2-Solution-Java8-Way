package solutions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This set of exercises covers new default methods on the Collections and related APIs.
 */
public class C_DefaultMethods {

  /**
   * Given a list of StringBuilders, modify each StringBuilder in-place by appending the string "new" to each one.
   */
  @Test
  public void c01_appendNew() {
    List<StringBuilder> sbList = Arrays.asList(new StringBuilder("alfa"), new StringBuilder("bravo"), new StringBuilder("charlie"));

    // TODO write code to modify sbList
    sbList.forEach(x -> x.append("new"));

    assertEquals(Arrays.asList("alfanew", "bravonew", "charlienew"), sbList.stream().map(StringBuilder::toString).collect(Collectors.toList()));
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use Iterable.forEach().
  // </editor-fold>

  /**
   * Remove the words that have odd lengths from the list.
   */
  @Test
  public void c02_removeOddLengthWords() {
    List<String> list = new ArrayList<>(Arrays.asList("alfa", "bravo", "charlie", "delta", "echo", "foxtrot"));

    // TODO write code to modify list
    list.removeIf(x -> (x.length() % 2 != 0));

    assertEquals(Arrays.asList("alfa", "echo"), list);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use Collection.removeIf().
  // </editor-fold>

  /**
   * Replace every word in the list with its upper case equivalent.
   */
  @Test
  public void c03_upcaseAllWords() {
    List<String> list = Arrays.asList("alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

    // TODO code to modify list
    list.replaceAll(x -> x.toUpperCase());

    assertEquals(Arrays.asList("ALFA", "BRAVO", "CHARLIE", "DELTA", "ECHO", "FOXTROT"), list);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use List.replaceAll().
  // </editor-fold>

  /**
   * Given a map whose keys are Integers and whose values are StringBuilders, append to each StringBuilder the string representation of its corresponding Integer key. This should
   * mutate each StringBuilder value in-place.
   */
  @Test
  public void c04_appendToMapValues() {
    Map<Integer, StringBuilder> map = new TreeMap<>();
    map.put(1, new StringBuilder("alfa"));
    map.put(2, new StringBuilder("bravo"));
    map.put(3, new StringBuilder("charlie"));

    // TODO write code to modify map
    map.forEach((x, y) -> {
      y.append(String.valueOf(x.intValue()));
    });

    assertEquals(3, map.size());
    assertTrue(map.values().stream().allMatch(x -> x instanceof StringBuilder));
    assertEquals("alfa1", map.get(1).toString());
    assertEquals("bravo2", map.get(2).toString());
    assertEquals("charlie3", map.get(3).toString());
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use Map.forEach().
  // </editor-fold>

  /**
   * Given a map whose keys are Integers and whose values are Strings, append to each String the string representation of its corresponding Integer key.
   */
  @Test
  public void c05_replaceMapValues() {
    Map<Integer, String> map = new TreeMap<>();
    map.put(1, "alfa");
    map.put(2, "bravo");
    map.put(3, "charlie");

    // TODO write code to modify map
    map.replaceAll((x, y) -> y + x);

    assertEquals(new HashMap() {
      {
        put(1, "alfa1");
        put(2, "bravo2");
        put(3, "charlie3");
      }
    }, map);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use Map.replaceAll().
  // </editor-fold>

  /**
   * Given a list of words, populate a map whose keys are the lengths of each word, and whose values are list of words with that length.
   */
  @Test
  public void c06_mapOfListOfStringsByLength() {
    List<String> list = Arrays.asList("aardvark", "bison", "capybara", "alligator", "bushbaby", "chimpanzee", "avocet", "bustard", "capuchin");
    Map<Integer, List<String>> result = new TreeMap<>();

    // TODO write code to populate result

    // /*
    // * iterate list find length
    // *
    // */
    // List<String> list2 = new ArrayList<>();
    // list2.addAll(list);
    // list.forEach(x -> {
    // final int len = x.length();
    // result.computeIfAbsent(len, new Function<Integer, List<String>>() {
    // @Override
    // public List<String> apply(Integer t) {
    // return list2.stream().filter(x -> {
    // return (x.length() == t);
    // }).collect(Collectors.toList());
    // }
    // });
    // });

    list.forEach(s -> result.computeIfAbsent(s.length(), key -> new ArrayList<>()).add(s));

    assertEquals(new HashMap() {
      {
        put(5, Arrays.asList("bison"));
        put(6, Arrays.asList("avocet"));
        put(7, Arrays.asList("bustard"));
        put(8, Arrays.asList("aardvark", "capybara", "bushbaby", "capuchin"));
        put(9, Arrays.asList("alligator"));
        put(10, Arrays.asList("chimpanzee"));
      }
    }, result);
  }

  /**
   * Given a list of words, populate a map whose keys are the initial characters of each word, and whose values are the concatenation of the words with that initial character. When
   * concatenating the words, they should be separated by a colon (':').
   */
  @Test
  public void c07_mapOfStringByInitialCharacter() {
    List<String> list = Arrays.asList("aardvark", "bison", "capybara", "alligator", "bushbaby", "chimpanzee", "avocet", "bustard", "capuchin");

    // list.stream().collect();
    Map<Character, String> result = new TreeMap<Character, String>();

    // TODO write code to populate result
    BiFunction<String, String, String> f = new BiFunction<String, String, String>() {

      @Override
      public String apply(String t, String u) {
        return t + u;
      }
    };
    BiFunction<String, String, String> f1 = (t, u) -> t + ":" + u;
    list.forEach(s -> result.merge(s.charAt(0), s, f1));

    assertEquals(new HashMap() {
      {
        put('a', "aardvark:alligator:avocet");
        put('b', "bison:bushbaby:bustard");
        put('c', "capybara:chimpanzee:capuchin");
      }
    }, result);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use Map.merge() within Iterable.forEach().
  // </editor-fold>

  /**
   * For some reason the provided map doesn't have mappings for all the keys. This is a problem, because if we call get() on a key that isn't present, it returns null, and we need
   * to add checks to protect against NullPointerException. Write code to ensure that all missing keys are mapped to the empty string.
   */
  @Test
  public void c08_mapWithMissingValues() {
    List<String> keys = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
    Map<String, String> map = new HashMap<>();
    map.put("a", "alfa");
    map.put("b", "bravo");
    map.put("c", "charlie");
    map.put("d", "delta");

    Map<String, String> expected = new HashMap<>();
    expected.put("a", "alfa");
    expected.put("b", "bravo");
    expected.put("c", "charlie");
    expected.put("d", "delta");
    expected.put("e", "");
    expected.put("f", "");
    expected.put("g", "");

    keys.forEach(key -> map.putIfAbsent(key, ""));

    // Map<String, String> map = new HashMap<>(Map.of("a", "alfa", "b", "bravo", "c", "charlie", "d", "delta"));

    // TODO write code to fix the map

    assertEquals(expected, map);
  }

  // // Hint:
  // // <editor-fold defaultstate="collapsed">
  // // Check the Map.putIfAbsent() default method.
  // // </editor-fold>
  //
  /**
   * In the previous example, we added map entries that had a default value. We've now determined that's incorrect, and we want to undo that. This time, we want to remove the entry
   * if the value is the empty string.
   */
  @Test
  public void c09_mapRemoveEntriesWithEmptyValues() {
    List<String> keys = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    Map<String, String> map = new HashMap<>();
    map.put("a", "alfa");
    map.put("b", "bravo");
    map.put("c", "charlie");
    map.put("d", "delta");
    map.put("e", "");
    map.put("f", "");
    map.put("g", "");

    Map<String, String> expected = new HashMap<>();
    expected.put("a", "alfa");
    expected.put("b", "bravo");
    expected.put("c", "charlie");
    expected.put("d", "delta");

    // TODO write code to fix the map
    map.entrySet().removeIf(x -> "".equals(x.getValue()));

    assertEquals(expected, map);
  }

  /**
   * We need another way to deal with the problem of the previous example. Instead of removing entries whose value is the empty string, we want to replace the empty-string values
   * with a value that's the key itself. Write the code to do that.
   */
  @Test
  public void c10_mapReplaceEmptyValues() {
    List<String> keys = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    Map<String, String> map = new HashMap<>();
    map.put("a", "alfa");
    map.put("b", "bravo");
    map.put("c", "charlie");
    map.put("d", "delta");
    map.put("e", "");
    map.put("f", "");
    map.put("g", "");

    Map<String, String> expected = new HashMap<>();
    expected.put("a", "alfa");
    expected.put("b", "bravo");
    expected.put("c", "charlie");
    expected.put("d", "delta");
    expected.put("e", "e");
    expected.put("f", "f");
    expected.put("g", "g");

    // TODO write code to fix the map
    keys.forEach(x -> map.replaceAll((t, u) -> {
      if ("".equals(u)) {
        return t;
      } else {
        return u;
      }
    }));

    assertEquals(expected, map);
  }

  /**
   * We are still dealing with a map with missing entries. For entries that are present, we want to convert the value to upper case; and for keys that are not present, we want to
   * add an entry where the value is the same as the key.
   */
  @Test
  public void c11_computeWithMissingEntries() {
    List<String> keys = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
    Map<String, String> map = new HashMap<>();
    map.put("a", "alfa");
    map.put("b", "bravo");
    map.put("c", "charlie");
    map.put("d", "delta");
    map.put("e", "");
    map.put("f", "");
    map.put("g", "");

    map.replaceAll(new BiFunction<String, String, String>() {

      @Override
      public String apply(String key, String value) {

        if ("".equals(value)) {
          return key;
        } else {
          return value.toUpperCase();
        }

      }
    });

    Map<String, String> expected = new HashMap<>();
    expected.put("a", "ALFA");
    expected.put("b", "BRAVO");
    expected.put("c", "CHARLIE");
    expected.put("d", "DELTA");
    expected.put("e", "e");
    expected.put("f", "f");
    expected.put("g", "g");

    assertEquals(expected, map);
  }

  /**
   * The map now has several entries, some with valid values, and some with values that are the empty string. This time, we want to convert the non-empty values to upper case, but
   * we want to remove the entries for which the values are the empty string.
   */
  @Test
  public void c12_computeAndRemoveSomeEntries() {
    List<String> keys = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
    Map<String, String> map = new HashMap<>();
    map.put("a", "alfa");
    map.put("b", "bravo");
    map.put("c", "charlie");
    map.put("d", "delta");
    map.put("e", "");
    map.put("f", "");
    map.put("g", "");

    // TODO write code transform the map   
    keys.forEach(key -> {map.compute(key, new BiFunction<String, String, String>() {

      @Override
      public String apply(String key, String value) {

        if ("".equals(value)) {
          return null;
        } else {
          return value.toUpperCase();
        }

      }
    });});
    
    Map<String, String> expected = new HashMap<>();
    expected.put("a", "ALFA");
    expected.put("b", "BRAVO");
    expected.put("c", "CHARLIE");
    expected.put("d", "DELTA");

    assertEquals(expected, map);
  }

}