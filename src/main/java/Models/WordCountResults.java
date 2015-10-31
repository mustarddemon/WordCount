package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCountResults {
	private Integer resultsToDisplay;
	private List<ResultEntry> results;
	private HashMap<String, Integer> mappedResults;

	public WordCountResults(HashMap<String, Integer> input, Integer resultsToDisplay) {
		mappedResults = input;

		this.resultsToDisplay = resultsToDisplay;
	}

	public List<ResultEntry> getResults() {
		if (results == null) {
			this.results = new ArrayList<ResultEntry>();
			for (Map.Entry<String, Integer> e : this.mappedResults.entrySet()) {
				this.results.add(new ResultEntry(e.getKey(), e.getValue()));
			}
			Collections.sort(results);
			Collections.reverse(results);
		}
		Integer showResults = resultsToDisplay;
		if (results.size() < resultsToDisplay) {
			showResults = results.size();
		}
		return results.subList(0, showResults);
	}

	public void setResults(List<ResultEntry> results) {
		this.results = results;
	}

}
