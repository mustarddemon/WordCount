package Models;

public class ResultEntry implements Comparable<ResultEntry> {

	private String word;
	private Integer count;

	public ResultEntry(String word, Integer count) {
		this.word = word;
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public int compareTo(ResultEntry o) {
		if (getCount() == o.getCount()) {
			return 0;
		} else if (getCount() > o.getCount()) {
			return 1;
		} else {
			return -1;
		}
	}

}
