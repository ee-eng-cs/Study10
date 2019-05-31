package kp.math;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import kp.utils.Utils;

/**
 * Randomly generate the big integer.
 *
 */
public class VeryBigIntegerRandomlyGenerated {

	/**
	 * Generate the number.
	 * 
	 */
	public static void compute() {

		System.out.println("Randomly generated BigInteger:");
		final int WORKERS = 5;
		final LongAdder counter = new LongAdder();
		final Instant startTotal = Instant.now();
		final IntFunction<Runnable> mapper = arg -> () -> {
			final Instant start = Instant.now();
			final SecureRandom secureRandom = new SecureRandom();
			final BigInteger veryBigInteger = new BigInteger(Integer.MAX_VALUE, secureRandom);
			final Instant stop = Instant.now();
			System.out.printf("(%1d) bitLength[%2$,d], bitCount[%3$,d], %4$s%n", arg, veryBigInteger.bitLength(),
					veryBigInteger.bitCount(), Utils.formatElapsed(start, stop));
			counter.increment();
		};
		final Consumer<Runnable> starter = runnable -> new Thread(runnable).start();
		IntStream.rangeClosed(1, WORKERS).mapToObj(mapper).forEach(starter);
		do {
			Utils.sleepMillis(250);
		} while (counter.sum() < WORKERS);
		System.out.printf("Total %s%n", Utils.formatElapsed(startTotal, Instant.now()));
		System.out.println("- ".repeat(50));
	}
}
