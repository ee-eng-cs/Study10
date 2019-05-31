package kp.math.statistics.bayes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The launcher of the randomized trials.
 *
 */
public class RandomizedTrialsLauncher {

	private final static int NUMBER_OF_TRIALS = 1000;
	private final static int NUMBER_OF_SAMPLES = 1000;

	/**
	 * The sample outcome enum.
	 *
	 */
	private enum OUTCOME {
		TN, FN, FP, TP
	};

	/**
	 * Launches series of trials.
	 * 
	 * @param index the index
	 */
	public static void launchSeriesOfTrials(int index) {

		final List<OUTCOME> outcomeList = Arrays.asList(OUTCOME.values());
		final Map<OUTCOME, Integer> aggregateMap = outcomeList.stream()
				.collect(Collectors.toMap(Function.identity(), arg -> 0));
		final Map<OUTCOME, Map<OUTCOME, Integer>> maxMap = outcomeList.stream()
				.collect(Collectors.toMap(Function.identity(), arg -> {
					Map<OUTCOME, Integer> map = new HashMap<>();
					map.putAll(aggregateMap);
					return map;
				}));
		for (int i = 1; i <= NUMBER_OF_TRIALS; i++) {
			final Map<OUTCOME, Integer> resultMap = launchTrials(index);
			Map<OUTCOME, Integer> tmpMap = null;
			if (resultMap.get(OUTCOME.TN) > maxMap.get(OUTCOME.TN).get(OUTCOME.TN)) {
				tmpMap = maxMap.get(OUTCOME.TN);
			} else if (resultMap.get(OUTCOME.FN) > maxMap.get(OUTCOME.FN).get(OUTCOME.FN)) {
				tmpMap = maxMap.get(OUTCOME.FN);
			} else if (resultMap.get(OUTCOME.FP) > maxMap.get(OUTCOME.FP).get(OUTCOME.FP)) {
				tmpMap = maxMap.get(OUTCOME.FP);
			} else if (resultMap.get(OUTCOME.TP) > maxMap.get(OUTCOME.TP).get(OUTCOME.TP)) {
				tmpMap = maxMap.get(OUTCOME.TP);
			}
			for (OUTCOME outcome : resultMap.keySet()) {
				aggregateMap.put(outcome, aggregateMap.get(outcome) + resultMap.get(outcome));
				if (Objects.nonNull(tmpMap)) {
					tmpMap.put(outcome, resultMap.get(outcome));
				}
			}
		}
		merge(aggregateMap);
		showResults("Avg", aggregateMap);
		for (OUTCOME outcome : new OUTCOME[] { OUTCOME.TN, OUTCOME.FN, OUTCOME.FP, OUTCOME.TP }) {
			showResults("Max ".concat(outcome.name()), maxMap.get(outcome));
		}
		System.out.println("- ".repeat(50));
	}

	/**
	 * Launches trials with samples.
	 * 
	 * @param index the index
	 * @return the result map
	 */
	private static Map<OUTCOME, Integer> launchTrials(int index) {

		final int together = Samples.LEVELS[index][0] + Samples.LEVELS[index][1];
		final Stream<Integer> numStream = IntStream.rangeClosed(1, together).boxed();
		final Map<Integer, OUTCOME> outcomeMap = numStream
				.collect(Collectors.toMap(Function.identity(), arg -> computeOutcome(Samples.LEVELS[index], arg)));

		final IntStream rndStream = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(together))
				.limit(NUMBER_OF_SAMPLES).map(arg -> ++arg);
		final Stream<OUTCOME> outcomeStream = Arrays.asList(OUTCOME.values()).stream();

		final Map<OUTCOME, Integer> tmpMap = outcomeStream.collect(Collectors.toMap(Function.identity(), arg -> 0));
		final Map<OUTCOME, Integer> resultMap = rndStream.collect(() -> tmpMap,
				(map, item) -> map.put(outcomeMap.get(item), map.get(outcomeMap.get(item)) + 1),
				(map1, map2) -> map1.putAll(map2));
		return resultMap;
	}

	/**
	 * Computes the outcome.
	 * 
	 * @param levelArr the array of levels
	 * @param item     the item
	 * @return the outcome
	 */
	private static OUTCOME computeOutcome(int[] levelArr, int item) {

		OUTCOME outcome = null;
		// 0-Ngen, 1-Pgen, 2-FN, 3-FP
		if (item <= levelArr[0]) {
			// Genuine Negative Ngen = TN + FP
			if (item <= levelArr[0] - levelArr[3]) {
				// TN = Ngen - FP
				outcome = OUTCOME.TN;
			} else {
				outcome = OUTCOME.FP;
			}
		} else {
			// Genuine Positive Pgen = TP + FN
			if (item <= levelArr[0] + levelArr[2]) {
				// FN
				outcome = OUTCOME.FN;
			} else {
				outcome = OUTCOME.TP;
			}
		}
		return outcome;
	}

	/**
	 * Computes average values.
	 * 
	 * @param aggregateMap the aggregate map
	 */
	private static void merge(Map<OUTCOME, Integer> aggregateMap) {

		for (OUTCOME outcome : aggregateMap.keySet()) {
			final BigDecimal value = new BigDecimal(aggregateMap.get(outcome)).divide(new BigDecimal(NUMBER_OF_TRIALS),
					RoundingMode.HALF_UP);
			aggregateMap.put(outcome, value.intValue());
		}
	}

	/**
	 * Shows the results in five rows:
	 * <ul>
	 * <li>the average values from all trials
	 * <li>the values of a trial with maximal true negative value
	 * <li>the values of a trial with maximal false negative value
	 * <li>the values of a trial with maximal false positive value
	 * <li>the values of a trial with maximal true positive value
	 * </ul>
	 * 
	 * @param label     the label
	 * @param resultMap the result map
	 */
	private static void showResults(String label, Map<OUTCOME, Integer> resultMap) {

		System.out.printf("%-6s ", label);
		Stream.of(OUTCOME.TN, OUTCOME.FN, OUTCOME.FP, OUTCOME.TP)/*-*/
				.forEach(outcome -> System.out.printf("[%1$s]=[%2$3d] ", outcome.name(), resultMap.get(outcome)));
		final int nRec = resultMap.get(OUTCOME.TN) + resultMap.get(OUTCOME.FN);
		final int pRec = resultMap.get(OUTCOME.TP) + resultMap.get(OUTCOME.FP);
		final int nGen = resultMap.get(OUTCOME.TN) + resultMap.get(OUTCOME.FP);
		final int pGen = resultMap.get(OUTCOME.TP) + resultMap.get(OUTCOME.FN);
		System.out.printf("►►► [Ngen]=[%3d] [Pgen]=[%3d] ● [Nrec]=[%3d] [Prec]=[%3d] ►►► [%3d]%n", nGen, pGen, nRec,
				pRec, nRec + pRec);
	}
}
