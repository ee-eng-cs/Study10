package kp.math;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Segregates even and odd numbers.
 * 
 */
public class EvenAndOddNumbers {

	/**
	 * Segregates even and odd numbers.
	 * 
	 */
	public static void segregate() {

		final Predicate<Integer> predicate = arg -> arg % 2 == 0;
		final Map<Boolean, List<Integer>> map = IntStream.rangeClosed(1, 5).boxed()
				.collect(Collectors.partitioningBy(predicate));
		System.out.printf("Evens%s, odds%s%n", map.get(Boolean.TRUE), map.get(Boolean.FALSE));
		System.out.println("- ".repeat(50));
	}
}
