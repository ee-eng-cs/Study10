package kp.math;

import java.math.BigInteger;
import java.time.Instant;

import kp.utils.Utils;

/**
 * Raise the big integer.
 *
 */
public class VeryBigIntegerRaised {

	/**
	 * Raising BigInteger with the specified value to the specified exponent value.
	 * 
	 */
	public static void compute() {

		final boolean SKIP_LONG_TIME_COMPUTING_FLAG = true;
		/*-
		 * The base36 length with max exponent: 415 380 039.
		 * It was created in 2 hours 15 minutes.
		 */
		final long[] VALUES = { /*-*/
				2L, /*-*/
				0x8000L, /*-            Short.MAX_VALUE + 1      */
				0x80000000L, /*-      Integer.MAX_VALUE + 1      */
				0x800000000000000L, /*- (Long.MAX_VALUE + 1) / 2 */
				3L, /*-*/
				9L, /*-*/
				17L, /*-*/
		};
		final int[] EXPONENTS = { /*-*/
				0xF, /*-*/
				0xFF, /*-*/
				0xFFF, /*-*/
				0xFFFF, /*-*/
				0xFFFFF, /*-*/
				0xFFFFFF, /*-*/
				0xFFFFFFF, /*-*/
				0x7FFFFFFF - 1,/*- Integer.MAX_VALUE - 1 */
		};
		MAIN_LOOP: for (long value : VALUES) {
			final BigInteger bigInteger = BigInteger.valueOf(value);
			System.out.printf("▼▼▼ To be raised the BigInteger with value DEC[%1$,23d]/HEX[%1$15X] ▼▼▼%n", bigInteger);
			for (int exponent : EXPONENTS) {
				if (SKIP_LONG_TIME_COMPUTING_FLAG &&
				/*-     */(value == VALUES[4] && exponent == EXPONENTS[5]
						|| value == VALUES[5] && exponent == EXPONENTS[5]
						|| value == VALUES[6] && exponent == EXPONENTS[5])) {
					System.out.println("▲".repeat(96));
					continue MAIN_LOOP;
				}
				Instant start = Instant.now();
				try {
					final BigInteger veryBigInteger = bigInteger.pow(exponent);
					System.out.printf("►exponent[%1$,13d]/[%1$8X]◄%n", exponent);
					System.out.printf("bitLength[%,13d], bitCount[%,13d], ", veryBigInteger.bitLength(),
							veryBigInteger.bitCount());
					System.out.println(Utils.formatElapsed(start, Instant.now()));

					if (false) {
						start = Instant.now();
						String base36 = veryBigInteger.toString(Character.MAX_RADIX);
						System.out.printf("base36 length[%d], %s%n", base36.length(),
								Utils.formatElapsed(start, Instant.now()));
					}
				} catch (Exception ex) {
					System.out.printf(" exponent[%1$,13d]/[%1$8X]\nEXCEPTION\t\t\t\t[%2$s]%n", exponent,
							ex.getMessage());
				}
			}
			System.out.println("▲".repeat(96));
		}
		System.out.println("- ".repeat(50));
	}
}