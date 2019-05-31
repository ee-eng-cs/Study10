package kp.math.statistics;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import kp.utils.Utils;

/**
 * Shows the summary statistics.
 *
 */
public class SummaryStatistics {

	/**
	 * Shows the summary statistics.
	 * 
	 */
	public static void show() {

		final long minExponent = 0, maxExponent = 6;
		System.out.printf("Generated using exponents between [%d] and [%d]%n", minExponent, maxExponent);
		final Supplier<LongStream> raisedSupplier = () -> LongStream.rangeClosed(minExponent, maxExponent)/*-*/
				.map(n -> Double.valueOf(Math.pow(10, n)).longValue());
		raisedSupplier.get().average()
				.ifPresent(avg -> System.out.printf("The arithmetic mean[%s]%n", Utils.formatNumber((long) avg)));

		final List<Long> list = raisedSupplier.get().boxed().collect(Collectors.toList());
		System.out.printf("Collected list%s%n", list);

		final LongSummaryStatistics stats = list.stream().mapToLong(Long::longValue).summaryStatistics();
		System.out.printf("The summary statistics: minimum [%s], arithmetic mean[%s], maximum [%s]%n",
				Utils.formatNumber(stats.getMin()), Utils.formatNumber((long) stats.getAverage()),
				Utils.formatNumber(stats.getMax()));
		System.out.println(stats);

		final String averageFormated = list.stream().collect(/*-*/
				Collectors.collectingAndThen(/*-*/
						Collectors.averagingLong(Long::longValue), arg -> Utils.formatNumber(arg.longValue())));
		System.out.printf("The arithmetic mean[%s]%n", averageFormated);
		System.out.println("- ".repeat(50));
	}
}
