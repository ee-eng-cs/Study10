package kp.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Compares the cached numbers.
 *
 */
public class CachedNumbers {

	/**
	 * Compares the cached numbers.
	 * 
	 */
	public static void compare() {

		IntStream.rangeClosed(127, 128).forEach(n -> {
			Byte b1 = (byte) n;
			Byte b2 = (byte) n;
			System.out.printf("number[%4d], 'equals'[%5b], '=='[%5b] byte → Byte%n", b1, b1.equals(b2), b1 == b2);
		});
		System.out.println();
		IntStream.of(127, 128).forEach(n -> {
			// ShortCache
			Short s1 = (short) n;
			Short s2 = (short) n;
			System.out.printf("number[%d], 'equals'[%5b], '=='[%5b] short → Short%n", s1, s1.equals(s2), s1 == s2);
			// IntegerCache
			Integer i1 = n;
			Integer i2 = n;
			System.out.printf("number[%d], 'equals'[%5b], '=='[%5b] int → Integer%n", i1, i1.equals(i2), i1 == i2);
			// LongCache
			Long l1 = (long) n;
			Long l2 = (long) n;
			System.out.printf("number[%d], 'equals'[%5b], '=='[%5b] long → Long%n%n", l1, l1.equals(l2), l1 == l2);
		});
		// 'compareTo' is preferred to 'equals'
		LongStream.of(10, 11, 16, 17).forEach(n -> {
			BigInteger bi1 = BigInteger.valueOf(n);
			BigInteger bi2 = BigInteger.valueOf(n);
			System.out.printf("number[%3d], 'compareTo'[%d], '=='[%5b] BigInteger%n", n, bi1.compareTo(bi2),
					bi1 == bi2);
			BigDecimal bd1 = BigDecimal.valueOf(n);
			BigDecimal bd2 = BigDecimal.valueOf(n);
			System.out.printf("number[%3d], 'compareTo'[%d], '=='[%5b] BigDecimal%n%n", n, bd1.compareTo(bd2),
					bd1 == bd2);
		});

		// therefore 'compareTo' is preferred to 'equals'
		final BigDecimal twoScaleTwo = BigDecimal.valueOf(2).setScale(2, RoundingMode.HALF_UP);
		int scale = 2;
		BigDecimal two = BigDecimal.valueOf(2).setScale(scale, RoundingMode.HALF_UP);
		System.out.printf("BigDecimal with scale 2[%e], other with scale[%d],      'compareTo'[%d], 'equals'[%b]%n",
				twoScaleTwo, scale, twoScaleTwo.compareTo(two), twoScaleTwo.equals(two));
		scale = 3;
		two = BigDecimal.valueOf(2).setScale(scale, RoundingMode.HALF_UP);
		System.out.printf("BigDecimal with scale 2[%e], other with scale[%d],      'compareTo'[%d], 'equals'[%b]%n",
				twoScaleTwo, scale, twoScaleTwo.compareTo(two), twoScaleTwo.equals(two));
		System.out.printf("BigDecimal with scale 2[%e], other with default scale, 'compareTo'[%d], 'equals'[%b]%n",
				twoScaleTwo, twoScaleTwo.compareTo(BigDecimal.valueOf(2)), twoScaleTwo.equals(BigDecimal.valueOf(2)));
		System.out.println("- ".repeat(50));
	}
}