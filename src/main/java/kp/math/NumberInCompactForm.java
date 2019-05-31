package kp.math;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Format the number in compact form.
 *
 */
public class NumberInCompactForm {

	/**
	 * Formats given number in compact form using locale.
	 * 
	 */
	public static void format() {

		final List<Long> numbers = List.of(1_234L, 1_234_567L, 1_234_567_890L, 1_234_567_890_123L);
		Locale[] locales = { Locale.US, Locale.FRANCE, Locale.GERMANY, new Locale("es", "ES") };
		for (Locale locale : locales) {
			final NumberFormat numberFormatShort = NumberFormat.getCompactNumberInstance(locale,
					NumberFormat.Style.SHORT);
			final NumberFormat numberFormatLong = NumberFormat.getCompactNumberInstance(locale,
					NumberFormat.Style.LONG);
			System.out.printf("Number formatted in compact form with locale[%s]:%n", locale);
			numbers.stream().forEach(num -> System.out.printf("\tnumber[%13d], SHORT format[%6s], LONG format[%14s]%n",
					num, numberFormatShort.format(num), numberFormatLong.format(num)));
		}
		System.out.println("- ".repeat(50));
	}
}