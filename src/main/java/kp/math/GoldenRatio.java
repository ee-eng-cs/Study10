package kp.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Computes the Golden Ratio.
 *
 */
public class GoldenRatio {

	/**
	 * Computes the golden ratio.
	 * 
	 */
	public static void compute() {

		compute(3);
		compute(8);
		compute(16);
		System.out.println("- ".repeat(50));
	}

	/**
	 * Computes the golden ratio with THE scale.
	 * 
	 * @param scale the scale of the BigDecimal
	 */
	private static void compute(int scale) {

		final PrimitiveIterator.OfInt intIterator = IntStream.iterate(1, i -> ++i).iterator();
		final PrimitiveIterator.OfDouble doubleIterator = DoubleStream.iterate(1, n -> 1 + 1 / n).iterator();
		// generate with endless iteration
		final DoubleStream doubleStream = IntStream/*-*/
				.generate(intIterator::next)/*-*/
				.mapToDouble(i -> doubleIterator.next());

		final DoubleUnaryOperator rounding = arg -> new BigDecimal(arg).setScale(scale, RoundingMode.HALF_UP)
				.doubleValue();
		final Map<Double, Boolean> flagMap = new HashMap<>();
		// φ represents the golden ratio
		final OptionalDouble φ = doubleStream/*-*/
				.map(rounding)/*-*/
				.peek(arg -> flagMap.put(arg, flagMap.containsKey(arg)))/*-*/
				.filter(flagMap::get)/*-*/
				.findFirst();
		System.out.printf("Golden ratio φ[%.15f] computed with scale[%2d]%n", φ.getAsDouble(), scale);
	}
}