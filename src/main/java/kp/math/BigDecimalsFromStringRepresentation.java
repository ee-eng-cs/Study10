package kp.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import kp.utils.Utils;

/**
 * Multiply big decimals from string representation.
 *
 */
public class BigDecimalsFromStringRepresentation {
	/**
	 * Multiplies big decimals.
	 *  
	 */
	public static void multiply() {

		final String[][] params = { /*- */
				{ "-1.4142E-12", "-1.4142E+12" }, /*- */
				{ "-1.111111106E+2", "+1.111111106E+3" }, /*- */
				{ "1", null }, /*- */
				{ null, "1" }, /*- */
				{ null, null },/*- */
		};
		for (int i = 0; i < params.length; i++) {
			multiply(params[i][0], params[i][1]);
			if (i < params.length - 1) {
				System.out.println();
			} else {
				System.out.println("- ".repeat(50));
			}
		}
	}

	/**
	 * Multiplies big decimals.
	 * 
	 * @param multiplierStr   the multiplier
	 * @param multiplicandStr the multiplicand
	 */
	private static void multiply(String multiplierStr, String multiplicandStr) {

		System.out.printf("String multiplier[%15s], string multiplicand[%15s]%n", multiplierStr, multiplicandStr);
		/* A - multiply with optionals */
		final Optional<BigDecimal> multiplier = Optional.ofNullable(multiplierStr).map(BigDecimal::new);
		final Optional<BigDecimal> multiplicand = Optional.ofNullable(multiplicandStr).map(BigDecimal::new);
		final BigDecimal productA = multiplier/*- */
				.map(arg -> arg.multiply(multiplicand.orElse(BigDecimal.ZERO)))/*- */
				.orElse(BigDecimal.ZERO);
		/* B - multiply using stream of two validated strings */
		final Stream<String> streamB = Stream.of(Objects.requireNonNullElse(multiplierStr, "0"),
				Objects.requireNonNullElse(multiplicandStr, "0"));
		final BigDecimal productB = streamB/*- */
				.map(BigDecimal::new)/*- */
				.reduce(BigDecimal::multiply)/*- */
				.get();
		/* C - multiply using stream of two optionals */
		final Stream<Optional<String>> streamC = Stream.of(Optional.ofNullable(multiplierStr),
				Optional.ofNullable(multiplicandStr));
		final BigDecimal productC = streamC/*- */
				.map(arg -> arg.orElse("0"))/*- guards against NoSuchElementException */
				.map(BigDecimal::new)/*- */
				.reduce(BigDecimal::multiply)/*- */
				.get();
		/* D - multiply using stream of map entries */
		final Map<String, String> mapD = Map.of(Objects.requireNonNullElse(multiplierStr, "0"),
				Objects.requireNonNullElse(multiplicandStr, "0"));
		final Function<Map.Entry<String, String>, Stream<String>> mapperE = arg -> Stream.of(arg.getKey(),
				arg.getValue());
		final BigDecimal productD = mapD/*- */
				.entrySet()/*- */
				.stream()/*- */
				.flatMap(mapperE)/*- */
				.map(BigDecimal::new)/*- */
				.reduce(BigDecimal::multiply)/*- */
				.get();
		/* E - multiply using two streams of big decimals */
		final Stream<BigDecimal> multiplierStream = Optional.ofNullable(multiplierStr).stream().map(BigDecimal::new);
		final Stream<BigDecimal> multiplicandStream = Optional.ofNullable(multiplicandStr).stream()
				.map(BigDecimal::new);
		final BiFunction<BigDecimal, BigDecimal, BigDecimal> biFunction = BigDecimal::multiply;
		final Function<BigDecimal, Stream<BigDecimal>> mapper = arg1 -> multiplicandStream
				.map(arg2 -> biFunction.apply(arg1, arg2));
		final BigDecimal productE = multiplierStream.flatMap(mapper).findFirst()
				.orElse(BigDecimal.ZERO);/*- guards against NoSuchElementException */

		System.out.printf(" → product A[%12s] B[%12s] C[%12s] D[%12s] E[%12s]%n", Utils.formatBigDecimal(productA),
				Utils.formatBigDecimal(productB), Utils.formatBigDecimal(productC), Utils.formatBigDecimal(productD),
				Utils.formatBigDecimal(productE));
		System.out.printf(" → BigDecimal scaled with scale '3': product[%11s]%n",
				productA.setScale(3, RoundingMode.HALF_EVEN));
	}
}