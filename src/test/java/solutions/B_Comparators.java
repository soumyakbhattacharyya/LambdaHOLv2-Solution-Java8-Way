package solutions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;

import org.junit.Ignore;
import org.junit.Test;

import model.Person;

/*
 * For the mathematically inclined, the relation that defines the imposed ordering that a given comparator c imposes on a given set of objects S is:
   {(x, y) such that c.compare(x, y) <= 0}.
 
   The quotient for this total order is:
   {(x, y) such that c.compare(x, y) == 0}.
 
   It follows immediately from the contract for compare that the quotient is an equivalence relation on S, and that the imposed ordering is a total order on S. When we say that the ordering imposed by c on S is consistent with equals, we mean that the quotient for the ordering is the equivalence relation defined by the objects' equals(Object) method(s):
   {(x, y) such that x.equals(y)}. 
 */

/**
 * Exercises to create comparators using lambda expressions and using the Comparator combinators. Some of the exercises use a Person class, which is a simple POJO containing a last
 * name, first name, and age, with the obvious constructors and getters.
 */
public class B_Comparators {

  final Person michael = new Person("Michael", "Jackson", 51);
  final Person rod = new Person("Rod", "Stewart", 71);
  final Person paul = new Person("Paul", "McCartney", 74);
  final Person mick = new Person("Mick", "Jagger", 73);
  final Person jermaine = new Person("Jermaine", "Jackson", 61);

  /**
   * Write a Comparator that compare instances of String using their length. For instance FOUR (4 letters) is greater than TWO (three letters)
   */
  @Test
  public void comparator01() {
    Comparator<String> compareByLength = (s1, s2) -> s1.length() - s2.length();

    assertTrue(compareByLength.compare("FOUR", "TWO") > 0);
    assertTrue(compareByLength.compare("ONE", "SEVEN") < 0);
    assertTrue(compareByLength.compare("ONE", "TWO") == 0);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Check the static factory methods of the Comparator interface. Remember
  // how you implemented functions in the previous exercises. Write it using
  // a method reference.
  // </editor-fold>

  /**
   * Write a Comparator that compare instances of String using their length. If the lengths are the same, then use the alphabetical order.
   */
  @Test
  public void comparator02() {
    Comparator<String> compareByLength = (s1, s2) -> s1.length() - s2.length();
    Comparator<String> naturalComparator = Comparator.naturalOrder();

    Comparator<String> compareByLengthThenAlphabetical = compareByLength.thenComparing(naturalComparator);

    assertTrue(compareByLengthThenAlphabetical.compare("FOUR", "TWO") > 0);
    assertTrue(compareByLengthThenAlphabetical.compare("ONE", "SEVEN") < 0);
    assertTrue(compareByLengthThenAlphabetical.compare("ONE", "TWO") < 0);
    assertTrue(compareByLengthThenAlphabetical.compare("FOUR", "FIVE") > 0);
    assertTrue(compareByLengthThenAlphabetical.compare("EIGHT", "EIGHT") == 0);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use the previous comparator and check the default methods of the
  // Comparator interface.
  // Check also the factory methods of the Comparator interface, and remember
  // that String is comparable.
  // </editor-fold>

  /**
   * Write a Comparator that compares instances of Person using their lastName.
   */
  @Test
  public void comparator03() {
    Comparator<Person> comparebyLastName = Comparator.comparing(Person::getLastName, String.CASE_INSENSITIVE_ORDER);

    assertTrue(comparebyLastName.compare(michael, rod) < 0);
    assertTrue(comparebyLastName.compare(paul, paul) == 0);
    assertTrue(comparebyLastName.compare(michael, jermaine) == 0);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Check the static factory methods of the Comparator interface. Remember
  // how you implemented functions in the previous exercises. Write it using
  // a method reference.
  // </editor-fold>

  /**
   * Write a Comparator that compares instances of Person using their lastName, and if their last name is the same, uses their first name.
   */
  @Test
  public void comparator04() {
    Comparator<Person> comparebyFirstName = Comparator.comparing(Person::getFirstName, String.CASE_INSENSITIVE_ORDER);
    Comparator<Person> comparebyLastName = Comparator.comparing(Person::getLastName, String.CASE_INSENSITIVE_ORDER);
    Comparator<Person> comparebyLastNameThenFirstName = comparebyLastName.thenComparing(comparebyFirstName);

    assertTrue(comparebyLastNameThenFirstName.compare(michael, rod) < 0);
    assertTrue(comparebyLastNameThenFirstName.compare(paul, paul) == 0);
    assertTrue(comparebyLastNameThenFirstName.compare(michael, jermaine) > 0);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use the previous comparator and check the default methods of the Comparator interface.
  // </editor-fold>

  /**
   * Write a Comparator that compares the people in the order reversed from the one you wrote in the comparator04() exercise. That is, the person with the greater last name should
   * be ordered first. If two persons have the same last name, the one with the greater first name should be ordered first.
   */
  @Test
  public void comparator05() {
    Comparator<Person> comparebyFirstName = Comparator.comparing(Person::getFirstName, String.CASE_INSENSITIVE_ORDER);
    Comparator<Person> comparebyLastName = Comparator.comparing(Person::getLastName, String.CASE_INSENSITIVE_ORDER);

    Comparator<Person> comparebyLastNameThenFirstNameReversed = comparebyLastName.reversed().thenComparing(comparebyFirstName.reversed());

    assertFalse(comparebyLastNameThenFirstNameReversed.compare(michael, rod) < 0);
    assertTrue(comparebyLastNameThenFirstNameReversed.compare(paul, paul) == 0);
    assertFalse(comparebyLastNameThenFirstNameReversed.compare(michael, jermaine) > 0);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use the previous comparator and check the default methods of the Comparator interface.
  // </editor-fold>

  /**
   * Write a Comparator that compares the people in the same order than the one you wrote in comparator04(), but that supports null values. The null values should be considered
   * greater than any non-null values.
   */
  @Test
  @Ignore
  public void comparator06() {
    Comparator<Person> comparebyLastNameThenFirstNameWithNull = null; // TODO

    assertTrue(comparebyLastNameThenFirstNameWithNull.compare(michael, rod) < 0);
    assertTrue(comparebyLastNameThenFirstNameWithNull.compare(paul, paul) == 0);
    assertTrue(comparebyLastNameThenFirstNameWithNull.compare(michael, jermaine) > 0);
    assertTrue(comparebyLastNameThenFirstNameWithNull.compare(mick, null) < 0);
    assertTrue(comparebyLastNameThenFirstNameWithNull.compare(null, mick) > 0);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use the previous comparator and check the static methods of the Comparator interface.
  // </editor-fold>

  /**
   * Write a Comparator that compares two people by age. Try to write the comparator so as to avoid boxing of primitives.
   */
  @Test
  public void comparator07() {
    Comparator<Person> comparebyAge = Comparator.comparingInt(Person::getAge);

    assertTrue(comparebyAge.compare(michael, rod) < 0);
    assertTrue(comparebyAge.compare(paul, paul) == 0);
    assertTrue(comparebyAge.compare(mick, jermaine) > 0);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Look for static methods on the Comparator interface that
  // have primitive specializations.
  // </editor-fold>

  /**
   * Write a lambda expression that compares two int values and returns an int result that is less than, equal to, or greater than zero, like a comparator. Watch out for overflow.
   * The Comparator interface takes two objects, but in this case we are comparing int primitives, so the functional interface we use is IntBinaryOperator.
   */
  @Test
  public void comparator08() {
    LongBinaryOperator intCompare = (x, y) -> {
      
      return ((x - y) == 0) ? 0 : (x- y);
      
    };
    assertTrue(intCompare.applyAsLong(0, 1) < 0);
    assertTrue(intCompare.applyAsLong(1, 1) == 0);
    assertTrue(intCompare.applyAsLong(2, 1) > 0);
    assertTrue(intCompare.applyAsLong(Integer.MIN_VALUE, Integer.MAX_VALUE) < 0);
    assertTrue(intCompare.applyAsLong(Integer.MAX_VALUE, Integer.MIN_VALUE) > 0);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use a ternary operator (cond ? result1 : result2) instead of subtraction.
  // </editor-fold>

  /**
   * Write a method reference that compares two int values and returns an int result that is less than, equal to, or greater than zero, like a comparator.
   */
  @Test
  @Ignore
  public void comparator09() {
    IntBinaryOperator intCompare = null; // TODO

    assertTrue(intCompare.applyAsInt(0, 1) < 0);
    assertTrue(intCompare.applyAsInt(1, 1) == 0);
    assertTrue(intCompare.applyAsInt(2, 1) > 0);
    assertTrue(intCompare.applyAsInt(Integer.MIN_VALUE, Integer.MAX_VALUE) < 0);
    assertTrue(intCompare.applyAsInt(Integer.MAX_VALUE, Integer.MIN_VALUE) > 0);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use a method reference to a static method on the Integer class.
  // </editor-fold>

  interface DoubleToIntBiFunction {
    int applyAsInt(double a, double b);
  }

  /**
   * Write a method reference that compares two double values and returns an int result that is less than, equal to, or greater than zero, like a comparator. There functional
   * interface that takes two doubles and returns an int, so we define one here. Comparing double values introduces special cases such NaN. Consider all NaN values to be equal to
   * each other and greater than any non-NaN value.
   */
  @Test
  @Ignore
  public void comparator10() {
    DoubleToIntBiFunction doubleCompare = null; // TODO

    assertTrue(doubleCompare.applyAsInt(0.0, 1.0) < 0);
    assertTrue(doubleCompare.applyAsInt(1.0, 1.0) == 0);
    assertTrue(doubleCompare.applyAsInt(2.0, 1.0) > 0);
    assertTrue(doubleCompare.applyAsInt(Double.NaN, Double.NaN) == 0);
    assertTrue(doubleCompare.applyAsInt(Double.NaN, 0.0) > 0);
    assertTrue(doubleCompare.applyAsInt(0.0, Double.NaN) < 0);
  }
  // Hint:
  // <editor-fold defaultstate="collapsed">
  // Use a method reference to a static method on the Double class.
  // </editor-fold>
}