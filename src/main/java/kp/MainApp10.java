package kp;

import kp.math.BigDecimalsFromStringRepresentation;
import kp.math.CachedNumbers;
import kp.math.CubesAndRoots;
import kp.math.EvenAndOddNumbers;
import kp.math.FibonacciNumbers;
import kp.math.GoldenRatio;
import kp.math.LeastCommonMultiple;
import kp.math.Logic;
import kp.math.Means;
import kp.math.NumberInCompactForm;
import kp.math.VeryBigIntegerRaised;
import kp.math.VeryBigIntegerRandomlyGenerated;
import kp.math.statistics.SummaryStatistics;
import kp.math.statistics.bayes.RandomizedTrialsLauncher;
import kp.math.statistics.bayes.StatisticalMeasures;

/**
 * Main application for Study 10.<br>
 * The mathematical research.
 *
 */
public class MainApp10 {
	private static final boolean ALL = true;
	private static final boolean BAYES = false;

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		if (ALL) {
			NumberInCompactForm.format();
		}
		if (ALL) {
			Logic.aggregate();
		}
		if (ALL) {
			EvenAndOddNumbers.segregate();
		}
		if (ALL) {
			Means.computeMeansWithTeeing();
		}
		if (ALL) {
			GoldenRatio.compute();
		}
		if (ALL) {
			FibonacciNumbers.compute();
		}
		if (ALL) {
			LeastCommonMultiple.compute();
		}
		if (ALL) {
			CubesAndRoots.compute();
		}
		if (ALL) {
			BigDecimalsFromStringRepresentation.multiply();
		}
		if (ALL) {
			VeryBigIntegerRaised.compute();
		}
		if (ALL) {
			VeryBigIntegerRandomlyGenerated.compute();
		}
		if (ALL) {
			CachedNumbers.compare();
		}
		if (ALL) {
			SummaryStatistics.show();
		}
		if (ALL || BAYES) {
			RandomizedTrialsLauncher.launchSeriesOfTrials(0);
		}
		if (ALL || BAYES) {
			StatisticalMeasures.measureSamples();
		}
	}
}