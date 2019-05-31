package kp.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Computes cubes and roots.
 *
 */
public class CubesAndRoots {

	/**
	 * Computes cubes and roots.
	 * 
	 */
	public static void compute() {

		final List<Long> numberList = LongStream.rangeClosed(0, 8).boxed().collect(Collectors.toList());
		System.out.printf("Numbers %s%n", numberList);
		final double exponent = 3;
		final List<Long> cubeList = computeCubes(numberList, exponent);
		computeRoots(cubeList, exponent);
		System.out.println("- ".repeat(50));
	}

	/**
	 * Computes cubes.
	 * 
	 * @param numberList the number list
	 * @param exponent   the exponent
	 * @return cubeList the cube list
	 */
	private static List<Long> computeCubes(List<Long> numberList, double exponent) {

		final List<Long> cubeList1 = numberList.stream()/*-*/
				.map(arg -> arg * arg * arg)/*-*/
				.collect(Collectors.toList());
		System.out.printf("Cubes   %s%n", cubeList1);
		final UnaryOperator<Double> powerOperator = base -> Math.pow(base, exponent);
		final List<Long> cubeList2 = cubeList1.stream()/*-*/
				.map(Double::valueOf)/*-*/
				.map(powerOperator)/*-*/
				.map(Double::longValue)/*-*/
				.collect(Collectors.toList());
		System.out.printf("Cubes   %s%n", cubeList2);
		return cubeList2;
	}

	/**
	 * Computes roots.
	 * 
	 * @param cubeList the cube list
	 * @param exponent the exponent
	 */
	private static void computeRoots(List<Long> cubeList, double exponent) {

		final Function<Double, Long> rounding = arg -> new BigDecimal(arg).setScale(0, RoundingMode.HALF_UP)
				.longValue();
		final List<Long> cubeRootList1 = cubeList.stream()/*-*/
				.map(Double::valueOf)/*-*/
				.map(Math::cbrt)/*-*/
				.map(rounding)/*-*/
				.collect(Collectors.toList());
		System.out.printf("Roots   %s%n", cubeRootList1);
		/*-
		 *  Computed with 'Math.log' because computing with 'Math.pow' loses precision!
		 *  The n-th root of a number x is equal with the number x in the power of 1/n.
		 */
		final UnaryOperator<Double> rootOperator = base -> Math.pow(Math.E, Math.log(base) / exponent);
		final List<Long> cubeRootList2 = cubeRootList1.stream()/*-*/
				.map(Double::valueOf)/*-*/
				.map(rootOperator)/*-*/
				.map(rounding)/*-*/
				.collect(Collectors.toList());
		System.out.printf("Roots   %s%n", cubeRootList2);
	}
}