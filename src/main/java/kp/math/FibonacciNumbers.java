package kp.math;

import java.time.Instant;
import java.util.List;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import kp.utils.Utils;

/**
 * Computes the Fibonacci numbers.
 *
 */
public class FibonacciNumbers {

	/**
	 * Computes the Fibonacci numbers.
	 * 
	 */
	public static void compute() {

		computeWithSlowerAlgorithm(75025);
		computeWithFasterAlgorithm(24);
		System.out.println("- ".repeat(50));
	}

	/**
	 * Computes the Fibonacci numbers with faster algorithm.
	 * 
	 * @param amountOfFibonacciNumbers the amount of Fibonacci numbers
	 */
	private static void computeWithFasterAlgorithm(int amountOfFibonacciNumbers) {

		final Instant begin = Instant.now();
		final Stream<long[]> stream = Stream.iterate(new long[] { 1, 1 },
				prev -> new long[] { prev[1], prev[0] + prev[1] });
		final List<Long> list = stream.mapToLong(pair -> pair[1]).boxed().limit(amountOfFibonacciNumbers)
				.collect(Collectors.toList());
		final Instant end = Instant.now();
		System.out.printf("Fibonacci numbers %s%n", list);
		System.out.println(Utils.formatElapsed(begin, end));
	}

	/**
	 * Computes the Fibonacci numbers with slower algorithm.
	 * 
	 * @param maxFibonacciNumber the maximal value of a Fibonacci number
	 */
	private static void computeWithSlowerAlgorithm(long maxFibonacciNumber) {

		final Instant begin = Instant.now();
		final LongPredicate predicate = number -> {
			double x = 5 * Math.pow(number, 2) + 4;
			long sqrtX = (long) Math.sqrt(x);
			if (sqrtX * sqrtX == x) {
				return true;
			}
			x -= 8;
			sqrtX = (long) Math.sqrt(x);
			return sqrtX * sqrtX == x;
		};
		final List<Long> list = LongStream.rangeClosed(1, maxFibonacciNumber).filter(predicate).boxed()
				.collect(Collectors.toList());
		final Instant end = Instant.now();
		System.out.printf("Fibonacci numbers %s%n", list);
		System.out.println(Utils.formatElapsed(begin, end));
	}
}