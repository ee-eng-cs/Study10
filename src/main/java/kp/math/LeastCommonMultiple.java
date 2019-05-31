package kp.math;

import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Computes the Least Common Multiple.
 *
 */
public class LeastCommonMultiple {

	/**
	 * Computes the Least Common Multiple.
	 * 
	 * The number '2520' is the smallest number divisible by all integers from 1 to
	 * 10, i.e., it is their least common multiple.
	 */
	public static void compute() {

		final PrimitiveIterator.OfInt intIterator = IntStream.iterate(1, i -> ++i).iterator();
		final IntPredicate predicate = arg -> IntStream.rangeClosed(1, 9)/*-*/
				.map(n -> arg % n).allMatch(n -> n == 0);
		final OptionalInt result =
				// generate with endless iteration
				IntStream.generate(() -> intIterator.next())/*-*/
						.filter(predicate)/*-*/
						.findFirst();
		System.out.printf("The smallest number divisible by all one digit numbers is [%d]%n", result.getAsInt());
		System.out.println("- ".repeat(50));
	}
}