package lib;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ParseFileUtil {

	private Integer getRunningThreads(List<ParseFile> threads) {
		Integer count = 0;
		for (ParseFile thread : threads) {
			if (thread.isDone() == false) {
				count += 1;
			}
		}
		return count;
	}

	private HashMap<String, Integer> combineThreadResults(List<HashMap<String, Integer>> threadResults) {
		HashMap<String, Integer> finalResults = new HashMap<String, Integer>();
		// Combine Thread Results
		for (HashMap<String, Integer> result : threadResults) {
			Set<String> keys = result.keySet();

			for (String key : keys) {
				Integer newValue = result.get(key);
				if (finalResults.containsKey(key)) {
					Integer currentValue = finalResults.get(key);

					finalResults.put(key, currentValue + newValue);
				} else {
					finalResults.put(key, newValue);
				}

			}
		}

		return finalResults;
	}

	public HashMap<String, Integer> runThreads(List<String> results, Integer maxThreadCount) {

		Integer currentResult = 0;
		Integer totalResults = results.size();

		List<HashMap<String, Integer>> threadResults = new ArrayList<HashMap<String, Integer>>();
		// start threads
		List<ParseFile> threads = new ArrayList<ParseFile>();

		while (currentResult < totalResults) {
			Integer runningThreads = getRunningThreads(threads);
			while (runningThreads < maxThreadCount && currentResult < totalResults) {

				ParseFile thread = new ParseFile(results.get(currentResult));
				thread.run();
				threads.add(thread);

				currentResult += 1;
			}
		}


		// Wait for and get results
		while (getRunningThreads(threads) != 0) {
		}

		for (ParseFile thread : threads) {
			threadResults.add(thread.getResults());
		}

		return combineThreadResults(threadResults);

	}

}
