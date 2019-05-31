package kp.math;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
/**
 * Logic
 *
 */
public class Logic {

	/**
	 * The logic values aggregated in a map.
	 * 
	 */
	public static void aggregate() {

		final String EXPECTED = "A";
		System.out.printf("Aggregated logic, expected value[%s]%n", EXPECTED);
		/*-
		 * 'AND' is 'true'  if the current value always was equal to the expected value
		 * 'OR'  is 'false' if the current value never  was equal to the expected value
		 * 'XOR' is 'true'  if the current value is equal to the expected value and
		 *                     the previous value was not equal to the expected value
		 */
		final Map<String, Boolean> map = new TreeMap<String, Boolean>();
		Stream.of("A", "A", "Z", "A").forEach(value -> {
			map.merge("AND", EXPECTED.equals(value), Boolean::logicalAnd);
			map.merge("OR", EXPECTED.equals(value), Boolean::logicalOr);
			map.merge("XOR", EXPECTED.equals(value), Boolean::logicalXor);
			System.out.printf("Current value[%s], is expected[%5b], logic map%s%n", value, EXPECTED.equals(value), map);
		});
		System.out.println("- ".repeat(50));
	}
}