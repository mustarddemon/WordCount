package lib;
import java.util.HashMap;

public class ParseFile implements Runnable {
	
	private String text;
	private HashMap<String, Integer> resultHash;
	private Boolean isDone = false;
	
	public ParseFile(String text) {
		this.text = text;
	}

	public void run() {
		IWordCounter counter = new SfWordCounter();
		HashMap<String, Integer> hash = counter.getWordHash(text);
		isDone = true;
		resultHash = hash;
	}
	
	public HashMap<String, Integer> getResults() {
		return this.resultHash;
	}
	
	public Boolean isDone() {
		return this.isDone;
	}

}
